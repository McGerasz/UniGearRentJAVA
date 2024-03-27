package unigearrent.unigearrentjava.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "posts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Post {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Integer Id;
    @JsonProperty("name")
    private String Name;
    @JsonProperty("location")
    private String Location;
    @JsonProperty("posterId")
    private Integer PosterId;
    @JsonProperty("description")
    private String Description;
    @JsonProperty("hourlyPrice")
    private Integer HourlyPrice;
    @JsonProperty("dailyPrice")
    private Integer DailyPrice;
    @JsonProperty("weeklyPrice")
    private Integer WeeklyPrice;
    @JsonProperty("securityDeposit")
    private Integer SecurityDeposit;
    @JsonProperty("lessorDetails")
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private LessorDetails LessorDetails;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH})
    private List<UserDetails> Users = new ArrayList<>();
}
