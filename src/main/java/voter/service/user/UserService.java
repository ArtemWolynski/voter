package voter.service.user;


import voter.model.entities.Restaurant;

import java.util.List;

public interface UserService {

    List<Restaurant> getAll();

    boolean vote (int restaurantId);
}
