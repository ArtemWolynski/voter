package voter.model.entities;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "menu")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MenuItem extends BaseEntity {

    static final long serialVersionUID = 1L;

    @NotEmpty
    @Column(name = "name", nullable = false)
    protected String name;

    @Column(name = "price")
    private long Price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
}
