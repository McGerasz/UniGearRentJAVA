package unigearrent.unigearrentjava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import unigearrent.unigearrentjava.models.CarPost;
import unigearrent.unigearrentjava.models.TrailerPost;
import unigearrent.unigearrentjava.services.repositoryservices.LessorDetailsService;
import unigearrent.unigearrentjava.services.repositoryservices.TrailerService;
import unigearrent.unigearrentjava.services.repositoryservices.UserService;

import java.util.Optional;

@Validated
@RestController
@CrossOrigin("*")
@RequestMapping("/api/Trailer")
public class TrailerController {
    @Autowired
    private TrailerService trailerService;
    @Autowired
    private LessorDetailsService lessorDetailsService;
    @Autowired
    private UserService userService;
    @GetMapping("/{id}")
    public ResponseEntity<?> GetTrailer(@PathVariable Integer id){
        try{
            Optional<TrailerPost> post = trailerService.GetById(id);
            return ResponseEntity.status(HttpStatus.OK).body(post.get());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PostMapping()
    public ResponseEntity<?> PostTrailer(@RequestBody TrailerPost post, @RequestParam String username){
        try{
            Integer id = userService.getUserByUsername(username).getId();
            post.setPosterId(id);
            post.setLessorDetails(lessorDetailsService.GetById(id));
            trailerService.SaveTrailer(post);
            return  ResponseEntity.status(HttpStatus.CREATED).body(null);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeleteTrailer(@PathVariable Integer id){
        try{
            Optional<TrailerPost> post = trailerService.GetById(id);
            trailerService.Delete(post.get());
            return ResponseEntity.status(HttpStatus.OK).body("Deleted");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }
    @PutMapping()
    public ResponseEntity<?> PutTrailer(@RequestBody TrailerPost post){
        try {
            trailerService.Update(post);
            return ResponseEntity.status(HttpStatus.OK).body("");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }
}
