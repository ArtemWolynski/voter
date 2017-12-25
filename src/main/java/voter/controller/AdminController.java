package voter.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import voter.model.entities.MenuItem;
import voter.model.entities.Restaurant;
import voter.repository.RestaurantRepositorySpringDataJpa;
import voter.service.restaurant.RestaurantService;
import voter.util.CustomError;

import java.util.List;

/**
 * <p>This class is responsible for manipulation on entities registered in the database. It contains methods to create and update existing restaurants and it's menus.</p>
 * <p>Can only be accessed by the authorized user with admin rights.</p>
 */

@RestController
@RequestMapping (value = "/admin")
@Slf4j
public class AdminController {


    private final
    RestaurantService restaurantService;

    private final RestaurantRepositorySpringDataJpa restaurantRepositorySpringDataJpa;


    @Autowired
    public AdminController(RestaurantService restaurantService, RestaurantRepositorySpringDataJpa restaurantRepositorySpringDataJpa) {
        this.restaurantService = restaurantService;
        this.restaurantRepositorySpringDataJpa = restaurantRepositorySpringDataJpa;
    }

    /**
     * <p>Method is responsible for registering provided restaurant instance in the database.</p>
     *
     * <p>Please note that newly created restaurant should not contain the menu. Menu should be created explicitly using updateMenu method.</p>
     * @param restaurant the entity about to be saved in the database.
     * @return instance of the restaurant with the new field (id) set by the database.
     */
    @PostMapping("/restaurant/create")
    public ResponseEntity createRestaurant(@RequestBody Restaurant restaurant) {
        log.info("Creating restaurant with name: {}", restaurant.getName());
        Restaurant r = restaurantService.save(restaurant);

        if (r == null) {
            log.error("Error creating restaurant with name: {}", restaurant.getName());
            return new ResponseEntity<>(new CustomError("Error creating restaurant"), HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(r, HttpStatus.CREATED);
    }

    /**
     * <p>Method is responsible for updating existing restaurant instance.</p>
     *
     * <p>Please note that only restaurant gets updated, hence restaurant's menu is not involved in the update and should be updated explicitly using updateMenu method..</p>
     * @param restaurant the entity about to be saved in the database.
     * @return updated instance of the restaurant.
     */
    @PutMapping("/restaurant/update")
    public ResponseEntity updateRestaurant(@RequestBody Restaurant restaurant) {
        log.info("Updating restaurant with name: {}", restaurant.getName());
        Restaurant r = restaurantRepositorySpringDataJpa.getRestaurantWithMenu(restaurant.getId());
        if (r == null) {
            log.error("Restaurant with id: {} is not found", restaurant.getId());
            return new ResponseEntity<>(new CustomError("Error updating restaurant with id: " + restaurant.getId()), HttpStatus.NOT_FOUND);
        }
        restaurant.setMenu(r.getMenu());
        return new ResponseEntity<>(restaurantService.save(restaurant), HttpStatus.OK);
    }

    /**
     * <p>Method is responsible for creation/update of the menu for the restaurant previously registered in the database.</p>
     *
     * <p>Please note that on the current development stage insertion and deletion of single menu items is not possible.</p>
     * @param restaurantId id of the restaurant for which update is executed.
     * @param menu new list of menu items.
     * @return updated list of menu items.
     */
    @PostMapping("/menu/update")
    public ResponseEntity updateMenu(@RequestParam int restaurantId, @RequestBody List<MenuItem> menu) {
        Restaurant restaurant = restaurantRepositorySpringDataJpa.getRestaurantWithMenu(restaurantId);
        if (restaurant == null) {
            return new ResponseEntity<>(new CustomError(""), HttpStatus.NOT_FOUND);
        }

        restaurant.getMenu().clear();
        for (MenuItem m: menu) {
            m.setRestaurant(restaurant);
        }
        restaurant.getMenu().addAll(menu);  //http://cristian.sulea.net/blog.php?p=2014-06-28-hibernate-exception-a-collection-with-cascade-all-delete-orphan-was-no-longer-referenced-by-the-owning-entity-instance
        return new ResponseEntity<>(restaurantRepositorySpringDataJpa.save(restaurant).getMenu(), HttpStatus.OK);
    }
}
