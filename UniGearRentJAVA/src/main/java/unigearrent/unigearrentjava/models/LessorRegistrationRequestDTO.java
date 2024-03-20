package unigearrent.unigearrentjava.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LessorRegistrationRequestDTO {
    private String email;
    private String username;
    private String phoneNumber;
    private String name;
    private String password;
}
