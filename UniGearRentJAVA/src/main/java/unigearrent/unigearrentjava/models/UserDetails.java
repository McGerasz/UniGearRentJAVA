package unigearrent.unigearrentjava.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_details")
@NoArgsConstructor
@Getter
@Setter
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String FirstName;
    private String LastName;
    @ManyToMany(mappedBy = "Users")
    private List<Post> FavouriteIDs = new ArrayList<>();
}
