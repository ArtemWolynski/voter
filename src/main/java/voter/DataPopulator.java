package voter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import voter.model.Role;
import voter.model.entities.MenuItem;
import voter.model.entities.Restaurant;
import voter.model.entities.User;
import voter.repository.RestaurantRepositorySpringDataJpa;
import voter.repository.UserRepositorySpringDataJpa;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Component
public class DataPopulator implements ApplicationRunner {


    private final UserRepositorySpringDataJpa userRepositorySpringDataJpa;


    private final RestaurantRepositorySpringDataJpa restaurantRepositorySpringDataJpa;

    private final
    PasswordEncoder passwordEncoder;

    @Autowired
    public DataPopulator(UserRepositorySpringDataJpa userRepositorySpringDataJpa, RestaurantRepositorySpringDataJpa restaurantRepositorySpringDataJpa, PasswordEncoder passwordEncoder) {
        this.userRepositorySpringDataJpa = userRepositorySpringDataJpa;
        this.restaurantRepositorySpringDataJpa = restaurantRepositorySpringDataJpa;
        this.passwordEncoder = passwordEncoder;
    }

    public void run(ApplicationArguments args){
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_USER);
        String password = passwordEncoder.encode("user");
        User user = new User("user", "user@gmail.com", password, roles);
        this.userRepositorySpringDataJpa.save(user);


        roles.add(Role.ROLE_ADMIN);
        password = passwordEncoder.encode("admin");
        User admin = new User("admin", "admin@gmail.com", password, roles);
        this.userRepositorySpringDataJpa.save(admin);

        Restaurant McDonalds = new Restaurant("McDonalds", null, 0);
        Restaurant KFC = new Restaurant("KFC", null, 0);


        MenuItem firstItem = new MenuItem("Rice", 200, McDonalds);
        MenuItem secondItem = new MenuItem("Meat", 300, McDonalds);

        List<MenuItem> menu = new LinkedList<>();
        menu.add(firstItem);
        menu.add(secondItem);

        McDonalds.setMenu(menu);

        restaurantRepositorySpringDataJpa.save(McDonalds);
        restaurantRepositorySpringDataJpa.save(KFC);

    }
}
