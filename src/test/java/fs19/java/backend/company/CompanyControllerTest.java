package fs19.java.backend.company;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import fs19.java.backend.application.dto.company.CompanyRequestDTO;
import fs19.java.backend.application.dto.company.CompanyUpdateDTO;
import fs19.java.backend.domain.entity.Company;
import fs19.java.backend.domain.entity.User;
import fs19.java.backend.infrastructure.JpaRepositories.CompanyJpaRepo;
import fs19.java.backend.infrastructure.JpaRepositories.UserJpaRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CompanyJpaRepo companyRepository;

    @Autowired
    private UserJpaRepo userRepository;

    private Company existingCompany;
    private User existingUser;

    @BeforeEach
    public void setUp() {
        existingUser = new User();
        existingUser.setId(UUID.randomUUID());
        existingUser.setFirstName("John");
        existingUser.setLastName("Doe");
        existingUser.setEmail("john.doe@example.com");
        existingUser.setPassword("password123");
        existingUser.setPhone("1234566574");
        userRepository.save(existingUser);

        existingCompany = new Company(UUID.randomUUID(), "Test Company", null, existingUser);
        companyRepository.save(existingCompany);
    }

    @Test
    public void testCreateCompany() throws Exception {
        UUID createdBy = existingUser.getId(); // Use existing user ID
        CompanyRequestDTO request = new CompanyRequestDTO("New Company", createdBy);

        mockMvc.perform(post("/api/v1/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code", is(201)))
                .andExpect(jsonPath("$.data.name", is("New Company")))
                .andExpect(jsonPath("$.data.createdDate").isNotEmpty());
    }

    @Test
    public void testUpdateCompany() throws Exception {
        CompanyUpdateDTO request = new CompanyUpdateDTO("Updated Company", existingUser.getId());

        mockMvc.perform(put("/api/v1/companies/" + existingCompany.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data.name", is("Updated Company")));
    }

    @Test
    public void testGetCompanyById() throws Exception {
        mockMvc.perform(get("/api/v1/companies/" + existingCompany.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data.name", is(existingCompany.getName())));
    }

    @Test
    public void testGetAllCompanies() throws Exception {
        mockMvc.perform(get("/api/v1/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    public void testDeleteCompany() throws Exception {
        mockMvc.perform(delete("/api/v1/companies/" + existingCompany.getId()))
                .andExpect(status().isNoContent());
    }

    private ResultActions performPostCompany(CompanyRequestDTO request) throws Exception {
        return mockMvc.perform(post("/api/v1/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }
}