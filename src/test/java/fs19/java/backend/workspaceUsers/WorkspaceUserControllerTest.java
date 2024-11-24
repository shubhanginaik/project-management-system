package fs19.java.backend.workspaceUsers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import fs19.java.backend.application.dto.workspace_user.WorkspaceUserRequestDTO;
import fs19.java.backend.domain.entity.Company;
import fs19.java.backend.domain.entity.enums.WorkspaceType;
import fs19.java.backend.domain.entity.Role;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.domain.entity.Workspace;
import fs19.java.backend.infrastructure.JpaRepositories.CompanyJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.RoleJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.WorkspaceJpaRepo;
import fs19.java.backend.presentation.shared.Utilities.DateAndTime;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WorkspaceUserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UserJpaRepo userRepository;

  @Autowired
  private WorkspaceJpaRepo workspaceRepository;

  @Autowired
  private RoleJpaRepo roleRepository;

  @Autowired
  private CompanyJpaRepo companyJpaRepo;

  private User user;
  private User user2;
  private User user3;
  private User user4;
  private Workspace workspace;
  private Workspace workspace2;
  private Role role;
  private Role role2;
  private WorkspaceUserRequestDTO workspaceUserRequestDTO;
  private WorkspaceUserRequestDTO workspaceUserRequestDTO2;
  private WorkspaceUserRequestDTO workspaceUserRequestDTO3;
  private WorkspaceUserRequestDTO workspaceUserRequestDTO4;

  @BeforeEach
  void setUp() {
    user = new User(
        UUID.randomUUID(),
        "user1",
        "Sony",
        "user1.sony@example.com",
        "password",
        "123456789",
        ZonedDateTime.now(),
        "profile.jpg");
    user = userRepository.findAll().stream().findFirst().orElseGet(() -> userRepository.save(user));



    Company company= new Company(UUID.randomUUID(), "Test Company", ZonedDateTime.now(), user);

    company = companyJpaRepo.save(company);


    workspace = new Workspace();
    workspace.setId(UUID.randomUUID());
    workspace.setName("Test Workspace 1");
    workspace.setDescription("Description");
    workspace.setType(WorkspaceType.PUBLIC);
    workspace.setCreatedBy(user);
    workspace.setCompanyId( company);
    workspace= workspaceRepository.save(workspace);

    role = new Role();
    role.setId(UUID.randomUUID());
    role.setName("Developer");
    role.setCreatedDate(DateAndTime.getDateAndTime());
    role.setCompany(company);
    role = roleRepository.save(role);

    role2 = new Role();
    role2.setId(UUID.randomUUID());
    role2.setName("Role2");
    role2.setCreatedDate(DateAndTime.getDateAndTime());
    role2.setCompany(company);
    role2 = roleRepository.save(role);

    workspaceUserRequestDTO = WorkspaceUserRequestDTO.builder()
        .id(UUID.randomUUID())
        .roleId(role.getId())
        .userId(user.getId())
        .workspaceId(workspace.getId())
        .build();
  }

  @Test
  void shouldCreateWorkspaceUser() throws Exception {
    mockMvc.perform(post("/api/v1/workspace-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(workspaceUserRequestDTO)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.code", is(201)));
  }

  @Test
  void shouldGetAllWorkspaceUsers() throws Exception {
    mockMvc.perform(get("/api/v1/workspace-users"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", is(200)));
  }

  @Test
  void shouldGetWorkspaceUserById() throws Exception {
    user2 = new User(
        UUID.randomUUID(),
        "User2",
        "Pony",
        "user2.pony@example.com",
        "password",
        "123456789",
        ZonedDateTime.now(),
        "profile.jpg");
    user2 = userRepository.save(user2);
    // Create a workspace user first
    workspaceUserRequestDTO2 = WorkspaceUserRequestDTO.builder()
        .id(UUID.randomUUID())
        .roleId(role.getId())
        .userId(user2.getId())
        .workspaceId(workspace.getId())
        .build();

    String response = performPostWorkspaceUser(workspaceUserRequestDTO2)
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString();

    UUID workspaceUserId = UUID.fromString(JsonPath.parse(response).read("$.data.id"));

    mockMvc.perform(get("/api/v1/workspace-users/{id}", workspaceUserId))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", is(200)));
  }

  @Test
  void shouldUpdateWorkspaceUser() throws Exception {
    user3 = new User(
        UUID.randomUUID(),
        "User3",
        "Pony",
        "user3.pony@example.com",
        "password",
        "123456789",
        ZonedDateTime.now(),
        "profile.jpg");
    user3 = userRepository.save(user3);
    // Create a workspace user first
    workspaceUserRequestDTO3 = WorkspaceUserRequestDTO.builder()
        .id(UUID.randomUUID())
        .roleId(role.getId())
        .userId(user3.getId())
        .workspaceId(workspace.getId())
        .build();
    String response = performPostWorkspaceUser(workspaceUserRequestDTO3)
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString();

    UUID workspaceUserId = UUID.fromString(JsonPath.parse(response).read("$.data.id"));

    WorkspaceUserRequestDTO updatedWorkspaceUserRequestDTO = WorkspaceUserRequestDTO.builder()
        .roleId(role2.getId())
        .userId(user.getId())
        .workspaceId(workspace.getId())
        .build();

    mockMvc.perform(put("/api/v1/workspace-users/{id}", workspaceUserId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedWorkspaceUserRequestDTO)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", is(200)));
  }

  @Test
  void shouldDeleteWorkspaceUser() throws Exception {

    user4 = new User(
        UUID.randomUUID(),
        "User4",
        "Tony",
        "user4.tony@example.com",
        "password",
        "123456789",
        ZonedDateTime.now(),
        "profile.jpg");
    user4 = userRepository.save(user4);
    // Create a workspace user first
    workspaceUserRequestDTO4 = WorkspaceUserRequestDTO.builder()
        .id(UUID.randomUUID())
        .roleId(role2.getId())
        .userId(user4.getId())
        .workspaceId(workspace.getId())
        .build();
    String response = performPostWorkspaceUser(workspaceUserRequestDTO4)
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString();

    UUID workspaceUserId = UUID.fromString(JsonPath.parse(response).read("$.data.id"));

    mockMvc.perform(delete("/api/v1/workspace-users/{id}", workspaceUserId))
        .andExpect(jsonPath("$.code", is(204)));
  }

  private ResultActions performPostWorkspaceUser(WorkspaceUserRequestDTO workspaceUserRequestDTO) throws Exception {
    return mockMvc.perform(post("/api/v1/workspace-users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(workspaceUserRequestDTO)));
  }
}
