package unigearrent.unigearrentjava.services.authentication;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import unigearrent.unigearrentjava.models.LoginResponseDTO;
import unigearrent.unigearrentjava.models.RegistrationResponseDTO;
import unigearrent.unigearrentjava.models.Role;
import unigearrent.unigearrentjava.models.User;
import unigearrent.unigearrentjava.repositories.IRoleRepository;
import unigearrent.unigearrentjava.repositories.IUserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Qualifier("userAuthManager")
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;


    public RegistrationResponseDTO registerUser(String username, String email, String password, String phoneNumber)
    {
        try {
            String encodedPassword = passwordEncoder.encode(password);
            if(roleRepository.findByAuthority("USER").isEmpty()){
                roleRepository.save(new Role("USER"));
            }
            Role userRole = roleRepository.findByAuthority("USER").get();

            Set<Role> authorities = new HashSet<>();
            authorities.add(userRole);

            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setPassword(encodedPassword);
            newUser.setAuthorities(authorities);
            newUser.setPhoneNumber(phoneNumber);

            userRepository.save(newUser);

            return new RegistrationResponseDTO(email, username, phoneNumber);

        }catch (Exception e)
        {
            System.out.println(e);
            return new RegistrationResponseDTO("","","");
        }
    }
    public RegistrationResponseDTO registerLessor(String username, String email, String password, String phoneNumber)
    {
        try {
            String encodedPassword = passwordEncoder.encode(password);
            if(roleRepository.findByAuthority("LESSOR").isEmpty()){
                roleRepository.save(new Role("LESSOR"));
            }
            Role userRole = roleRepository.findByAuthority("LESSOR").get();

            Set<Role> authorities = new HashSet<>();
            authorities.add(userRole);

            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setPassword(encodedPassword);
            newUser.setAuthorities(authorities);
            newUser.setPhoneNumber(phoneNumber);

            userRepository.save(newUser);

            return new RegistrationResponseDTO(username, email, phoneNumber);

        }catch (Exception e)
        {
            return new RegistrationResponseDTO("","","");
        }
    }

    public LoginResponseDTO loginUser(String email, String password)
    {
        try{

            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            String token = tokenService.generateJwt(auth, userRepository.getUserByEmail(email).get());

            User user = userRepository.getUserByEmail(email).get();
            return new LoginResponseDTO(user.getUsername(), user.getPhoneNumber(), token, user.getId(), user.getEmail());

        }catch (AuthenticationException e) {
            return new LoginResponseDTO("", "", "", 0, "");
        }
    }
}
