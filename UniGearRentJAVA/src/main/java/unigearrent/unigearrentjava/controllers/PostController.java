package unigearrent.unigearrentjava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unigearrent.unigearrentjava.models.CarPost;
import unigearrent.unigearrentjava.models.LessorDetails;
import unigearrent.unigearrentjava.models.Post;
import unigearrent.unigearrentjava.models.TrailerPost;
import unigearrent.unigearrentjava.services.repositoryservices.CarService;
import unigearrent.unigearrentjava.services.repositoryservices.LessorDetailsService;
import unigearrent.unigearrentjava.services.repositoryservices.TrailerService;
import unigearrent.unigearrentjava.services.repositoryservices.UserService;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/Post")
public class PostController {
    @Autowired
    private CarService carService;
    @Autowired
    private TrailerService trailerService;
    @Autowired
    private LessorDetailsService lessorDetailsService;
    @Autowired
    private UserService userService;
    @GetMapping("/byName/{name}")
    public ResponseEntity<?> GetByName(@PathVariable String name){
        List<Object> postList = new ArrayList<>();
        List<LessorDetails> details = lessorDetailsService.GetContainingName(name);
        if((long) details.size() == 0) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No matching element was found");
        details.forEach(detail -> postList.addAll(detail.getPosts()));
        System.out.println(details.get(0).getPosts());
        return ResponseEntity.status(HttpStatus.OK).body(postList);
    }
    @GetMapping("/byLocation/{location}")
    public ResponseEntity<?> GetByLocation(@PathVariable String location){
        List<Object> postList = new ArrayList<>(carService.GetAll().stream().filter(post -> post.getLocation().toLowerCase().contains(location.toLowerCase())).toList());
        postList.addAll(trailerService.GetAll().stream().filter(trailerPost -> trailerPost.getLocation().toLowerCase().contains(location.toLowerCase())).toList());
        return ResponseEntity.status(HttpStatus.OK).body(postList);
    }
    @GetMapping("/byUser/{user}")
    public ResponseEntity<?> GetByUsername(@PathVariable String user){
        try {
            Integer userId = userService.getUserByUsername(user).getId();
            return ResponseEntity.status(HttpStatus.OK).body(lessorDetailsService.GetById(userId).getPosts());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        }
    }
    @GetMapping("{id}")
    public ResponseEntity<?> GetById(@PathVariable Integer id){
        Optional<Object> post = Optional.ofNullable(carService.GetById(id));
        if(post.get() == Optional.empty()){
            post = Optional.ofNullable(trailerService.GetById(id));
        }
        if(post.get() == Optional.empty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No entity with the provided id was found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(post);
    }
    @GetMapping("/lessorDetails/{id}")
    public ResponseEntity<?> GetLessorDetailsByPostId(@PathVariable Integer id){
        Integer posterId;
        Optional<CarPost> carPost = carService.GetById(id);
        if(carPost.isPresent()){
            posterId = carPost.get().getPosterId();
        }
        else{
            Optional<TrailerPost> trailerPost = trailerService.GetById(id);
            if(trailerPost.isPresent()){
                posterId = trailerPost.get().getPosterId();
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No entity with the provided id was found");
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(lessorDetailsService.GetById(posterId));
    }
}
