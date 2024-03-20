package unigearrent.unigearrentjava.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserRegistrationRequestDTO {
    private String email;
    private String username;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String password;
}
