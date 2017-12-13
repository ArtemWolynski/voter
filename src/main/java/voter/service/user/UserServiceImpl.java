package voter.service.user;


import org.springframework.stereotype.Service;
import voter.model.entities.Restaurant;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public List<Restaurant> getAll() {
        return null;
    }

    @Override
    public boolean vote(int restaurantId) {
        return false;
    }
}
