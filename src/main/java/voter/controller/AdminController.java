package voter.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import voter.service.restaurant.RestaurantService;
import voter.service.user.UserService;

@RestController
@Slf4j
public class AdminController {

    private final
    UserService userService;

    private final
    RestaurantService restaurantService;


    @Autowired
    public AdminController(RestaurantService restaurantService, UserService userService) {
        this.restaurantService = restaurantService;
        this.userService = userService;
    }
}
