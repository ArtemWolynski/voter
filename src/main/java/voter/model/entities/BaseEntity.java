package voter.model.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Access(AccessType.FIELD)
public class BaseEntity implements Persistable<Integer> {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(value = AccessType.PROPERTY)
    @Setter
    private int id;

    @NotEmpty
    @Column(name = "name", nullable = false)
    protected String name;

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public boolean isNew() {
        return (this.id == 0);
    }
}
