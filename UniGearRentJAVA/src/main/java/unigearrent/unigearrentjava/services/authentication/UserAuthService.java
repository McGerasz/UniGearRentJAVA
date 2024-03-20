package unigearrent.unigearrentjava.services.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import unigearrent.unigearrentjava.repositories.IUserRepository;

@Service
public class UserAuthService implements UserDetailsService {
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private IUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.getUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("user is not valid"));
    }
}
