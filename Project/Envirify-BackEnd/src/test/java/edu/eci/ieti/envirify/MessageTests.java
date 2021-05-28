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
import edu.eci.ieti.envirify.controllers.dtos.*;
import edu.eci.ieti.envirify.security.jwt.JwtResponse;
import org.json.JSONException;
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
import java.text.SimpleDateFormat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MessageTests {

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
    void shouldAddAMessage() throws Exception {
        String email = "test@gmail.com";
        CreateUserDTO user = new CreateUserDTO(email, "Test", "12345", "male", "password");
        CreateUserDTO user2 = new CreateUserDTO("test2@gmail.com", "Test2", "12345", "male", "password");
        createUser(user);
        createUser(user2);
        String token = loginUser(email, user.getPassword());
        MessageDTO messageDTO = new MessageDTO("New Message",user.getEmail(),user2.getEmail(),user.getEmail()+user2.getEmail());
        MvcResult result = mockMvc.perform(post("/api/v1/messages")
                .header("Authorization", token)
                .header("X-Email", email)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getMessageJSON(messageDTO)))
                .andExpect(status().isCreated())
                .andReturn();
        Assertions.assertEquals(201, result.getResponse().getStatus());
    }
    
    @Test
    void shouldGetMessagesOfAUser() throws Exception {
        String email = "testPrueba@gmail.com";
        CreateUserDTO user = new CreateUserDTO(email, "Testtt", "12345", "male", "password");
        CreateUserDTO user2 = new CreateUserDTO("test2Prueba@gmail.com", "Testtt", "12345", "male", "password");
        createUser(user);
        createUser(user2);
        String token = loginUser(email, user.getPassword());
        MessageDTO messageDTO = new MessageDTO("Hooola",user.getEmail(),user2.getEmail(),user.getEmail()+user2.getEmail());
        mockMvc.perform(post("/api/v1/messages")
                .header("Authorization", token)
                .header("X-Email", email)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getMessageJSON(messageDTO)))
                .andExpect(status().isCreated())
                .andReturn();
        MvcResult result = mockMvc.perform(get("/api/v1/messages/"+email+"/chats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getMessageJSON(messageDTO)))
                .andExpect(status().isOk())
                .andReturn();
        
    }

    @Test
    void shouldNotAddMessageFromNonExistentUsers() throws Exception {
        String email = "a@gmail.com";
        try {
            MessageDTO messageDTO = new MessageDTO("New Message", email, "b@gmail.com", "a@gmail.comb@gmail.com");
            MvcResult result = mockMvc.perform(post("/api/v1/messages")
                    .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTYxNzAzMjU1NiwiZXhwIjoxNjE3MTE4OTU2fQ.-prthEdpzVwl2LeKDT5GhW7sNSymiyGvsapD7kbZlm1Gl6hcBEmopoktPgb1hCHAkD5OEtr2QVnGEfvI_ojPXg")
                    .header("X-Email", email)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(getMessageJSON(messageDTO)))
                    .andExpect(status().isCreated())
                    .andReturn();
            Assertions.assertEquals(500, result.getResponse().getStatus());
        } catch(Exception ex){
            Assertions.assertEquals("Request processing failed; nested exception is edu.eci.ieti.envirify.exceptions.EnvirifyPersistenceException: There is no user with the email address "+email+"" , ex.getMessage());
        }
    }

    @Test
    void shouldAUserNotAddMessageFromAnotherUser() throws Exception {
        String email = "Javier@gmail.com";
        CreateUserDTO user = new CreateUserDTO(email, "Javier", "12345", "male", "password");
        CreateUserDTO user2 = new CreateUserDTO("maria@gmail.com", "Test2", "12345", "male", "password");
        CreateUserDTO user3 = new CreateUserDTO("natalia@gmail.com", "Test3", "12345", "male", "password");
        try {
            createUser(user);
            createUser(user2);
            createUser(user3);

            String token = loginUser(email, user.getPassword());
            MessageDTO messageDTO = new MessageDTO("New Message",user3.getEmail(),user2.getEmail(),user.getEmail()+user2.getEmail());
            MvcResult result = mockMvc.perform(post("/api/v1/messages")
                    .header("Authorization", token)
                    .header("X-Email", user.getEmail())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(getMessageJSON(messageDTO)))
                    .andExpect(status().isCreated())
                    .andReturn();
            Assertions.assertEquals(500, result.getResponse().getStatus());
        } catch(Exception ex){
            String expected = "Request processing failed; nested exception is edu.eci.ieti.envirify.exceptions.EnvirifyPersistenceException: The authenticated user: "+user.getEmail()+" is not the same as the user who sent the message :"+user3.getEmail()+"";
            Assertions.assertEquals(expected , ex.getMessage());
        }
    }

    private String getMessageJSON(MessageDTO messageDTO) throws JSONException {
        JSONObject message = new JSONObject();
        message.put("messageDTO", messageDTO.getMessageDTO());
        message.put("senderDTO", messageDTO.getSenderDTO());
        message.put("receiverDTO", messageDTO.getReceiverDTO());
        message.put("channelIdDTO", messageDTO.getChannelIdDTO());
        return String.valueOf(message);
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
