package unigearrent.unigearrentjava.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import unigearrent.unigearrentjava.services.repositoryservices.CarService;
import unigearrent.unigearrentjava.services.repositoryservices.LessorDetailsService;
import unigearrent.unigearrentjava.services.repositoryservices.UserService;

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

    @Test
    public void GetReturnsNotFoundWhenTheItemDoesntExist() throws Exception {
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/Car/0"));
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
