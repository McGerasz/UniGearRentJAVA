package unigearrent.unigearrentjava.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "posts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Post {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String Name;
    private String Location;
    private String PosterId;
    private String Description;
    private Integer HourlyPrice;
    private Integer DailyPrice;
    private Integer SecurityDeposit;
}
