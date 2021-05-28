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
import edu.eci.ieti.envirify.controllers.dtos.CreateUserDTO;
import edu.eci.ieti.envirify.controllers.dtos.LoginDTO;
import edu.eci.ieti.envirify.exceptions.EnvirifyException;
import edu.eci.ieti.envirify.security.jwt.AuthTokenFilter;
import edu.eci.ieti.envirify.security.jwt.JwtResponse;
import edu.eci.ieti.envirify.security.jwt.JwtUtils;
import edu.eci.ieti.envirify.security.userdetails.UserDetailsImpl;
import edu.eci.ieti.envirify.security.userdetails.UserDetailsServiceImpl;
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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Gson gson = new Gson();

    private static final String CONNECTION_STRING = "mongodb://%s:%d";

    private MongodExecutable mongodExecutable;
    private MongoTemplate mongoTemplate;

    @Autowired
    AuthTokenFilter authTokenFilter;

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
    void shouldLogin() {
        CreateUserDTO user = new CreateUserDTO("nicolas@gmail.com", "Nicolas", "12345", "male", "password");
        LoginDTO loginDTO = new LoginDTO("nicolas@gmail.com", "password");
        MvcResult result = null;
        try {
            mockMvc.perform(post("/api/v1/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(user)))
                    .andExpect(status().isCreated())
                    .andReturn();
            result = mockMvc.perform(post("/api/v1/users/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(loginDTO)))
                    .andExpect(status().isAccepted())
                    .andReturn();
            Assertions.assertEquals(202, result.getResponse().getStatus());
        } catch (Exception e) {
            Assertions.assertTrue(false);
        }
    }

    @Test
    void shouldGetJWTResponseAfterLogin() {
        CreateUserDTO user = new CreateUserDTO("jefferson@gmail.com", "Jefferson", "12345", "male", "password");
        LoginDTO loginDTO = new LoginDTO("jefferson@gmail.com", "password");
        try {
            mockMvc.perform(post("/api/v1/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(user)))
                    .andExpect(status().isCreated())
                    .andReturn();
            String result = mockMvc.perform(post("/api/v1/users/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(loginDTO)))
                    .andExpect(status().isAccepted())
                    .andReturn().getResponse().getContentAsString();
            Gson g = new Gson();
            JwtResponse jwtResponse = g.fromJson(result, JwtResponse.class);
            Assertions.assertEquals(jwtResponse.getEmail(), user.getEmail());
            Assertions.assertEquals(jwtResponse.getUsername(), user.getName());
            Assertions.assertTrue(jwtResponse.getId()!=null);

        } catch (Exception e) {
            Assertions.assertTrue(false);
        }
    }

    @Test
    void shouldNotDoLogin() {
        LoginDTO loginDTO = new LoginDTO("nonexistentuser@gmail.com", "password");
        MvcResult result = null;
        try {
            result = mockMvc.perform(post("/api/v1/users/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(loginDTO)))
                    .andExpect(status().isUnauthorized())
                    .andReturn();
            Assertions.assertEquals(401, result.getResponse().getStatus());
        } catch (Exception e) {
            Assertions.assertTrue(false);
        }
    }


    @Test
    void shouldGetUserDetails() throws Exception {
        String id = "1";
        String username = "nicolas9906";
        String email = "nicolas@gmail.com";
        String password = "nicolas123";
        UserDetailsImpl userDetails = new UserDetailsImpl(id, username, email, password);
        Assertions.assertEquals(id, userDetails.getId());
        Assertions.assertEquals(username, userDetails.getUsername());
        Assertions.assertEquals(email, userDetails.getEmail());
        Assertions.assertEquals(password, userDetails.getPassword());
        Assertions.assertEquals(0, userDetails.getAuthorities().size());
        Assertions.assertEquals(true, userDetails.isAccountNonExpired());
        Assertions.assertEquals(true, userDetails.isAccountNonLocked());
        Assertions.assertEquals(true, userDetails.isCredentialsNonExpired());
        Assertions.assertEquals(true, userDetails.isEnabled());
    }

    @Test
    void shouldBuildUserDetails() throws Exception {
        CreateUserDTO user = new CreateUserDTO("andres@gmail.com", "Andres", "12345", "Masculino", "andres123");
        MvcResult result = mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user)))
                .andExpect(status().isCreated())
                .andReturn();
        UserDetails userDetails = userDetailsService.loadUserByUsername("andres@gmail.com");
        Assertions.assertEquals(user.getName(), userDetails.getUsername());
        Assertions.assertEquals(0, userDetails.getAuthorities().size());
        Assertions.assertEquals(true, userDetails.isAccountNonExpired());
        Assertions.assertEquals(true, userDetails.isAccountNonLocked());
        Assertions.assertEquals(true, userDetails.isCredentialsNonExpired());
        Assertions.assertEquals(true, userDetails.isEnabled());
    }

    @Test
    void shouldNotBuildUserDetails() throws Exception {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername("carlos@gmail.com");
        } catch (UsernameNotFoundException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    void shouldGetEmailFromJWTToken() throws Exception {
        LoginDTO loginDTO = new LoginDTO("daniela45@gmail.com", "password");
        CreateUserDTO user = new CreateUserDTO("daniela45@gmail.com", "Daniela", "12345", "male", "password");
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user)))
                .andExpect(status().isCreated());
        String result = mockMvc.perform(post("/api/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(loginDTO)))
                .andExpect(status().isAccepted())
                .andReturn().getResponse().getContentAsString();
        JSONObject json = new JSONObject(result);
        System.out.println(json.get("jwt"));
        Assertions.assertEquals(jwtUtils.getEmailFromJwtToken(json.getString("jwt")), loginDTO.getEmail());
    }

    @Test
    void shouldValidateJWTToken() throws Exception {
        LoginDTO loginDTO = new LoginDTO("jorge@gmail.com", "password");
        CreateUserDTO user = new CreateUserDTO("jorge@gmail.com", "Jorge", "12345", "male", "password");
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user)))
                .andExpect(status().isCreated());
        String result = mockMvc.perform(post("/api/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(loginDTO)))
                .andExpect(status().isAccepted())
                .andReturn().getResponse().getContentAsString();
        JSONObject json = new JSONObject(result);
        Assertions.assertEquals(true,jwtUtils.validateJwtToken(json.getString("jwt")));
    }

    @Test
    void shouldNotValidateJWTToken() throws Exception {
        try {
            Assertions.assertEquals(true,jwtUtils.validateJwtToken("kjfhdskhfwe9w49"));
        } catch (EnvirifyException e){
            Assertions.assertTrue(true);
        }
    }

    @Test
    void shouldParseJWTToken() throws Exception {
        CreateUserDTO user = new CreateUserDTO("pepe@gmail.com", "Pepe", "12345", "male", "password");
        MockHttpServletRequest request = mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user)))
                .andExpect(status().isCreated())
                .andReturn().getRequest();
        request.addHeader("Authorization","Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJuaWNvbGFzQGdtYWlsLmNvbSIsImlhdCI6MTYxNTIzMTQyNSwiZXhwIjoxNjE1MzE3ODI1fQ.SA8j3NBI77pA0Ve1jqKA0mhIU-D89EQmxyVxOKwXBdJHapkNpsYw1LbjZVSyxx1Y_9CNtoBliOgBT55mkpDthw");
        String token = authTokenFilter.parseJwt(request);
        Assertions.assertEquals("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJuaWNvbGFzQGdtYWlsLmNvbSIsImlhdCI6MTYxNTIzMTQyNSwiZXhwIjoxNjE1MzE3ODI1fQ.SA8j3NBI77pA0Ve1jqKA0mhIU-D89EQmxyVxOKwXBdJHapkNpsYw1LbjZVSyxx1Y_9CNtoBliOgBT55mkpDthw",token);
    }

}
