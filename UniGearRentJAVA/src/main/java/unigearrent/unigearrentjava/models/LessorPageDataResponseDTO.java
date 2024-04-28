package unigearrent.unigearrentjava.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class LessorPageDataResponseDTO {
    private String Name;
    private String PhoneNumber;
    private String Email;
    private List<Post> Posts;
}
