package unigearrent.unigearrentjava.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CarPost extends Post{
    private Integer NumberOfSeats;
    private Boolean CanItBeDelivered;
}
