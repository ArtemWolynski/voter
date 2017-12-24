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

    @Autowired
    private final UserRepositorySpringDataJpa userRepositorySpringDataJpa;

    @Autowired
    private final RestaurantRepositorySpringDataJpa restaurantRepositorySpringDataJpa;

    @Autowired
    PasswordEncoder passwordEncoder;

    public DataPopulator(UserRepositorySpringDataJpa userRepositorySpringDataJpa, RestaurantRepositorySpringDataJpa restaurantRepositorySpringDataJpa) {
        this.userRepositorySpringDataJpa = userRepositorySpringDataJpa;
        this.restaurantRepositorySpringDataJpa = restaurantRepositorySpringDataJpa;
    }

    public void run(ApplicationArguments args){
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_USER);
        String password = passwordEncoder.encode("password");
        User user = new User("user", "user@gmail.com", password, true, roles);
        this.userRepositorySpringDataJpa.save(user);


        roles.add(Role.ROLE_ADMIN);
        password = passwordEncoder.encode("admin");
        User admin = new User("admin", "admin@gmail.com", password, true, roles);
        this.userRepositorySpringDataJpa.save(admin);

        Restaurant restaurant = new Restaurant("TestRest", null, 0);

        MenuItem firstItem = new MenuItem("Rice", 200, restaurant);
        MenuItem secondItem = new MenuItem("Meat", 300, restaurant);

        List<MenuItem> menu = new LinkedList<>();
        menu.add(firstItem);
        menu.add(secondItem);

        restaurant.setMenu(menu);
        restaurantRepositorySpringDataJpa.save(restaurant);

        List<User> users = userRepositorySpringDataJpa.findAll();

        for (User u: users) {
            System.out.println(u);
        }
    }
}
