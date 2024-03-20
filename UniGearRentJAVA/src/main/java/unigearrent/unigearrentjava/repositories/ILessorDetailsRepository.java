package unigearrent.unigearrentjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unigearrent.unigearrentjava.models.LessorDetails;

@Repository
public interface ILessorDetailsRepository extends JpaRepository<LessorDetails, Integer> {
}
