package voter.service.restaurant;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import voter.model.entities.MenuItem;
import voter.model.entities.Restaurant;
import voter.repository.RestaurantRepositorySpringDataJpa;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final
    RestaurantRepositorySpringDataJpa restaurantRepositorySpringDataJpa;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepositorySpringDataJpa restaurantRepositorySpringDataJpa) {
        this.restaurantRepositorySpringDataJpa = restaurantRepositorySpringDataJpa;
    }

    @Override
    public Restaurant save(Restaurant user) {
        return null;
    }

    @Override
    public Restaurant get(int id) {
        return null;
    }

    @Override
    public List<Restaurant> getAll() {
        return restaurantRepositorySpringDataJpa.findAll();
    }

    @Override
    public Restaurant update(Restaurant user) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Restaurant getMenu(int id) {
        return null;
    }

    @Override
    public MenuItem getMenuItem(int itemId) {
        return null;
    }

    @Override
    public Restaurant updateMenuItem(int itemId) {
        return null;
    }

    @Override
    public Restaurant deleteMenuItem(int itemId) {
        return null;
    }
}
