package voter.model.entities;



import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import voter.model.Role;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User extends BaseEntity {

    public User (String name, String email, String password, Set <Role> roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.enabled = true;
    }

    static final long serialVersionUID = 1L;

    @NotEmpty
    @Column(name = "name", nullable = false)
    protected String name;

    @Column(name = "email")
    @NotNull
    private String email;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "lastvote")
    private LocalDateTime lastVoteDateTime = LocalDateTime.MIN;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;
}
