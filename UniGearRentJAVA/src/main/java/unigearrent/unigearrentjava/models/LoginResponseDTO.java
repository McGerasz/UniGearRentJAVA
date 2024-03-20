package unigearrent.unigearrentjava.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginResponseDTO {
    private String UserName;
    private String PhoneNumber;
    private String Token;
    private Integer Id;
}
