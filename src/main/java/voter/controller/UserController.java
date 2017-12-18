package voter.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import voter.model.entities.MenuItem;
import voter.model.entities.Restaurant;
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

    private final
    RestaurantService restaurantService;

    @Autowired
    public UserController(UserService userService, RestaurantService restaurantService) {
        this.userService = userService;
        this.restaurantService = restaurantService;
    }

    @GetMapping(value = "/all")
    public ResponseEntity getAll() {
        List<Restaurant> restaurantList = restaurantService.getAll();
        return new ResponseEntity<>(restaurantList, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity get(@RequestParam int id) {
        log.info("Fetching menu item with id: {}", id);
        MenuItem menuItem = restaurantService.getMenuItem(id);
        if (menuItem == null) {
            log.error("Couldn't fetch item with id: {}", id);
            return new ResponseEntity<>(new CustomError("Couldn't get menu item with id: " + id), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(menuItem, HttpStatus.OK);
    }
}
