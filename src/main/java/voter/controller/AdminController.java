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
import voter.service.user.UserService;
import voter.util.CustomError;

import java.util.List;

@RestController
@RequestMapping (value = "/admin")
@Slf4j
public class AdminController {

    private final
    UserService userService;

    private final
    RestaurantService restaurantService;

    private final RestaurantRepositorySpringDataJpa restaurantRepositorySpringDataJpa;


    @Autowired
    public AdminController(RestaurantService restaurantService, UserService userService, RestaurantRepositorySpringDataJpa restaurantRepositorySpringDataJpa) {
        this.restaurantService = restaurantService;
        this.userService = userService;
        this.restaurantRepositorySpringDataJpa = restaurantRepositorySpringDataJpa;
    }

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

    @PutMapping("/restaurant/update")
    public ResponseEntity updateRestaurant(@RequestBody Restaurant restaurant) {
        log.info("Updating restaurant with name: {}", restaurant.getName());
        Restaurant r = restaurantService.get(restaurant.getId());
        if (r == null) {
            log.error("Restaurant with id: {} is not found", restaurant.getId());
            return new ResponseEntity<>(new CustomError("Error updating restaurant with id: " + restaurant.getId()), HttpStatus.NOT_FOUND);
        }
        restaurant.setMenu(r.getMenu());
        return new ResponseEntity<>(restaurantService.save(restaurant), HttpStatus.OK);
    }

    @PostMapping("/menu/update")
    public ResponseEntity updateMenu(@RequestParam int restaurantId, @RequestBody List<MenuItem> menu) {
        Restaurant restaurant = restaurantRepositorySpringDataJpa.findOne(restaurantId);
        if (restaurant == null) {
            return new ResponseEntity<>(new CustomError(""), HttpStatus.NOT_FOUND);
        }
        restaurant.setMenu(menu);
        restaurantRepositorySpringDataJpa.save(restaurant);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
