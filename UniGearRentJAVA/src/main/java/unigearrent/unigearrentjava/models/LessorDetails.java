package unigearrent.unigearrentjava.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "lessor_details")
public class LessorDetails {
    @Id
    private String PosterId;
    private String Name;
    @OneToMany(mappedBy = "LessorDetails",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> Posts = new ArrayList<>();
}
