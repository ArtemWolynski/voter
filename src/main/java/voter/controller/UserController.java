package voter.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import voter.model.entities.MenuItem;
import voter.model.entities.Restaurant;
import voter.repository.UserRepositorySpringDataJpa;
import voter.service.restaurant.RestaurantService;
import voter.service.user.UserService;
import voter.util.CustomError;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "/user")
public class UserController {

    private final
    UserService userService;

    private final UserRepositorySpringDataJpa userRepositorySpringDataJpa;

    private final
    RestaurantService restaurantService;


    @Autowired
    public UserController(UserService userService, RestaurantService restaurantService, UserRepositorySpringDataJpa userRepositorySpringDataJpa) {
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.userRepositorySpringDataJpa = userRepositorySpringDataJpa;
    }

    @GetMapping(value = "/restaurants")
    public ResponseEntity getAll() {
        List<Restaurant> restaurantList = restaurantService.getAll();
        return new ResponseEntity<>(restaurantList, HttpStatus.OK);
    }

    @GetMapping(value = "/restaurant/menu")
    public ResponseEntity get(@RequestParam ("id") int restaurantId) {
        List<MenuItem> menuItems = restaurantService.getRestaurantWithMenu(restaurantId).getMenu();
        if (menuItems == null) {
            return new ResponseEntity<>(new CustomError("Menu for restaurant: " + restaurantId + " is not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(restaurantService.getRestaurantWithMenu(restaurantId), HttpStatus.OK);
    }

    @PostMapping(value = "restaurant/vote")
    public ResponseEntity upVote(@RequestParam ("id") int restaurantId) {
        int userId = 0;
        if(!userService.vote(userId, restaurantId)) {
            return new ResponseEntity<>(new CustomError("You've already voted"), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
