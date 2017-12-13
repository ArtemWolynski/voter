package voter.model.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "restaurants")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Restaurant extends BaseEntity {

    static final long serialVersionUID = 1L;


    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "restaurant")
    @JsonIgnore
    private Set<MenuItem> menu;
}
