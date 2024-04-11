package unigearrent.unigearrentjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unigearrent.unigearrentjava.models.UserDetails;

@Repository
public interface IUserDetailsRepository extends JpaRepository<UserDetails, Integer> {
}

