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
import unigearrent.unigearrentjava.controllers.CarController;
import unigearrent.unigearrentjava.models.CarPost;
import unigearrent.unigearrentjava.services.repositoryservices.CarService;
import unigearrent.unigearrentjava.services.repositoryservices.LessorDetailsService;
import unigearrent.unigearrentjava.services.repositoryservices.UserService;

import java.util.Optional;

@WebMvcTest(controllers = CarController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CarControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @MockBean
    private LessorDetailsService lessorService;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void GetReturnsNotFoundWhenTheItemDoesntExist() throws Exception {
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/Car/0"));
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    public void GetReturnsTheRightItem() throws Exception {
        CarPost post = new CarPost();
        post.setId(1);
        BDDMockito.given(carService.GetById(1)).willReturn(Optional.of(post));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/Car/1"));
        Assertions.
                assertEquals(objectMapper.readValue(response.andReturn().getResponse().getContentAsString(),
                        CarPost.class).getId(), post.getId());
    }
}
