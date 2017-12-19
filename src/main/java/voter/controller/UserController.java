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

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@RestController
@Slf4j
@RequestMapping(value = "/user")
public class UserController {

    private final
    UserService userService;

    private final
    RestaurantService restaurantService;

    private final RestaurantRepositorySpringDataJpa restaurantRepositorySpringDataJpa;

    @Autowired
    public UserController(UserService userService, RestaurantService restaurantService, RestaurantRepositorySpringDataJpa restaurantRepositorySpringDataJpa) {
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.restaurantRepositorySpringDataJpa = restaurantRepositorySpringDataJpa;
    }

    @GetMapping(value = "/restaurants")
    public ResponseEntity getAll() {
        List<Restaurant> restaurantList = restaurantService.getAll();
        return new ResponseEntity<>(restaurantList, HttpStatus.OK);
    }

    @GetMapping(value = "/restaurant/menu")
    public ResponseEntity get(@RequestParam int id) {

        List<MenuItem> menuItems = restaurantRepositorySpringDataJpa.getRestaurantWithMenu(id).getMenu();
        if (menuItems == null) {
            return new ResponseEntity<>(new CustomError("Menu for restaurant: " + id + " is not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(menuItems, HttpStatus.OK);
    }
}
