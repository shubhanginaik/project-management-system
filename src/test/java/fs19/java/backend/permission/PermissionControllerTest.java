package fs19.java.backend.permission;

import com.fasterxml.jackson.databind.ObjectMapper;
import fs19.java.backend.application.PermissionServiceImpl;
import fs19.java.backend.application.dto.permission.PermissionRequestDTO;
import fs19.java.backend.application.dto.permission.PermissionResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class PermissionControllerTest {

    PermissionRequestDTO request;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PermissionServiceImpl permissionService;

    @BeforeEach
    public void setUp() {
        request = new PermissionRequestDTO(UUID.randomUUID(), "READ_PRIVILEGES");
        PermissionResponseDTO permission = permissionService.createPermission(request);
        request.setId(permission.getId());
    }

    @Test
    void testCreatePermission_Success() throws Exception {
        PermissionRequestDTO request2 = new PermissionRequestDTO(UUID.randomUUID(), "READ_PRIVILEGES_2");
        mockMvc.perform(post("/app/v1/permissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request2)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value("READ_PRIVILEGES_2"));
    }

    @Test
    void testCreatePermission_ValidationError() throws Exception {
        PermissionRequestDTO myNewRequest = new PermissionRequestDTO(UUID.randomUUID(), ""); // Invalid name
        mockMvc.perform(post("/app/v1/permissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(myNewRequest)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").isNotEmpty());
    }

    @Test
    void testUpdatePermission_Success() throws Exception {

        if(request.getId() == null){
            request = new PermissionRequestDTO(UUID.randomUUID(), "READ_PRIVILEGES_4");
            PermissionResponseDTO permission = permissionService.createPermission(request);
            request.setId(permission.getId());
        }

        PermissionRequestDTO newRequest = new PermissionRequestDTO(UUID.randomUUID(), "WRITE_PRIVILEGES_2");
        mockMvc.perform(put("/app/v1/permissions/" + request.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value("WRITE_PRIVILEGES_2"));
    }

    @Test
    void testDeletePermission_Success() throws Exception {
        if(request.getId() == null){
             request = new PermissionRequestDTO(UUID.randomUUID(), "READ_PRIVILEGES_3");
            PermissionResponseDTO permission = permissionService.createPermission(request);
            request.setId(permission.getId());
        }
        mockMvc.perform(delete("/app/v1/permissions/" + request.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value("READ_PRIVILEGES_3"));
    }

    @Test
    void testGetPermissions_Success() throws Exception {
        mockMvc.perform(get("/app/v1/permissions"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].name").value("READ_ACCESS"));
    }

    @Test
    void testGetPermissionById_Success() throws Exception {
        mockMvc.perform(get("/app/v1/permissions/" + request.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value("READ_PRIVILEGES"));
    }

    @Test
    void testGetPermissionByName_Success() throws Exception {
        mockMvc.perform(get("/app/v1/permissions/search/" + request.getName()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value("READ_PRIVILEGES"));
    }
}