package voter.model.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @NotEmpty
    @Column(name = "name", nullable = false)
    protected String name;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "restaurant")
    @JsonIgnore
    private Set<MenuItem> menu;
}
