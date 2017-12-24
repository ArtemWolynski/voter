package voter.model.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "menu")
@AllArgsConstructor
@NoArgsConstructor
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

    public String toString(){
        return this.getName() + ", "
                + this.getPrice();
    }
}
