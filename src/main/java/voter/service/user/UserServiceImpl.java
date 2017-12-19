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
    public boolean upVote(int restaurantId) {
        return false;
    }

    @Override
    public boolean downVite(int restaurantId) {
        return false;
    }
}
