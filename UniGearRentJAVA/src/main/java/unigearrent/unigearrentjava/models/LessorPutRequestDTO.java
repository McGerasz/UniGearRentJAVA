package unigearrent.unigearrentjava.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LessorPutRequestDTO {
    @JsonProperty("id")
    private Integer Id;
    @JsonProperty("username")
    private String Username;
    @JsonProperty("email")
    private String Email;
    @JsonProperty("phonenumber")
    private String Phonenumber;
    @JsonProperty("name")
    private String Name;
}
