package voter.service.restaurant;


import voter.model.entities.Restaurant;

public interface RestaurantService {
    Restaurant save(Restaurant user);

    Restaurant get(int id);

    Restaurant update(Restaurant user);

    void delete(int id);

    Restaurant getMenu(int id);

    Restaurant getMenuItem(int itemId);

    Restaurant updateMenuItem(int itemId);

    Restaurant deleteMenuItem(int itemId);
}
