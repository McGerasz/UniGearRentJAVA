package unigearrent.unigearrentjava.controllers;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import unigearrent.unigearrentjava.models.*;
import unigearrent.unigearrentjava.repositories.ILessorDetailsRepository;
import unigearrent.unigearrentjava.repositories.IUserRepository;
import unigearrent.unigearrentjava.services.authentication.AuthenticationService;
import unigearrent.unigearrentjava.services.repositoryservices.LessorDetailsService;
import unigearrent.unigearrentjava.services.repositoryservices.UserService;

import java.util.Objects;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/Auth")
public class AuthController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private ILessorDetailsRepository lessorDetailsService;
    @Autowired
    private UserService userService;
    @PostMapping("/RegisterUser")
    public ResponseEntity<?> RegisterUser(@Validated @RequestBody UserRegistrationRequestDTO body, @NotNull BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid register request");
        }
        try {
            authenticationService.registerUser(body.getUsername(), body.getEmail(), body.getPassword(), body.getPhoneNumber());
            return ResponseEntity.status(HttpStatus.OK).body(new RegistrationResponseDTO(body.getUsername(), body.getEmail(), body.getPhoneNumber()));

        }catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during registering user");
        }

    }
    @PostMapping("/RegisterLessor")
    public ResponseEntity<?> RegisterLessor(@Validated @RequestBody LessorRegistrationRequestDTO body, @NotNull BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid register request");
        }
        try {
            authenticationService.registerLessor(body.getUsername(), body.getEmail(), body.getPassword(), body.getPhoneNumber());
            Integer id = userService.getUserByUsername(body.getUsername()).getId();
            LessorDetails details = new LessorDetails();
            details.setName(body.getName());
            details.setPosterId(id);
            lessorDetailsService.save(details);
            return ResponseEntity.status(HttpStatus.OK).body(new RegistrationResponseDTO(body.getUsername(), body.getEmail(), body.getPhoneNumber()));

        }catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during registering user");
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> LoginUser(@Validated @RequestBody LoginRequestDTO body, @NotNull BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid login request");
        }

        try {

            LoginResponseDTO loginResponseDTO =  authenticationService.loginUser(body.getEmail(), body.getPassword());


            if(loginResponseDTO.getUserName() == null || Objects.equals(loginResponseDTO.getToken(), ""))
            {
                return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bad Credentials");
            }
            else {
                return ResponseEntity.status(HttpStatus.OK).body(loginResponseDTO);
            }

        } catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during login user");
        }

    }
}
