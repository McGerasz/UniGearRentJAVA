package unigearrent.unigearrentjava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unigearrent.unigearrentjava.models.*;
import unigearrent.unigearrentjava.services.repositoryservices.*;

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
    private UserDetailsService userDetailsService;
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
    @PostMapping("/favourite")
    public ResponseEntity<?> PostFavourite(@RequestParam String userName, @RequestParam Integer id){
        Integer userId = 0;
        try {
            userId = userService.getUserByUsername(userName).getId();
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The user was not found");
        }
        UserDetails details = userDetailsService.GetById(userId);
        List<Post> favourites = details.getFavouriteIDs();
        Optional<CarPost> carPost = carService.GetById(id);
        if(carPost.isPresent()){
            favourites.add(carPost.get());
        }
        else {
            Optional<TrailerPost> trailerPost = trailerService.GetById(id);
            if (trailerPost.isPresent()) {
                favourites.add(trailerPost.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No post with the id " + id.toString() + " was found");
            }
        }
        userDetailsService.Update(details);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }
    @GetMapping("getFavourites/{userName}")
    public ResponseEntity<?> GetFavourites(@PathVariable String userName){
        Integer userId = 0;
        try {
            userId = userService.getUserByUsername(userName).getId();
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The user was not found");
        }
        UserDetails details = userDetailsService.GetById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(details.getFavouriteIDs());
    }
}
