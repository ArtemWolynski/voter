package voter.service.restaurant;


import voter.model.entities.MenuItem;
import voter.model.entities.Restaurant;

import java.util.List;

public interface RestaurantService {

    Restaurant get(int id);

    Restaurant save(Restaurant restaurant);

    List<Restaurant> getAll();

    Restaurant update(Restaurant user);

    void delete(int id);

    Restaurant getRestaurantWithMenu(int restaurantId);

    Restaurant updateMenuItem(MenuItem menuItem);
}
