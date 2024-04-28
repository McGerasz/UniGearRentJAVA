package unigearrent.unigearrentjava.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LessorPutRequestDTO {
    private Integer Id;
    private String Username;
    private String Email;
    private String Phonenumber;
    private String Name;
}
