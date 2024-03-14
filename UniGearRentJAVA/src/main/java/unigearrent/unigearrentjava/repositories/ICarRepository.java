package unigearrent.unigearrentjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unigearrent.unigearrentjava.models.CarPost;
@Repository
public interface ICarRepository extends JpaRepository<CarPost, Integer> {
}
