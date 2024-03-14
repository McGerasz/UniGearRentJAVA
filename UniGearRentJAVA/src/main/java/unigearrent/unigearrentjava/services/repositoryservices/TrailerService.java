package unigearrent.unigearrentjava.services.repositoryservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unigearrent.unigearrentjava.models.TrailerPost;
import unigearrent.unigearrentjava.repositories.ICarRepository;
import unigearrent.unigearrentjava.repositories.ITrailerRepository;

import java.util.List;
@Service
public class TrailerService {
    @Autowired
    private ITrailerRepository _repository;

    @Autowired
    public TrailerService(ITrailerRepository repository){
        _repository = repository;
    }
    public TrailerPost SaveCar(TrailerPost post){
        return _repository.save(post);
    }
    public List<TrailerPost> GetAll(){
        return _repository.findAll();
    }
    public TrailerPost GetById(Integer id){
        try{
            return _repository.getById(id);
        }
        catch (Exception e){
            throw new RuntimeException("Error getting car with id " + id);
        }
    }
    public void Delete(TrailerPost post){
        _repository.delete(post);
    }
    public void Update(TrailerPost post){
        TrailerPost existingPost = GetById(post.getId());
        existingPost.setName(post.getName());
        existingPost.setLocation(post.getLocation());
        existingPost.setPosterId(post.getPosterId());
        existingPost.setDescription(post.getDescription());
        existingPost.setHourlyPrice(post.getHourlyPrice());
        existingPost.setDailyPrice(post.getDailyPrice());
        existingPost.setWeeklyPrice(post.getWeeklyPrice());
        existingPost.setSecurityDeposit(post.getSecurityDeposit());
        existingPost.setWidth(post.getWidth());
        existingPost.setLength(post.getLength());
        _repository.save(existingPost);
    }
}
