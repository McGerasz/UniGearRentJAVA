package unigearrent.unigearrentjava.services.repositoryservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unigearrent.unigearrentjava.models.TrailerPost;
import unigearrent.unigearrentjava.repositories.ICarRepository;
import unigearrent.unigearrentjava.repositories.ITrailerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TrailerService {
    @Autowired
    private ITrailerRepository _repository;

    @Autowired
    public TrailerService(ITrailerRepository repository){
        _repository = repository;
    }
    public TrailerPost SaveTrailer(TrailerPost post){
        return _repository.save(post);
    }
    public List<TrailerPost> GetAll(){
        return _repository.findAll();
    }
    public Optional<TrailerPost> GetById(Integer id){
        try{
            return _repository.findById(id);
        }
        catch (Exception e){
            throw new RuntimeException("Error getting car with id " + id);
        }
    }
    public void Delete(TrailerPost post){
        _repository.delete(post);
    }
    public void Update(TrailerPost post){
        TrailerPost existingPost = GetById(post.getId()).get();
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
        if((long) post.getUsers().size() > 0) existingPost.setUsers(post.getUsers());
        _repository.save(existingPost);
    }
}
