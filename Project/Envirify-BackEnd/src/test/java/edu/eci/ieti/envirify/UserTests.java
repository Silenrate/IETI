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
import edu.eci.ieti.envirify.controllers.dtos.BookDTO;
import edu.eci.ieti.envirify.controllers.dtos.CreatePlaceDTO;
import edu.eci.ieti.envirify.controllers.dtos.CreateUserDTO;
import edu.eci.ieti.envirify.controllers.dtos.LoginDTO;
import edu.eci.ieti.envirify.controllers.dtos.UserDTO;
import edu.eci.ieti.envirify.security.jwt.JwtResponse;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

@SpringBootTest
@AutoConfigureMockMvc
class UserTests {

    @Autowired
    private MockMvc mockMvc;

    private static final Gson gson = new Gson();
    
    private static final long DAY_IN_MILLISECONDS = 86400000;
    
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

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
    void shouldCreateAUser() throws Exception {
        CreateUserDTO user = new CreateUserDTO("daniel@gmail.com", "Daniel", "12345", "Masculino", "password");
        MvcResult result = mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user)))
                .andExpect(status().isCreated())
                .andReturn();
        Assertions.assertEquals(201, result.getResponse().getStatus());
    }

    @Test
    void shouldGetAUserById() throws Exception {
        String email = "edscx@gmail.com";
        CreateUserDTO user = new CreateUserDTO(email, "Daniel", "12345", "Masculino", "password");
        createUser(user);
        MvcResult result = mockMvc.perform(get("/api/v1/users/" + email))
                .andExpect(status().isAccepted())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        UserDTO returnedUser = gson.fromJson(responseBody, UserDTO.class);
        String id =returnedUser.getId();
        MvcResult result1 = mockMvc.perform(get("/api/v1/users/id/" + id))
                .andExpect(status().isAccepted())
                .andReturn();

    }

    @Test
    void shouldNotGetAUserById() throws Exception {
        String email = "edsdsccx@gmail.com";
        CreateUserDTO user = new CreateUserDTO(email, "Daniel", "12345", "Masculino", "password");
        createUser(user);
        MvcResult result = mockMvc.perform(get("/api/v1/users/" + email))
                .andExpect(status().isAccepted())
                .andReturn();
        String id ="no";
        MvcResult result1 = mockMvc.perform(get("/api/v1/users/id/" + id))
                .andExpect(status().isNotFound())
                .andReturn();
        String responseBody1 = result1.getResponse().getContentAsString();
        Assertions.assertEquals("There is no user", responseBody1);

    }

    @Test
    void shouldNotAddAnExistingAUser() throws Exception {
        String email = "daniel2@gmail.com";
        CreateUserDTO user = new CreateUserDTO(email, "Daniel", "12345", "Masculino", "password");
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user)))
                .andExpect(status().isCreated());
        MvcResult result = mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user)))
                .andExpect(status().isConflict())
                .andReturn();
        Assertions.assertEquals("There is already a user with the " + email + " email address", result.getResponse().getContentAsString());
    }

    @Test
    void shouldGetAUserByEmail() throws Exception {
        String email = "daniel3@gmail.com";
        CreateUserDTO user = new CreateUserDTO(email, "Daniel", "12345", "Masculino", "password");
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user)))
                .andExpect(status().isCreated());
        MvcResult result = mockMvc.perform(get("/api/v1/users/" + email))
                .andExpect(status().isAccepted())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        UserDTO returnedUser = gson.fromJson(responseBody, UserDTO.class);
        Assertions.assertEquals(user.getEmail(), returnedUser.getEmail());
        Assertions.assertEquals(user.getName(), returnedUser.getName());
        Assertions.assertEquals(user.getPhoneNumber(), returnedUser.getPhoneNumber());
        Assertions.assertEquals(user.getGender(), returnedUser.getGender());
    }

    @Test
    void shouldNotGetANonExistentUser() throws Exception {
        String email = "noexiste@gmail.com";
        MvcResult result = mockMvc.perform(get("/api/v1/users/" + email))
                .andExpect(status().isNotFound())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        Assertions.assertEquals("There is no user with the email address " + email, responseBody);
    }


 
	@Test
    void shouldUpdateUserWithValidEmail() throws Exception {
		String email = "daniella@gmail.com";
        CreateUserDTO user = new CreateUserDTO(email, "Dani", "12345", "Femenino", "password");
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user)))
                .andExpect(status().isCreated());
        user.setName("Danniella");
        MvcResult result = mockMvc.perform(put("/api/v1/users/"+email)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user) ))
                .andExpect(status().isAccepted())
                .andReturn();
        Assertions.assertEquals(202, result.getResponse().getStatus());
    }


    @Test
    void shouldNotGetBooksOfANonExistentUser() throws Exception {
        String email = "fake1dsd@gmail.com";
        MvcResult result = mockMvc.perform(get("/api/v1/users/" + email + "/books"))
                .andExpect(status().isNotFound())
                .andReturn();
        Assertions.assertEquals("There is no user with the email address " + email, result.getResponse().getContentAsString());


    }

	@Test
    void shouldNotGetBookingsOfANonExistentUser() throws Exception {
        String email = "fakfdvce@gmail.com";
        MvcResult result = mockMvc.perform(get("/api/v1/users/" + email + "/bookings"))
                .andExpect(status().isNotFound())
                .andReturn();
        Assertions.assertEquals("There is no user with the email address " + email, result.getResponse().getContentAsString());
        
        
    }

    @Test
    void shouldGetAUserBooks() throws Exception {
        String email = "fabiodcbbvx@gmail.com";
        String email1 = "fabidsdccdcx@gmail.com";
        String department = "Algunoccdc";
        CreateUserDTO user = new CreateUserDTO(email, "Fabio", "12345", "Masculino", "password");
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user)))
                .andExpect(status().isCreated());

        CreateUserDTO user1 = new CreateUserDTO(email1, "Fabio", "12345", "Masculino", "password");
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user1)))
                .andExpect(status().isCreated());

        CreatePlaceDTO place = new CreatePlaceDTO("Finca PRUEBA", department, "pvvrueba", "alguna direccion", "finca bien nice", "hola.png", 3, 2, 1);
        createPlace(place, email);
        String token = loginUser(email, user.getPassword());
        String placeId = getPlaceId(department);
        BookDTO bookDTO = new BookDTO(getDate(2), getDate(4), placeId);
        mockMvc.perform(post("/api/v1/books")
                .header("Authorization", token)
                .header("X-Email", email1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getBookJSON(bookDTO)))
                .andExpect(status().isCreated());
        MvcResult result = mockMvc.perform(get("/api/v1/users/" + email + "/books"))
                .andExpect(status().isAccepted())
                .andReturn();
        Assertions.assertEquals(202, result.getResponse().getStatus());
    }
	
	@Test
    void shouldNotGetBookingsOfAUserWithoutBookings() throws Exception {
		String email = "correcto@gmail.com";
        CreateUserDTO user = new CreateUserDTO(email, "Correcto", "12345", "Femenino", "password");
        createUser(user);
        MvcResult result = mockMvc.perform(get("/api/v1/users/" + email + "/bookings"))
                .andExpect(status().isNotFound())
                .andReturn();
        Assertions.assertEquals("There user with the email address " + email +" don't have bookings", result.getResponse().getContentAsString());
    }
	
	@Test
    void shouldGetAUserBookings() throws Exception {
		String email = "fabio@gmail.com";
        String department = "Alguno";
		CreateUserDTO user = new CreateUserDTO(email, "Fabio", "12345", "Masculino", "password");
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user)))
                .andExpect(status().isCreated()); 
        
        CreatePlaceDTO place = new CreatePlaceDTO("Finca PRUEBA", department, "prueba", "alguna direccion", "finca bien nice", "hola.png", 3, 2, 1);
        createPlace(place, email);
        String token = loginUser(email, user.getPassword());
        String placeId = getPlaceId(department);
        BookDTO bookDTO = new BookDTO(getDate(2), getDate(4), placeId);
        mockMvc.perform(post("/api/v1/books")
                .header("Authorization", token)
                .header("X-Email", email)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getBookJSON(bookDTO)))
                .andExpect(status().isCreated());
        MvcResult result = mockMvc.perform(get("/api/v1/users/" + email + "/bookings"))
                .andExpect(status().isAccepted())
                .andReturn();
        Assertions.assertEquals(202, result.getResponse().getStatus());
    }
	
	private void createPlace(CreatePlaceDTO placeDTO, String userEmail) throws Exception {
        mockMvc.perform(post("/api/v1/places").header("X-Email", userEmail)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(placeDTO)))
                .andExpect(status().isCreated());
    }
	
	private String getPlaceId(String department) throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/places?search=" + department))
                .andExpect(status().isOk())
                .andReturn();
        String bodyResult = result.getResponse().getContentAsString();
        JSONObject object = new JSONArray(bodyResult).getJSONObject(0);
        CreatePlaceDTO placeDTO = gson.fromJson(object.toString(), CreatePlaceDTO.class);
        return placeDTO.getId();
    }

    private String loginUser(String email, String password) throws Exception {
        LoginDTO loginDTO = new LoginDTO(email, password);
        MvcResult result = mockMvc.perform(post("/api/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(loginDTO)))
                .andExpect(status().isAccepted())
                .andReturn();
        String bodyResult = result.getResponse().getContentAsString();
        JwtResponse response = gson.fromJson(bodyResult, JwtResponse.class);
        return "Bearer " + response.getJwt();
    }
    
    private String getBookJSON(BookDTO bookDTO) {
        String placeIdLine = "null";
        if (bookDTO.getPlaceId() != null) {
            placeIdLine = "\"" + bookDTO.getPlaceId() + "\"";
        }
        return "{\"initialDate\":\"" + formatter.format(bookDTO.getInitialDate()) + "\",\n" +
                "    \"finalDate\": \"" + formatter.format(bookDTO.getFinalDate()) + "\",\n" +
                "    \"placeId\": " + placeIdLine + "\n" +
                "}";
    }
    
    private Date getDate(int days) {
        Date actualDate = new Date();
        return new Date(actualDate.getTime() + (days * DAY_IN_MILLISECONDS));
    }
    
    private void createUser(CreateUserDTO userDTO) throws Exception {
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(userDTO)))
                .andExpect(status().isCreated());
    }

}
