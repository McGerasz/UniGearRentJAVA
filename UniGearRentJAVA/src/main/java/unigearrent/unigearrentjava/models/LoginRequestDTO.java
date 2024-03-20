package unigearrent.unigearrentjava.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginRequestDTO {
    private String email;
    private String password;
}
