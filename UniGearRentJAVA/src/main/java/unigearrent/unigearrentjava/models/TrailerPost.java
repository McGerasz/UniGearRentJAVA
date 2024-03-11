package unigearrent.unigearrentjava.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class TrailerPost extends Post{
    private Integer Width;
    private Integer Length;
}
