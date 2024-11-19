package fs19.java.backend.workspace;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import fs19.java.backend.domain.abstraction.WorkspaceRepository;
import fs19.java.backend.domain.entity.Workspace;
import fs19.java.backend.domain.entity.enums.WorkspaceType;
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
public class WorkspaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    private Workspace existingWorkspace;

    @BeforeEach
    public void setUp() {
        existingWorkspace = new Workspace(UUID.randomUUID(), "Test Workspace", "Test Description", WorkspaceType.PUBLIC, null, UUID.randomUUID(), UUID.randomUUID());
        workspaceRepository.save(existingWorkspace);
    }

    @Test
    public void testCreateWorkspace() throws Exception {
        UUID companyId = UUID.randomUUID();
        UUID createdBy = UUID.randomUUID();

        mockMvc.perform(post("/api/v1/workspaces")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Workspace\",\"description\":\"New Description\",\"type\":\"PUBLIC\",\"createdBy\":\"" + createdBy + "\",\"companyId\":\"" + companyId + "\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code", is(201)))
                .andExpect(jsonPath("$.data.name", is("New Workspace")));
    }

    @Test
    public void testUpdateWorkspace() throws Exception {
        mockMvc.perform(put("/api/v1/workspaces/" + existingWorkspace.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Workspace\",\"description\":\"Updated Description\",\"type\":\"SHARED\",\"companyId\":\"" + existingWorkspace.getCompanyId() + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data.name", is("Updated Workspace")));
    }

    @Test
    public void testGetWorkspaceById() throws Exception {
        mockMvc.perform(get("/api/v1/workspaces/" + existingWorkspace.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data.name", is("Test Workspace")));
    }

    @Test
    public void testGetAllWorkspaces() throws Exception {
        mockMvc.perform(get("/api/v1/workspaces"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data[0].name", is("Test Workspace")));
    }

    @Test
    public void testDeleteWorkspace() throws Exception {
        mockMvc.perform(delete("/api/v1/workspaces/" + existingWorkspace.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetNonExistentWorkspace() throws Exception {
        UUID nonExistentId = UUID.randomUUID();
        mockMvc.perform(get("/api/v1/workspaces/" + nonExistentId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(404)))
                .andExpect(jsonPath("$.errors[0].message", containsString("Workspace with ID " + nonExistentId + " not found")));
    }

    private ResultActions performPostWorkspace(Workspace workspace) throws Exception {
        return mockMvc.perform(post("/api/v1/workspaces")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(workspace)));
    }
}
