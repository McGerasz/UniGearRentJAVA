package unigearrent.unigearrentjava.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import unigearrent.unigearrentjava.controllers.TrailerController;
import unigearrent.unigearrentjava.models.TrailerPost;
import unigearrent.unigearrentjava.services.repositoryservices.TrailerService;
import unigearrent.unigearrentjava.services.repositoryservices.LessorDetailsService;
import unigearrent.unigearrentjava.services.repositoryservices.UserService;

import java.util.Optional;

@WebMvcTest(controllers = TrailerController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class TrailerControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrailerService trailerService;

    @MockBean
    private LessorDetailsService lessorService;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void GetReturnsNotFoundWhenTheItemDoesntExist() throws Exception {
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/Trailer/0"));
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    public void GetReturnsTheRightItem() throws Exception {
        TrailerPost post = new TrailerPost();
        post.setId(2);
        BDDMockito.given(trailerService.GetById(2)).willReturn(Optional.of(post));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/Trailer/2"));
        Assertions.
                assertEquals(objectMapper.readValue(response.andReturn().getResponse().getContentAsString(),
                        TrailerPost.class).getId(), post.getId());
    }
}