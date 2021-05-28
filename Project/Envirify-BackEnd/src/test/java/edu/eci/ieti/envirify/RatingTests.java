package edu.eci.ieti.envirify;

import com.google.gson.Gson;
import com.mongodb.client.MongoClients;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import edu.eci.ieti.envirify.controllers.dtos.CreatePlaceDTO;
import edu.eci.ieti.envirify.controllers.dtos.CreateUserDTO;
import edu.eci.ieti.envirify.controllers.dtos.RatingDTO;
import edu.eci.ieti.envirify.model.Rating;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RatingTests {

    @Autowired
    private MockMvc mockMvc;

    private static final Gson gson = new Gson();

    private static final String CONNECTION_STRING = "mongodb://%s:%d";

    private MongodExecutable mongodExecutable;
    private MongoTemplate mongoTemplate;

    @AfterEach
    void clean() {
        mongodExecutable.stop();
    }

    @BeforeEach
    void setup() throws Exception {
        String ip = "localhost";
        int port = 27017;
        IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
                .net(new Net(ip, port, Network.localhostIsIPv6()))
                .build();
        MongodStarter starter = MongodStarter.getDefaultInstance();
        mongodExecutable = starter.prepare(mongodConfig);
        mongodExecutable.start();
        mongoTemplate = new MongoTemplate(MongoClients.create(String.format(CONNECTION_STRING, ip, port)), "test");
    }

    @Test
    void shouldCreateARating() throws Exception {

        String email = "armando343@gmail.com";
        String department = "ABCD";
        CreateUserDTO user = new CreateUserDTO(email, "Armando", "12345", "Masculino", "password");
        createUser(user);
        CreatePlaceDTO place = new CreatePlaceDTO("Finca pepe", department, "Suescas123", "direccion4", "finca linda", "hola.png", 3, 2, 1);
        createPlace(place, email);
        MvcResult result = mockMvc.perform(get("/api/v1/places?search="+department))
                .andExpect(status().isOk())
                .andReturn();
        String bodyResult = result.getResponse().getContentAsString();
        JSONObject object = new JSONArray(bodyResult).getJSONObject(0);
        CreatePlaceDTO placeDTO = gson.fromJson(object.toString(), CreatePlaceDTO.class);
        String id = placeDTO.getId();

        RatingDTO rating = new RatingDTO("aa",1);
        String idr=rating.getId();

        MvcResult result1 = mockMvc.perform(post("/api/v1/ratings?placeId="+id).header("X-Email", "email123@gmail.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(rating)))
                .andExpect(status().isCreated())
                .andReturn();

        Assertions.assertEquals(201, result1.getResponse().getStatus());
    }

    @Test
    void shouldGetRatingsOfAPlace() throws Exception {
        //Create user and place
        String email = "nico989@gmail.com";
        String department = "Choco";
        CreateUserDTO user = new CreateUserDTO(email, "Nicolas", "123456", "male", "password");
        createUser(user);
        CreatePlaceDTO place = new CreatePlaceDTO("Finca Nicolas989", department, "Atlantida123", "direccion6", "finca bella", "nico.png", 3, 2, 1);
        createPlace(place, email);
        MvcResult result = mockMvc.perform(get("/api/v1/places?search="+department))
                .andExpect(status().isOk())
                .andReturn();
        //Create rating
        String bodyResult = result.getResponse().getContentAsString();
        JSONObject object = new JSONArray(bodyResult).getJSONObject(0);
        CreatePlaceDTO placeDTO = gson.fromJson(object.toString(), CreatePlaceDTO.class);
        String id = placeDTO.getId();
        RatingDTO rating = new RatingDTO("Buen lugar y muy comodo",5);
        mockMvc.perform(post("/api/v1/ratings?placeId="+id).header("X-Email", "emaildcxz.@gmail.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(rating)))
                .andExpect(status().isCreated());
        //Get ratings
        MvcResult result3 = mockMvc.perform(get("/api/v1/ratings?placeId="+id))
                .andExpect(status().isAccepted())
                .andReturn();
        String bodyResult3 = result3.getResponse().getContentAsString();
        JSONObject object3 = new JSONArray(bodyResult3).getJSONObject(0);
        RatingDTO ratingDTO = gson.fromJson(object3.toString(), RatingDTO.class);
        Assertions.assertEquals(202, result3.getResponse().getStatus());
        Assertions.assertEquals("Buen lugar y muy comodo",ratingDTO.getComment());
        Assertions.assertEquals(5,ratingDTO.getQualification());
    }

    @Test
    void shouldNotGetRatingsOfNoPlace() throws Exception {
        try {
            mockMvc.perform(get("/api/v1/ratings?placeId=" + "84773838"))
                    .andExpect(status().isAccepted());
            Assertions.assertTrue(false);
        } catch(Exception e){
            Assertions.assertEquals("Request processing failed; nested exception is edu.eci.ieti.envirify.exceptions.EnvirifyPersistenceException: There is no place with the id 84773838",e.getMessage());
        }
    }

    @Test
    void shouldNotCreateARatingOfNoPlace() throws Exception {
        String id = "NoExisteee1245";
        RatingDTO rating = new RatingDTO("aa",1);
        MvcResult result = mockMvc.perform(post("/api/v1/ratings?placeId="+id).header("X-Email", "emailgfd@gmail.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(rating)))
                .andExpect(status().isNotFound())
                .andReturn();
        String bodyResult = result.getResponse().getContentAsString();
        Assertions.assertEquals("There is no place with the id " + id, bodyResult);
    }

    private void createUser(CreateUserDTO userDTO) throws Exception {
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(userDTO)))
                .andExpect(status().isCreated());
    }

    private void createPlace(CreatePlaceDTO placeDTO, String userEmail) throws Exception {
        mockMvc.perform(post("/api/v1/places").header("X-Email", userEmail)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(placeDTO)))
                .andExpect(status().isCreated());
    }
}
