package unigearrent.unigearrentjava.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegistrationResponseDTO {
    private String Email;
    private String UserName;
    private String PhoneNumber;
}
