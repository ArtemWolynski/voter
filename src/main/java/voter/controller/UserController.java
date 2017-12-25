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

/**
 * <p>This class is responsible for providing minimum functionality for the end user.<br>
 * It contains methods to retrieve all the information about registered restaurants and vote for one of them.</p>
 * <p>Can only be accessed by the authorized user.</p>
 */

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

    /**
     * <p>Method is responsible for retrieving all the restaurants registered in the database.</p>
     *
     * <p>Due the performance optimisation provided list does not contain restaurant's menu. To retrieve the menu use getRestaurantWithMenu method.</p>
     * @return list of restaurants.
     */
    @GetMapping(value = "/restaurants")
    public ResponseEntity getAll() {
        List<Restaurant> restaurantList = restaurantService.getAll();
        return new ResponseEntity<>(restaurantList, HttpStatus.OK);
    }

    /**
     * <p>Method is responsible for retrieving menu for single restaurant.</p>
     * @param restaurantId id of the restaurant which menu needs to be retrieved.
     * @return updated instance of the restaurant.
     */
    @GetMapping(value = "/restaurant/menu")
    public ResponseEntity getRestaurantWithMenu(@RequestParam ("id") int restaurantId) {
        List<MenuItem> menuItems = restaurantService.getRestaurantWithMenu(restaurantId).getMenu();
        if (menuItems == null) {
            return new ResponseEntity<>(new CustomError("Menu for restaurant: " + restaurantId + " is not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(restaurantService.getRestaurantWithMenu(restaurantId), HttpStatus.OK);
    }

    /**
     * <p>This method allows user to vote for the restaurant he wants to attend.</p>
     * @param restaurantId id of the restaurant which user wants to attend.
     * @return status of users vote.
     */
    @PostMapping(value = "restaurant/vote")
    public ResponseEntity upVote(@RequestParam ("id") int restaurantId) {
        int userId = 0;
        if(!userService.vote(userId, restaurantId)) {
            return new ResponseEntity<>(new CustomError("You've already voted"), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
