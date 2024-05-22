package unigearrent.unigearrentjava.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.gson.JsonParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import unigearrent.unigearrentjava.controllers.PostController;
import unigearrent.unigearrentjava.models.*;
import unigearrent.unigearrentjava.repositories.ICarRepository;
import unigearrent.unigearrentjava.services.repositoryservices.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class PostControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CarService carService;

    @Autowired
    private TrailerService trailerService;

    @Autowired
    private LessorDetailsService lessorDetailsService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @BeforeEach
    void init(){
        if(!carService.GetAll().isEmpty()) return;
        
        User user1 = new User();
        user1.setId(1);
        user1.setEmail("test@test.com");
        user1.setPhoneNumber("06201111111");
        user1.setUsername("test123");
        userService.saveUser(user1);
        
        User user2 = new User();
        user2.setId(2);
        user2.setEmail("test2@test.com");
        user2.setUsername("poster1username");
        user2.setPhoneNumber("06202222222");
        userService.saveUser(user2);
        
        User user3 = new User();
        user3.setId(3);
        user3.setEmail("test3@test.com");
        user3.setUsername("poster2username");
        user3.setPhoneNumber("06203333333");
        userService.saveUser(user3);
        
        UserDetails uDetails1 = new UserDetails();
        uDetails1.setId(1);
        uDetails1.setFirstName("Test1");
        uDetails1.setLastName("Test2");
        userDetailsService.SaveUserDetails(uDetails1);
        
        int idBase = carService.GetAll().size() + trailerService.GetAll().size();
        
        LessorDetails details1 = new LessorDetails();
        details1.setPosterId(2);
        details1.setName("Poster1");
        
        LessorDetails details2 = new LessorDetails();
        details2.setPosterId(3);
        details2.setName("Poster2");
        
        CarPost post1 = new CarPost();
        post1.setId(idBase + 1);
        post1.setLocation("Location1");
        post1.setLessorDetails(details1);
        details1.getPosts().add(post1);
        
        CarPost post2 = new CarPost();
        post2.setId(idBase + 2);
        post2.setLocation("Location1");
        post2.setLessorDetails(details1);
        details1.getPosts().add(post2);
        
        TrailerPost post3 = new TrailerPost();
        post3.setId(idBase + 3);
        post3.setLocation("Location1");
        post3.setLessorDetails(details2);
        details2.getPosts().add(post3);
        
        TrailerPost post4 = new TrailerPost();
        post4.setId(idBase + 4);
        post4.setLocation("Location2");
        post4.setLessorDetails(details2);
        details2.getPosts().add(post4);
        
        lessorDetailsService.SaveLessorDetails(details1);
        lessorDetailsService.SaveLessorDetails(details2);
        
        carService.SaveCar(post1);
        carService.SaveCar(post2);
        
        trailerService.SaveTrailer(post3);
        trailerService.SaveTrailer(post4);

        User user4 = new User();
        user4.setId(4);
        user4.setEmail("test4@test.com");
        user4.setPhoneNumber("06204444444");
        user4.setUsername("UserToBeUpdated");
        userService.saveUser(user4);

        UserDetails uDetails2 = new UserDetails();
        uDetails2.setId(4);
        uDetails2.setFirstName("ToBe");
        uDetails2.setLastName("Updated");
        userDetailsService.SaveUserDetails(uDetails2);

        User user5 = new User();
        user5.setId(5);
        user5.setEmail("test5@test.com");
        user5.setPhoneNumber("06205555555");
        user5.setUsername("LessorToBeUpdated");
        userService.saveUser(user5);

        LessorDetails details3 = new LessorDetails();
        details3.setPosterId(5);
        details3.setName("Poster3ToBeUpdated");
        lessorDetailsService.SaveLessorDetails(details3);
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
    @Test
    public void PostFavouriteSavesFavourite() throws Exception {
        ResultActions response1 = mockMvc.perform(MockMvcRequestBuilders.post("/api/Post/favourite?userName=test123&postId=2"));
        ResultActions response2 = mockMvc.perform(MockMvcRequestBuilders.get("/api/Post/isFavourite?userName=test123&Id=2"));

        Assertions.assertEquals("true", response2.andReturn().getResponse().getContentAsString());
    }
    @Test
    public void ProfileDetailsReturnsUserDetails() throws Exception {
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/Post/profileDetails/1"));
        Assertions.assertEquals('"' + "Test2" + '"',
                new JsonParser().parse(response.andReturn().getResponse().getContentAsString()).getAsJsonObject().get("lastName").toString());
    }
    @Test
    public void ProfileDetailsReturnsLessorDetails() throws Exception {
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/Post/profileDetails/3"));
        Assertions.assertEquals('"' + "Poster2" + '"',
                new JsonParser().parse(response.andReturn().getResponse().getContentAsString()).getAsJsonObject().get("name").toString());
    }
    @Test
    public void LessorPageDataReturnsCorrectData() throws Exception {
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/Post/lessorPageData/2"));
        Assertions.assertEquals('"' +"Poster1"+ '"',
                new JsonParser().parse(response.andReturn().getResponse().getContentAsString()).getAsJsonObject().get("name").toString());
    }
    @Test
    public void LessorPageDataReturnsAllContentWithUsername() throws Exception{
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/Post/lessorPageData/3?userName=test123"));
        Assertions.assertEquals('"' + "test3@test.com" + '"',
                new JsonParser().parse(response.andReturn().getResponse().getContentAsString()).getAsJsonObject().get("email").toString());
    }
    @Test
    public void LessorPutEndpointTest() throws Exception{
        String updateValue = "LessorHasUpdated";
        ResultActions response= mockMvc.perform(MockMvcRequestBuilders.put("/api/Post/lessor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new LessorPutRequestDTO(5, "LessorToBeUpdated", "test5@test.com",
                                "06205555555", updateValue))));
        Assertions.assertEquals(updateValue, lessorDetailsService.GetById(5).getName());
    }
}
