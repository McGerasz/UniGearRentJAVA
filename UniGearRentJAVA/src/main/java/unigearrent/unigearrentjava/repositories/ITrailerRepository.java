package unigearrent.unigearrentjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unigearrent.unigearrentjava.models.TrailerPost;
@Repository
public interface ITrailerRepository extends JpaRepository<TrailerPost, Integer> {
}
