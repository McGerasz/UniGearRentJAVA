package unigearrent.unigearrentjava.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import unigearrent.unigearrentjava.controllers.PostController;
import unigearrent.unigearrentjava.models.CarPost;
import unigearrent.unigearrentjava.models.LessorDetails;
import unigearrent.unigearrentjava.models.Post;
import unigearrent.unigearrentjava.models.TrailerPost;
import unigearrent.unigearrentjava.services.repositoryservices.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@WebMvcTest(controllers = PostController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PostControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CarService carService;
    @MockBean
    private TrailerService trailerService;
    @MockBean
    private LessorDetailsService lessorDetailsService;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private UserService userService;

    @BeforeEach
    void init(){
        LessorDetails details1 = new LessorDetails();
        details1.setName("Poster1");
        LessorDetails details2 = new LessorDetails();
        details2.setName("Poster2");

        BDDMockito.given(lessorDetailsService.GetAll())
                .willReturn(new ArrayList<LessorDetails>(){{add(details1); add(details2);}});
        BDDMockito.given(lessorDetailsService
                .GetContainingName(ArgumentMatchers.anyString()))
                .willAnswer(i -> lessorDetailsService.GetAll().stream().filter(x -> x.getName().toLowerCase().contains(i.getArgument(0).toString().toLowerCase())).toList());

        CarPost post1 = new CarPost();
        post1.setId(1);
        post1.setLocation("Location1");
        post1.setLessorDetails(details1);
        details1.getPosts().add(post1);
        CarPost post2 = new CarPost();
        post2.setId(2);
        post2.setLocation("Location1");
        post2.setLessorDetails(details1);
        details1.getPosts().add(post2);
        TrailerPost post3 = new TrailerPost();
        post3.setId(3);
        post3.setLocation("Location1");
        post3.setLessorDetails(details2);
        details2.getPosts().add(post3);
        TrailerPost post4 = new TrailerPost();
        post4.setId(4);
        post4.setLocation("Location2");
        post4.setLessorDetails(details2);
        details2.getPosts().add(post4);
        BDDMockito.given(carService.GetAll()).willReturn(new ArrayList<>(){{add(post1); add(post2);}});
        BDDMockito.given(trailerService.GetAll()).willReturn(new ArrayList<>(){{add(post4); add(post3);}});
    }
    @Test
    public void ByNameReturnsTheRightAmountOfEntries() throws Exception{
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/Post/byName/poster1"));
        Assertions.assertEquals(2, objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), Set.class).size());
    }
    @Test
    public void ByLocationReturnsTheRightAmountOfEntries() throws Exception{
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/Post/byLocation/location2"));
        Assertions.assertEquals(1, objectMapper.readValue(response.andReturn().getResponse().getContentAsString(), Set.class).size());
    }
}
