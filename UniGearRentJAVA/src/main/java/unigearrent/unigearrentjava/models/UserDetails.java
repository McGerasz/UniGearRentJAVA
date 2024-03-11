package unigearrent.unigearrentjava.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_details")
@NoArgsConstructor
@Getter
@Setter
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String Id;
    private String FirstName;
    private String LastName;
}
