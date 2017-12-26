package voter.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import voter.model.entities.MenuItem;
import voter.model.entities.Restaurant;
import voter.model.entities.User;
import voter.repository.RestaurantRepositorySpringDataJpa;
import voter.repository.UserRepositorySpringDataJpa;
import voter.util.CustomError;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>This class is responsible for providing minimum functionality for the end user.<br>
 * It contains methods to retrieve all the information about registered restaurants and vote for one of them.</p>
 * <p>Can only be accessed by the authorized user.</p>
 */

@RestController
@Slf4j
@CacheConfig(cacheNames = "restaurants")
@RequestMapping(value = "/user")
public class UserController {



    private final UserRepositorySpringDataJpa userRepositorySpringDataJpa;

    private final RestaurantRepositorySpringDataJpa restaurantRepositorySpringDataJpa;


    @Autowired
    public UserController(UserRepositorySpringDataJpa userRepositorySpringDataJpa,
                          RestaurantRepositorySpringDataJpa restaurantRepositorySpringDataJpa) {
        this.userRepositorySpringDataJpa = userRepositorySpringDataJpa;
        this.restaurantRepositorySpringDataJpa = restaurantRepositorySpringDataJpa;
    }

    /**
     * <p>Method is responsible for retrieving all the restaurants registered in the database.</p>
     *
     * <p>Due the performance optimisation provided list does not contain restaurant's menu. To retrieve the menu use getRestaurantWithMenu method.</p>
     * @return list of restaurants.
     */
    @GetMapping(value = "/restaurants")
    @Cacheable
    public ResponseEntity getAll() {
        log.info("Fetching all restaurants");
        List<Restaurant> restaurantList = restaurantRepositorySpringDataJpa.findAll();
        if(restaurantList == null || restaurantList.size() == 0) {
            log.error("Error fetching all restaurants");
            return new ResponseEntity<>(new CustomError("Error fetching all restaurants"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(restaurantList, HttpStatus.OK);
    }

    /**
     * <p>Method is responsible for retrieving menu for single restaurant.</p>
     * @param restaurantId id of the restaurant which menu needs to be retrieved.
     * @return updated instance of the restaurant.
     */
    @GetMapping(value = "/restaurant/menu")
    @Cacheable
    public ResponseEntity getRestaurantWithMenu(@RequestParam ("id") int restaurantId) {
        List<MenuItem> menuItems = restaurantRepositorySpringDataJpa.getRestaurantWithMenu(restaurantId).getMenu();
        System.out.println(menuItems);
        if (menuItems == null) {
            return new ResponseEntity<>(new CustomError("Menu for restaurant: " + restaurantId + " is not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(menuItems, HttpStatus.OK);
    }

    /**
     * <p>This method allows user to vote for the restaurant he wants to attend.</p>
     * @param restaurantId id of the restaurant which user wants to attend.
     * @return status of users vote.
     */
    @PostMapping(value = "restaurant/vote")
    public ResponseEntity upVote(@RequestParam ("id") int restaurantId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepositorySpringDataJpa.getUserByUserName(authentication.getName());

        if (user.getLastVoteDateTime().getDayOfYear() == LocalDateTime.now().getDayOfYear() && user.getLastVoteDateTime().getHour() > 10) {
            return new ResponseEntity<>(new CustomError("Something went wrong!"), HttpStatus.BAD_REQUEST);
        }
        user.setLastVoteDateTime(LocalDateTime.now());
        userRepositorySpringDataJpa.upVote(restaurantId);
        userRepositorySpringDataJpa.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
