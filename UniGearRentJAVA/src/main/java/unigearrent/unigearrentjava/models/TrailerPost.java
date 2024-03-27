package unigearrent.unigearrentjava.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class TrailerPost extends Post{
    @JsonProperty("width")
    private Integer Width;
    @JsonProperty("length")
    private Integer Length;
}
