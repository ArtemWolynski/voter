package voter.service.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import voter.model.entities.Restaurant;
import voter.model.entities.User;
import voter.repository.UserRepositorySpringDataJpa;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final
    UserRepositorySpringDataJpa userRepositorySpringDataJpa;

    @Autowired
    public UserServiceImpl(UserRepositorySpringDataJpa userRepositorySpringDataJpa) {
        this.userRepositorySpringDataJpa = userRepositorySpringDataJpa;
    }

    @Override
    public List<Restaurant> getAll() {
        List<User> users = userRepositorySpringDataJpa.findAll();

        for (User user: users) {
            System.out.println(user);
            System.out.println(user.getId());
        }
        return null;
    }

    @Override
    public boolean vote(int restaurantId) {
        return false;
    }
}
