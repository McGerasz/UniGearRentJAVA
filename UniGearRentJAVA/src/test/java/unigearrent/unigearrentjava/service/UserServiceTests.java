package unigearrent.unigearrentjava.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Null;
import org.mockito.junit.jupiter.MockitoExtension;
import unigearrent.unigearrentjava.models.User;
import unigearrent.unigearrentjava.repositories.IUserRepository;
import unigearrent.unigearrentjava.services.repositoryservices.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void UserService_GetUserByUsername_ReturnUser()
    {
        User testUser = new User();
        testUser.setUsername("test");

        when(userRepository.getUserByUsername(Mockito.any(String.class))).thenReturn(Optional.of(testUser));

        User foundUser = userService.getUserByUsername(testUser.getUsername());

        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(foundUser.getUsername(), testUser.getUsername());

    }

    @Test
    public void UserService_GetUserByUsername_ReturnNullIfNotFound()
    {
        User notFoundUser = userService.getUserByUsername("test");
        Assertions.assertNull(notFoundUser);
    }

    @Test
    public void UserService_GetUserById_ReturnUser()
    {
        User testUser = new User();
        testUser.setUserId(6);

        when(userRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(testUser));

        User foundUser  = userService.getUserById(testUser.getId());

        Assertions.assertEquals(foundUser.getUserId(), testUser.getUserId());
    }

    @Test
    public void UserService_GetAll_ReturnsEveryUser(){
        User testUser1 = new User();
        User testUser2 = new User();
        when(userRepository.findAll()).thenReturn(new ArrayList<User>(){{add(testUser1); add(testUser2);}});
        Assertions.assertEquals(2, userService.getUsers().size());
    }
}
