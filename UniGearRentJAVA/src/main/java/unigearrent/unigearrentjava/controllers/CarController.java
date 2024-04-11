package unigearrent.unigearrentjava.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import unigearrent.unigearrentjava.models.CarPost;
import unigearrent.unigearrentjava.services.repositoryservices.CarService;
import unigearrent.unigearrentjava.services.repositoryservices.LessorDetailsService;
import unigearrent.unigearrentjava.services.repositoryservices.UserService;

import java.util.Optional;

@Validated
@RestController
@CrossOrigin("*")
@RequestMapping("/api/Car")
public class CarController {
    @Autowired
    private CarService carService;
    @Autowired
    private LessorDetailsService lessorDetailsService;
    @Autowired
    private UserService userService;
    @GetMapping("/{id}")
    public ResponseEntity<?> GetCar(@PathVariable Integer id){
        try{
            Optional<CarPost> post = carService.GetById(id);
            return ResponseEntity.status(HttpStatus.OK).body(post.get());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PostMapping("/")
    public ResponseEntity<?> PostCar(@RequestBody CarPost post, @RequestParam String userName){
        try{
            Integer id = userService.getUserByUsername(userName).getId();
            post.setPosterId(id);
            post.setLessorDetails(lessorDetailsService.GetById(id));
            carService.SaveCar(post);
            return  ResponseEntity.status(HttpStatus.CREATED).body(new Object());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeleteCar(@PathVariable Integer id){
        try{
            Optional<CarPost> post = carService.GetById(id);
            carService.Delete(post.get());
            return ResponseEntity.status(HttpStatus.OK).body("Deleted");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }
    @PutMapping("/")
    public ResponseEntity<?> PutCar(@RequestBody CarPost post){
        try {
            carService.Update(post);
            return ResponseEntity.status(HttpStatus.OK).body("");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }
}
