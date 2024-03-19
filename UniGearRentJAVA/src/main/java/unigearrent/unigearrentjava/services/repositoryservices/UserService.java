package unigearrent.unigearrentjava.services.repositoryservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import unigearrent.unigearrentjava.models.User;
import unigearrent.unigearrentjava.repositories.IUserRepository;

import java.nio.file.*;
import java.util.UUID;

import java.util.List;
import java.util.Optional;



@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;


    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error saving user", e);
        }
    }

    public List<User> getUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving users", e);
        }
    }

    public User getUserById(int id) {
        try {
            Optional<User> optionalUser = userRepository.findById(id);
            return optionalUser.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving user by ID", e);
        }
    }

    public User getUserByUsername(String username)
    {
        try {
            Optional<User> optionalUser = userRepository.getUserByUsername(username);
            return optionalUser.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving user by username");
        }
    }

    public String deleteUser(int id) {
        try {
            userRepository.deleteById(id);
            return "User removed!" + id;
        } catch (Exception e) {
            throw new RuntimeException("Error deleting user", e);
        }
    }

    public User updateUser(User user) {
        try {
            User existingUser = userRepository.findById(user.getId()).orElse(null);
            if (existingUser == null) {
                throw new IllegalArgumentException("User with ID " + user.getId() + " not found");
            }
            existingUser.setName(user.getName());
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            return userRepository.save(existingUser);
        } catch (Exception e) {
            throw new RuntimeException("Error updating user", e);
        }
    }

    public void UpdateEmail(String email, Integer id)
    {
        try {
            User existingUser = userRepository.findById(id).orElse(null);
            if (existingUser == null) {
                throw new IllegalArgumentException("User with ID " + id + " not found");
            }

            existingUser.setEmail(email);
            userRepository.save(existingUser);

        }catch (Exception e)
        {
            throw new RuntimeException("Error updating email", e);
        }
    }

    public void UpdatePassword(String password, Integer id)
    {
        try {
            User existingUser = userRepository.findById(id).orElse(null);
            if (existingUser == null) {
                throw new IllegalArgumentException("User with ID " + id + " not found");
            }

            existingUser.setPassword(passwordEncoder.encode(password));
            userRepository.save(existingUser);


        }catch (Exception e)
        {
            throw new RuntimeException("Error updating password", e);
        }
    }

}