package voter.service.user;


import voter.model.entities.Restaurant;

import java.util.List;

public interface UserService {

    boolean vote(int userId, int restaurantId);

}
