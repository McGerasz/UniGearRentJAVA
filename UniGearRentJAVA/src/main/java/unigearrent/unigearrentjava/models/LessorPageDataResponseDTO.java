package unigearrent.unigearrentjava.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class LessorPageDataResponseDTO {
    private String Name;
    private String PhoneNumber;
    private String Email;
    private List<Post> Posts;
}
