package unigearrent.unigearrentjava.services.repositoryservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unigearrent.unigearrentjava.models.CarPost;
import unigearrent.unigearrentjava.repositories.ICarRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    @Autowired
    private ICarRepository _repository;

    @Autowired
    public CarService(ICarRepository repository){
        _repository = repository;
    }
    public CarPost SaveCar(CarPost post){
        return _repository.save(post);
    }
    public List<CarPost> GetAll(){
        return _repository.findAll();
    }
    public Optional<CarPost> GetById(Integer id){
        try{
            return _repository.findById(id);
        }
        catch (Exception e){
            throw new RuntimeException("Error getting car with id " + id);
        }
    }
    public void Delete(CarPost post){
        _repository.delete(post);
    }
    public void Update(CarPost post){
        CarPost existingPost = GetById(post.getId()).get();
        existingPost.setName(post.getName());
        existingPost.setLocation(post.getLocation());
        existingPost.setPosterId(post.getPosterId());
        existingPost.setDescription(post.getDescription());
        existingPost.setHourlyPrice(post.getHourlyPrice());
        existingPost.setDailyPrice(post.getDailyPrice());
        existingPost.setWeeklyPrice(post.getWeeklyPrice());
        existingPost.setSecurityDeposit(post.getSecurityDeposit());
        existingPost.setCanItBeDelivered(post.getCanItBeDelivered());
        existingPost.setNumberOfSeats(post.getNumberOfSeats());
        if((long) post.getUsers().size() > 0) existingPost.setUsers(post.getUsers());
        _repository.save(existingPost);
    }
}
