package fs19.java.backend;

import fs19.java.backend.application.dto.CompanyDTO;
import fs19.java.backend.domain.entity.Company;
import fs19.java.backend.domain.abstraction.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc

public class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyRepository companyRepository;

    private Company existingCompany;

    @BeforeEach
    public void setUp() {
        existingCompany = new Company(UUID.randomUUID(), "Test Company", null, UUID.randomUUID());
        companyRepository.save(existingCompany);
    }

    @Test
    public void testCreateCompany() throws Exception {
        CompanyDTO companyDTO = new CompanyDTO(null, "New Company", null, UUID.randomUUID());

        mockMvc.perform(post("/v1/api/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Company\",\"createdBy\":\"123e4567-e89b-12d3-a456-426614174000\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", is("SUCCESS")))
                .andExpect(jsonPath("$.data.name", is("New Company")));
    }

    @Test
    public void testUpdateCompany() throws Exception {
        CompanyDTO companyDTO = new CompanyDTO(null, "Updated Company", null, existingCompany.getCreatedBy());

        mockMvc.perform(put("/v1/api/companies/" + existingCompany.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Company\",\"createdBy\":\"" + existingCompany.getCreatedBy() + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("SUCCESS")))
                .andExpect(jsonPath("$.data.name", is("Updated Company")));
    }

    @Test
    public void testGetCompanyById() throws Exception {
        mockMvc.perform(get("/v1/api/companies/" + existingCompany.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("SUCCESS")))
                .andExpect(jsonPath("$.data.name", is(existingCompany.getName())));
    }

    @Test
    public void testGetAllCompanies() throws Exception {
        mockMvc.perform(get("/v1/api/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("SUCCESS")));
    }

    @Test
    public void testDeleteCompany() throws Exception {
        mockMvc.perform(delete("/v1/api/companies/" + existingCompany.getId()))
                .andExpect(status().isNoContent());
    }
}
