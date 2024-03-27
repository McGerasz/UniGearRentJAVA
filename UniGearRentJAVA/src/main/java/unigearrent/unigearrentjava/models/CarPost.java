package unigearrent.unigearrentjava.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Entity;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarPost extends Post{
    @JsonProperty("numberOfSeats")
    private Integer NumberOfSeats;
    @JsonProperty("canDeliverToYou")
    private Boolean CanItBeDelivered;
}
