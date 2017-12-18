package voter.service.restaurant;


import voter.model.entities.MenuItem;
import voter.model.entities.Restaurant;

import java.util.List;

public interface RestaurantService {
    Restaurant save(Restaurant user);

    Restaurant get(int id);

    List<Restaurant> getAll();

    Restaurant update(Restaurant user);

    void delete(int id);

    Restaurant getMenu(int id);

    MenuItem getMenuItem(int itemId);

    Restaurant updateMenuItem(int itemId);

    Restaurant deleteMenuItem(int itemId);
}
