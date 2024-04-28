package unigearrent.unigearrentjava.services.repositoryservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unigearrent.unigearrentjava.models.LessorDetails;
import unigearrent.unigearrentjava.models.LessorDetails;
import unigearrent.unigearrentjava.repositories.ICarRepository;
import unigearrent.unigearrentjava.repositories.ILessorDetailsRepository;

import java.util.List;
@Service
public class LessorDetailsService {
    @Autowired
    private ILessorDetailsRepository _repository;

    @Autowired
    public LessorDetailsService(ILessorDetailsRepository repository){
        _repository = repository;
    }
    public LessorDetails SaveLessorDetails(LessorDetails details){
        return _repository.save(details);
    }
    public List<LessorDetails> GetAll(){
        return _repository.findAll();
    }
    public LessorDetails GetById(Integer id){
        try{
            return _repository.findById(id).get();
        }
        catch (Exception e){
            throw new RuntimeException("Error getting car with id " + id);
        }
    }
    public void Delete(LessorDetails post){
        _repository.delete(post);
    }
    public void Update(LessorDetails details){
        LessorDetails existingDetails = GetById(details.getPosterId());
        existingDetails.setName(details.getName());
        existingDetails.setPosts(details.getPosts());
        _repository.save(existingDetails);
    }
    public List<LessorDetails> GetContainingName(String name){
        return _repository.findAll().stream().filter(details -> details.getName().toLowerCase().contains(name.toLowerCase())).toList();
    }
}
