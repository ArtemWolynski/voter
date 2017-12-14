package voter.model.entities;

import lombok.*;

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



    @Column(name = "price")
    private long Price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
}
