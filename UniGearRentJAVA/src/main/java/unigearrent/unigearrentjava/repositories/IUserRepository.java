package unigearrent.unigearrentjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import unigearrent.unigearrentjava.models.User;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserByUsername(String username);
}
