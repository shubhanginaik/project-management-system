package fs19.java.backend.role;

import com.fasterxml.jackson.databind.ObjectMapper;
import fs19.java.backend.application.dto.role.RoleRequestDTO;
import fs19.java.backend.application.RoleServiceImpl;
import fs19.java.backend.domain.abstraction.CompanyRepository;
import fs19.java.backend.domain.entity.Company;
import fs19.java.backend.domain.entity.Role;
import fs19.java.backend.infrastructure.CompanyRepoImpl;
import fs19.java.backend.infrastructure.RoleRepoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class RoleControllerTest {
    RoleRequestDTO roleRequest;
    private Company existingCompany;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private RoleRepoImpl roleRepository;


    @Autowired
    private CompanyRepoImpl companyRepo;

    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    public void setUp() {

        existingCompany = new Company(UUID.randomUUID(), "Test Company", null, UUID.randomUUID());
        companyRepository.save(existingCompany);

        roleRequest = new RoleRequestDTO(UUID.randomUUID(), "Admin", existingCompany.getId());
        Role role = roleRepository.createRole(roleRequest, existingCompany);
        roleRequest.setId(role.getId());


    }

    @Test
    void testCreateRole_Success() throws Exception {
        roleRequest = new RoleRequestDTO(UUID.randomUUID(), "System-User", existingCompany.getId());
        mockMvc.perform(post("/app/v1/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roleRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(roleRequest.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.createdDate").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.status").doesNotExist());
    }

    @Test
    void testCreateRole_ValidationError() throws Exception {
        RoleRequestDTO roleRequest = new RoleRequestDTO(UUID.randomUUID(), "", existingCompany.getId()); // Invalid name
        mockMvc.perform(post("/app/v1/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roleRequest)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").isNotEmpty());
    }

    @Test
    void testUpdateRole_Success() throws Exception {
        RoleRequestDTO roleUpdateRequest = new RoleRequestDTO(UUID.randomUUID(), "UpdatedAdmin", existingCompany.getId());
        mockMvc.perform(put("/app/v1/roles/" + roleRequest.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roleUpdateRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value("UpdatedAdmin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.createdDate").isNotEmpty());
    }

    @Test
    void testDeleteRole_Success() throws Exception {
        mockMvc.perform(delete("/app/v1/roles/" + roleRequest.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value("Admin"));
    }

    @Test
    void testGetRoles_Success() throws Exception {
        mockMvc.perform(get("/app/v1/roles"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].name").value("DEV"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].createdDate").isNotEmpty())
                .andExpect(jsonPath("$.code", is(200)));
    }

    @Test
    void testGetRoleById_Success() throws Exception {
        mockMvc.perform(get("/app/v1/roles/" + roleRequest.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value("Admin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.createdDate").isNotEmpty());
    }

    @Test
    void testGetRoleByName_Success() throws Exception {
        mockMvc.perform(get("/app/v1/roles/search/" + roleRequest.getName()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value("Admin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.createdDate").isNotEmpty());
    }
}