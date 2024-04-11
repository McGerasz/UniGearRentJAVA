package unigearrent.unigearrentjava.services.repositoryservices;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import unigearrent.unigearrentjava.models.UserDetails;
import unigearrent.unigearrentjava.repositories.IUserDetailsRepository;

import java.util.List;

@Service
public class UserDetailsService {
    @Autowired
    private IUserDetailsRepository _repository;

    @Autowired
    public UserDetailsService(IUserDetailsRepository repository) {
        _repository = repository;
    }

    public UserDetails SaveUserDetails(UserDetails details) {
        return _repository.save(details);
    }

    public List<UserDetails> GetAll() {
        return _repository.findAll();
    }

    public UserDetails GetById(Integer id) {
        try {
            return _repository.getById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error getting car with id " + id);
        }
    }

    public void Delete(UserDetails post) {
        _repository.delete(post);
    }

    public void Update(UserDetails details) {
        UserDetails existingDetails = GetById(details.getId());
        existingDetails.setFirstName(details.getFirstName());
        existingDetails.setLastName(details.getLastName());
        existingDetails.setFavouriteIDs(details.getFavouriteIDs());
        _repository.save(existingDetails);
    }
}

