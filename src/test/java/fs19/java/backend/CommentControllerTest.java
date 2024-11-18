package fs19.java.backend;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import fs19.java.backend.application.dto.comment.CommentRequestDTO;
import fs19.java.backend.application.dto.comment.CommentUpdateDTO;
import fs19.java.backend.domain.abstraction.CommentRepository;
import fs19.java.backend.domain.entity.Comment;
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
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CommentRepository commentRepository;

    private Comment existingComment;
    private UUID taskId;
    private UUID userId;

    @BeforeEach
    public void setUp() {
        taskId = UUID.randomUUID();
        userId = UUID.randomUUID();
        existingComment = new Comment(UUID.randomUUID(), taskId, "This is a test comment", null, userId);
        commentRepository.save(existingComment);
    }

    @Test
    public void testCreateComment() throws Exception {
        UUID newUserId = UUID.randomUUID();
        CommentRequestDTO request = new CommentRequestDTO(taskId, "This is a new comment", newUserId);

        mockMvc.perform(post("/v1/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code", is(201)))
                .andExpect(jsonPath("$.data.content", is("This is a new comment")));
    }

    @Test
    public void testUpdateComment() throws Exception {
        CommentUpdateDTO request = new CommentUpdateDTO();
        request.setContent("Updated comment content");

        mockMvc.perform(put("/v1/api/comments/" + existingComment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data.content", is("Updated comment content")));
    }

    @Test
    public void testGetCommentById() throws Exception {
        mockMvc.perform(get("/v1/api/comments/" + existingComment.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data.content", is(existingComment.getContent())));
    }

    @Test
    public void testGetAllComments() throws Exception {
        mockMvc.perform(get("/v1/api/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    public void testDeleteComment() throws Exception {
        mockMvc.perform(delete("/v1/api/comments/" + existingComment.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetNonExistentComment() throws Exception {
        UUID nonExistentId = UUID.randomUUID();
        mockMvc.perform(get("/v1/api/comments/" + nonExistentId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(404)))
                .andExpect(jsonPath("$.errors[0].message", is("Comment with ID " + nonExistentId + " not found")));
    }

    private ResultActions performPostComment(CommentRequestDTO request) throws Exception {
        return mockMvc.perform(post("/v1/api/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }
}