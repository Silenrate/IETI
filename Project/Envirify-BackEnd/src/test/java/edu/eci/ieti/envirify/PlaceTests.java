package edu.eci.ieti.envirify;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
import edu.eci.ieti.envirify.controllers.dtos.UserDTO;
import edu.eci.ieti.envirify.exceptions.EnvirifyException;
import edu.eci.ieti.envirify.exceptions.EnvirifyPersistenceException;
import edu.eci.ieti.envirify.model.Place;
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
class PlaceTests {

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
    void shouldCreateAPlace() throws Exception {

        String email = "daniela@gmail.com";
        CreateUserDTO user = new CreateUserDTO(email, "Daniela", "12345", "Masculino", "password");

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user)))
                .andExpect(status().isCreated());

        CreatePlaceDTO place = new CreatePlaceDTO("Finca pepe", "Cund", "Bog", "km 1 via tabio(finca pepe)", "finca linda", "hola.png", 3, 2, 1);


        MvcResult result1 = mockMvc.perform(post("/api/v1/places").header("X-Email", email)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(place)))
                .andExpect(status().isCreated())
                .andReturn();

        Assertions.assertEquals(201, result1.getResponse().getStatus());
    }

    @Test
    void shouldNotAddAnExistingAPlace() throws Exception {
        String direction = "calle 1 # 12-34";
        String city = "Bog";

        String email = "daniela1@gmail.com";
        CreateUserDTO user = new CreateUserDTO(email, "Daniela", "12345", "Masculino", "password");

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user)))
                .andExpect(status().isCreated());

        CreatePlaceDTO place = new CreatePlaceDTO("casa colonial", "Cund", city, direction, "finca linda", "hola.png", 3, 2, 1);

        mockMvc.perform(post("/api/v1/places").header("X-Email", email)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(place)))
                .andExpect(status().isCreated());

        MvcResult result = mockMvc.perform(post("/api/v1/places").header("X-Email", email)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(place)))
                .andExpect(status().isConflict())
                .andDo(print())
                .andReturn();
        Assertions.assertEquals("There is already a place with the " + direction + "-" + city + "-address", result.getResponse().getContentAsString());
    }

    @Test
    void shouldAddAnExistingADirectionDifferentCity() throws Exception {

        String email = "daniela2@gmail.com";
        CreateUserDTO user = new CreateUserDTO(email, "Daniela", "12345", "Masculino", "password");

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user)))
                .andExpect(status().isCreated());

        String direction = "calle 2 # 12-34";
        String city = "Bog";
        CreatePlaceDTO place = new CreatePlaceDTO("casa colonial", "Cund", city, direction, "finca linda", "hola.png", 3, 2, 1);
        mockMvc.perform(post("/api/v1/places").header("X-Email", email)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(place)))
                .andExpect(status().isCreated());
        place.setCity("Med");
        MvcResult result = mockMvc.perform(post("/api/v1/places").header("X-Email", email)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(place)))
                .andExpect(status().isCreated())
                .andReturn();

        Assertions.assertEquals(201, result.getResponse().getStatus());
    }

    @Test
    void shouldNotCreateAPlaceWithoutUser() throws Exception {
        CreatePlaceDTO place = new CreatePlaceDTO("Finca pepe", "Cund", "Bog", "km 2 via tabio(finca pepe)", "finca linda", "hola.png", 3, 2, 1);
        MvcResult result1 = mockMvc.perform(post("/api/v1/places").header("X-Email", "noexiste@gmail.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(place)))
                .andExpect(status().isConflict())
                .andReturn();
        String responseBody = result1.getResponse().getContentAsString();
        Assertions.assertEquals("There is no user with the email address " + "noexiste@gmail.com", responseBody);
    }

    @Test
    void shouldGetPlacesByDepartment() throws Exception {
        String email = "armando@gmail.com";
        String department = "Cundinamarca";
        CreateUserDTO user = new CreateUserDTO(email, "Armando", "12345", "Masculino", "password");
        createUser(user);
        CreatePlaceDTO place = new CreatePlaceDTO("Finca pepe", department, "Suesca", "direccion", "finca linda", "hola.png", 3, 2, 1);
        createPlace(place, email);
        place.setCity("Cogua");
        createPlace(place, email);
        MvcResult result = mockMvc.perform(get("/api/v1/places?search="+department))
                .andExpect(status().isOk())
                .andReturn();
        String bodyResult = result.getResponse().getContentAsString();
        JSONArray array = new JSONArray(bodyResult);
        CreatePlaceDTO placeDTO;
        for (int i = 0; i < array.length(); i++) {
            placeDTO = gson.fromJson(array.getJSONObject(i).toString(), CreatePlaceDTO.class);
            Assertions.assertEquals(department, placeDTO.getDepartment());
        }
    }

    @Test
    void shouldGetPlacesByCity() throws Exception {
        String email = "armando2@gmail.com";
        String search = "Cajica";
        CreateUserDTO user = new CreateUserDTO(email, "Armando", "12345", "Masculino", "password");
        createUser(user);
        CreatePlaceDTO place = new CreatePlaceDTO("Finca pepe", "Boyaca", search, "direccion1", "finca linda", "hola.png", 3, 2, 1);
        place.setOwner(email);
        createPlace(place, email);
        place.setDirection("direccion2");
        place.setDepartment(search);
        place.setCity("ciudad");
        createPlace(place, email);
        place.setDirection("direccion3");
        place.setDepartment(search);
        place.setCity(search);
        createPlace(place, email);
        MvcResult result = mockMvc.perform(get("/api/v1/places?search="+search))
                .andExpect(status().isOk())
                .andReturn();
        String bodyResult = result.getResponse().getContentAsString();
        JSONArray array = new JSONArray(bodyResult);
        CreatePlaceDTO placeDTO;
        for (int i = 0; i < array.length(); i++) {
            placeDTO = gson.fromJson(array.getJSONObject(i).toString(), CreatePlaceDTO.class);
            Assertions.assertTrue(placeDTO.getCity().equals(search) || placeDTO.getDepartment().equals(search));
        }
    }

    @Test
    void shouldNotGetNonExistentPlacesByDepartmentOrByCity() throws Exception {
        String search = "NoExiste";
        MvcResult result = mockMvc.perform(get("/api/v1/places?search="+search))
                .andExpect(status().isNotFound())
                .andReturn();
        String bodyResult = result.getResponse().getContentAsString();
        Assertions.assertEquals("There are no results for " + search, bodyResult);
    }

    @Test
    void shouldGetPlacesById() throws Exception {
        String email = "armando3@gmail.com";
        String department = "ABC";
        CreateUserDTO user = new CreateUserDTO(email, "Armando", "12345", "Masculino", "password");
        createUser(user);
        CreatePlaceDTO place = new CreatePlaceDTO("Finca pepe", department, "Suescas", "direccion4", "finca linda", "hola.png", 3, 2, 1);
        createPlace(place, email);
        MvcResult result = mockMvc.perform(get("/api/v1/places?search="+department))
                .andExpect(status().isOk())
                .andReturn();
        String bodyResult = result.getResponse().getContentAsString();
        JSONObject object = new JSONArray(bodyResult).getJSONObject(0);
        CreatePlaceDTO placeDTO = gson.fromJson(object.toString(), CreatePlaceDTO.class);
        String id = placeDTO.getId();
        result = mockMvc.perform(get("/api/v1/places/"+id))
                .andExpect(status().isOk())
                .andReturn();
        bodyResult = result.getResponse().getContentAsString();
        placeDTO = gson.fromJson(bodyResult,CreatePlaceDTO.class);
        Assertions.assertEquals(place.getCapacity(),placeDTO.getCapacity());
        Assertions.assertEquals(place.getHabitations(),placeDTO.getHabitations());
        Assertions.assertEquals(place.getBathrooms(),placeDTO.getBathrooms());
        Assertions.assertEquals(place.getName(),placeDTO.getName());
        Assertions.assertEquals(place.getDepartment(),placeDTO.getDepartment());
        Assertions.assertEquals(place.getCity(),placeDTO.getCity());
        Assertions.assertEquals(place.getDescription(),placeDTO.getDescription());
        Assertions.assertEquals(place.getDirection(),placeDTO.getDirection());
        Assertions.assertEquals(place.getUrlImage(),placeDTO.getUrlImage());
        Assertions.assertEquals(place.getRatings(),placeDTO.getRatings());
    }

    @Test
    void shouldNotGetANonExistentPlaceById() throws Exception {
        String id = "NoExiste";
        MvcResult result = mockMvc.perform(get("/api/v1/places/"+id))
                .andExpect(status().isNotFound())
                .andReturn();
        String bodyResult = result.getResponse().getContentAsString();
        Assertions.assertEquals("There is no place with the id " + id, bodyResult);
    }

    @Test
    void shouldGetPlacesOfUser() throws Exception {

        String email = "danielita@gmail.com";
        String department = "ABC";
        CreateUserDTO user = new CreateUserDTO(email, "Armando", "12345", "Masculino", "password");
        createUser(user);
        CreatePlaceDTO place = new CreatePlaceDTO("Finca pepe", department, "Zipa", "direccion45", "finca linda", "hola.png", 3, 2, 1);
        createPlace(place, email);
        MvcResult result = mockMvc.perform(get("/api/v1/places/myplaces").header("X-Email", email))
                .andExpect(status().isOk())
                .andReturn();

        String bodyResult = result.getResponse().getContentAsString();
        JSONObject object = new JSONArray(bodyResult).getJSONObject(0);
        CreatePlaceDTO placeDTO = gson.fromJson(object.toString(), CreatePlaceDTO.class);
        Assertions.assertEquals(place.getCapacity(),placeDTO.getCapacity());
        Assertions.assertEquals(place.getHabitations(),placeDTO.getHabitations());
        Assertions.assertEquals(place.getBathrooms(),placeDTO.getBathrooms());
        Assertions.assertEquals(place.getName(),placeDTO.getName());
        Assertions.assertEquals(place.getDepartment(),placeDTO.getDepartment());
        Assertions.assertEquals(place.getCity(),placeDTO.getCity());
        Assertions.assertEquals(place.getDescription(),placeDTO.getDescription());
        Assertions.assertEquals(place.getDirection(),placeDTO.getDirection());
        Assertions.assertEquals(place.getUrlImage(),placeDTO.getUrlImage());
        Assertions.assertEquals(place.getRatings(),placeDTO.getRatings());

        Assertions.assertEquals(200, result.getResponse().getStatus());


    }

    @Test
    void shouldGetEmptyPlacesOfUser() throws Exception {

        String email = "danielitaasadx123@gmail.com";
        CreateUserDTO user = new CreateUserDTO(email, "Armando", "12345", "Masculino", "password");
        createUser(user);
        MvcResult result = mockMvc.perform(get("/api/v1/places/myplaces").header("X-Email", email))
                .andExpect(status().isNotFound())
                .andReturn();

        String bodyResult = result.getResponse().getContentAsString();
        Assertions.assertEquals("There are no places created by you", bodyResult);

    }

    @Test
    void shouldNotGetPlacesOfUser() throws Exception {

        String email = "noExiste";
        CreateUserDTO user = new CreateUserDTO(email, "Armando", "12345", "Masculino", "password");
        createUser(user);
        mockMvc.perform(get("/api/v1/places/myplaces").header("X-Email", email))
                .andExpect(status().isNotFound())
                .andReturn();
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
    
    @Test
    void shouldNotDeletePlaceUserNotExist() throws Exception {
    	String email = "giselle@gmail.com";
        CreateUserDTO user = new CreateUserDTO(email, "Giselle", "12345", "Femenino", "password");
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user)))
                .andExpect(status().isCreated());
        CreatePlaceDTO place = new CreatePlaceDTO("Villa Las Juanas", "Cund", "Bog", "vereda carrizal", "villa linda", "hola.png", 3, 2, 1);
        MvcResult result1 = mockMvc.perform(post("/api/v1/places").header("X-Email", email)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(place)))
                .andExpect(status().isCreated())
                .andReturn();
        MvcResult result = mockMvc.perform(get("/api/v1/places/myplaces").header("X-Email", email))
                .andExpect(status().isOk())
                .andReturn();
        String badEmail = "fail";
        String bodyResult = result.getResponse().getContentAsString();
        JSONObject object = new JSONArray(bodyResult).getJSONObject(0);
        CreatePlaceDTO placeDTO = gson.fromJson(object.toString(), CreatePlaceDTO.class);
        String id = placeDTO.getId();
        MvcResult ans =mockMvc.perform(delete("/api/v1/places/"+id).header("X-Email", badEmail))
                .andExpect(status().isNotFound())
                .andReturn();
        String responseBody = ans.getResponse().getContentAsString();
        Assertions.assertEquals("There is no user with the email address " + "fail", responseBody);
    }

    @Test
    void shouldNotDeletePlaceBecauseNotExit() throws Exception {
    	String email = "giselleee@gmail.com";
        CreateUserDTO user = new CreateUserDTO(email, "Giselle", "12345", "Femenino", "password");
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user)))
                .andExpect(status().isCreated());
        String id = "0";
        MvcResult ans =mockMvc.perform(delete("/api/v1/places/"+id).header("X-Email", email))
                .andExpect(status().isNotFound())
                .andReturn();
        String responseBody = ans.getResponse().getContentAsString();
        Assertions.assertEquals("There is no place with the id " + id, responseBody);
     }
    
    @Test
    void shouldDeletePlaceOfUser() throws Exception {
    	String email = "adelaida@gmail.com";
        CreateUserDTO user = new CreateUserDTO(email, "Adelaida", "12345", "Femenino", "password");
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user)))
                .andExpect(status().isCreated());
        CreatePlaceDTO place = new CreatePlaceDTO("Villa Las Juanas 2", "Cund", "Bog", "vereda carrizal 2", "villa linda 2", "hola.png", 3, 2, 1);
        MvcResult result1 = mockMvc.perform(post("/api/v1/places").header("X-Email", email)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(place)))
                .andExpect(status().isCreated())
                .andReturn();
        MvcResult result = mockMvc.perform(get("/api/v1/places/myplaces").header("X-Email", email))
                .andExpect(status().isOk())
                .andReturn();
        String bodyResult = result.getResponse().getContentAsString();
        JSONObject object = new JSONArray(bodyResult).getJSONObject(0);
        CreatePlaceDTO placeDTO = gson.fromJson(object.toString(), CreatePlaceDTO.class);
        String id = placeDTO.getId();
        MvcResult ans =mockMvc.perform(delete("/api/v1/places/"+id).header("X-Email", email))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals(200,ans.getResponse().getStatus());	
    }

    @Test
    void shouldUpdateAPlace() throws Exception {
        String email = "nata@gmail.com";
        CreateUserDTO user = new CreateUserDTO(email, "Natalia", "12345", "Masculino", "password");
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user)))
                .andExpect(status().isCreated());
        CreatePlaceDTO place = new CreatePlaceDTO("Finca nata", "Nat", "Bog", "km 1 via tabio(finca Nat)", "finca hermosa", "nico.png", 3, 2, 1);
        MvcResult result1 = mockMvc.perform(post("/api/v1/places").header("X-Email", email)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(place)))
                .andExpect(status().isCreated())
                .andReturn();
        MvcResult result = mockMvc.perform(get("/api/v1/places?search=Nat"))
                .andExpect(status().isOk())
                .andReturn();
        String bodyResult = result.getResponse().getContentAsString();
        JSONObject object = new JSONArray(bodyResult).getJSONObject(0);
        CreatePlaceDTO placeDTO = gson.fromJson(object.toString(), CreatePlaceDTO.class);
        placeDTO.setCity("Buc");
        placeDTO.setName("Javier");
        placeDTO.setDepartment("Cat");
        MvcResult result2 = mockMvc.perform(put("/api/v1/places/"+placeDTO.getId()).header("X-Email", email)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(placeDTO)))
                .andExpect(status().isCreated())
                .andReturn();
        String bodyResponse2 = result2.getResponse().getContentAsString();
        Place updatedPlace = gson.fromJson(bodyResponse2, Place.class);
        Assertions.assertEquals(placeDTO.getCity(),updatedPlace.getCity());
        Assertions.assertEquals(placeDTO.getName(),updatedPlace.getName());
        Assertions.assertEquals(placeDTO.getDepartment(),updatedPlace.getDepartment());
    }

    @Test
    void shouldNotUpdateAPlace() throws Exception {
        try {
            String email = "rafael@gmail.com";
            CreateUserDTO user = new CreateUserDTO(email, "Rafael", "12345", "Masculino", "password");
            mockMvc.perform(post("/api/v1/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(user)))
                    .andExpect(status().isCreated());
            CreatePlaceDTO placeDTO = new CreatePlaceDTO("Finca rafael", "Raf", "Bog", "km 1 via tabio(finca Rafael)", "finca hermosa", "rafael.png", 3, 2, 1);
            MvcResult result = mockMvc.perform(put("/api/v1/places/" + 1).header("X-Email", email)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(placeDTO)))
                    .andExpect(status().isCreated())
                    .andReturn();
        } catch (Exception e){
            String expected = "Request processing failed; nested exception is edu.eci.ieti.envirify.exceptions.EnvirifyPersistenceException: There is no place with the id 1";
            Assertions.assertEquals(expected,e.getMessage());
        }
    }

}
