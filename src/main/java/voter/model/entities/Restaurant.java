package voter.model.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "restaurants")
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant extends BaseEntity {

    static final long serialVersionUID = 1L;

    @NotEmpty
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "restaurant", orphanRemoval = true)
//    @JsonIgnore
    private List<MenuItem> menu;

    @NotNull
    @Column(name = "score", nullable = false)
    private int score;

    public String toString() {
        return " id = " + this.getId()+
                "\n name = " + this.getName()+
                "\n score = " + this.getScore();
    }
}
