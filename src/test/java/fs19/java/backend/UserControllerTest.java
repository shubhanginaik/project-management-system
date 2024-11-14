package fs19.java.backend;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import fs19.java.backend.application.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private UserDTO userDTO;

  @BeforeEach
  void setUp() {
    userDTO = UserDTO.builder()
        .firstName("John")
        .lastName("Doe")
        .email("john.doe@example.com")
        .password("password123")
        .profileImage("profile.jpg")
        .phone("123456")
        .build();
  }

  @Test
  void shouldCreateUserOnPost() throws Exception {
    // Act and Assert
    performPostUser(userDTO)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.code", is(201)))
        .andExpect(jsonPath("$.data.firstName", is("John")));

    mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", is(200)))
        .andExpect(jsonPath("$.data").isArray())
        .andExpect(jsonPath("$.data.size()", is(1)))
        .andExpect(jsonPath("$.data[0].firstName", is("John")))
        .andExpect(jsonPath("$.data[0].lastName", is("Doe")));
  }

  @Test
  void shouldGetAllUsersOnGet() throws Exception {
    performPostUser(userDTO).andExpect(status().isCreated());
    performPostUser(userDTO).andExpect(status().isCreated());

    mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", is(200)))
        .andExpect(jsonPath("$.data").isArray());
  }

  @Test
  void shouldGetUserByIdOnGet() throws Exception {
    String response = performPostUser(userDTO)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.data.firstName", is("John")))
        .andDo(print())
        .andReturn()
        .getResponse()
        .getContentAsString();

    // Extract the ID from the response
    String userId = JsonPath.parse(response).read("$.data.id");

    mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/users/{id}", userId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.firstName", is("John")));
  }

  @Test
  void shouldDeleteUserOnDelete() throws Exception {
    String response = performPostUser(userDTO)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.data.firstName", is("John")))
        .andDo(print())
        .andReturn()
        .getResponse()
        .getContentAsString();

    // Extract the ID from the response
    String userId = JsonPath.parse(response).read("$.data.id");
    mockMvc.perform(MockMvcRequestBuilders.delete("/v1/api/users/{id}", userId))
        .andExpect(status().isNoContent());
  }

  @Test
  void shouldUpdateUserOnPut() throws Exception {
    String response = performPostUser(userDTO)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.data.firstName", is("John")))
        .andDo(print())
        .andReturn()
        .getResponse()
        .getContentAsString();

    // Extract the ID from the response
    String userId = JsonPath.parse(response).read("$.data.id");

    userDTO.setPhone("98765432");
    mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/users/{id}", userId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.phone", is("98765432")));
  }

  private ResultActions performPostUser(UserDTO userDTO) throws Exception {
    return mockMvc.perform(post("/v1/api/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userDTO)));
  }
}