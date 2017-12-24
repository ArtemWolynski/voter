package voter.controller;

import junit.framework.TestCase;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import voter.Application;
import voter.model.Role;
import voter.model.entities.MenuItem;
import voter.model.entities.Restaurant;
import voter.model.entities.User;
import voter.repository.RestaurantRepositorySpringDataJpa;
import voter.repository.UserRepositorySpringDataJpa;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class UserControllerTest extends TestCase {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));


    private User user;
    private List<Restaurant> restaurantList = new ArrayList<>();

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepositorySpringDataJpa userRepositorySpringDataJpa;

    @Autowired
    private RestaurantRepositorySpringDataJpa restaurantRepositorySpringDataJpa;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.stream(converters)
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception{
        mockMvc = webAppContextSetup(webApplicationContext).build();

        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_USER);
        roles.add(Role.ROLE_ADMIN);

        String password = passwordEncoder.encode("password");
        User user = new User("Will", "will@gmail.com", password, true, roles);
        this.user = this.userRepositorySpringDataJpa.save(user);


        Restaurant restaurant = new Restaurant("TestRest", null, 0);

        MenuItem firstItem = new MenuItem("Rice", 200, restaurant);
        MenuItem secondItem = new MenuItem("Meat", 300, restaurant);

        List<MenuItem> menu = new LinkedList<>();
        menu.add(firstItem);
        menu.add(secondItem);

        restaurant.setMenu(menu);

        restaurant = restaurantRepositorySpringDataJpa.save(restaurant);
        restaurantList.add(restaurant);
    }

    @Test
    public void userNotFound() throws Exception {
        mockMvc.perform(get("/user/get?id")
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllRestaurantsTest() throws Exception {
        mockMvc.perform(get("/user/restaurants")
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(this.restaurantList.get(0).getName())));
    }

    @Test
    public void getRestaurantTest() throws Exception {
        Restaurant restaurant = restaurantRepositorySpringDataJpa.findOne(this.restaurantList.get(0).getId());
        Assert.assertNotNull(restaurant);
    }

    @Test
    public void getRestaurantWithMenuTest() throws Exception {
        Restaurant restaurant = restaurantRepositorySpringDataJpa.getRestaurantWithMenu(this.restaurantList.get(0).getId());
        Assert.assertNotNull(restaurant.getMenu());
        Assert.assertTrue(restaurant.getMenu().size() > 0);
    }

    @Test
    public void vote() throws Exception {
        mockMvc.perform(post("/user/restaurant/vote?id=" + this.restaurantList.get(0).getId())
                .contentType(contentType))
                .andExpect(status().isOk());

        Assert.assertEquals(restaurantRepositorySpringDataJpa.getRestaurantWithMenu(this.restaurantList.get(0).getId()).getScore(), 1);
    }

    @Test
    public void createRestaurantTest() throws Exception {
        Restaurant restaurant = new Restaurant("BabaKitchen", null, 0);
        String restaurantJson = json(restaurant);

        mockMvc.perform(post("/admin/restaurant/create")
                .contentType(contentType)
                .content(restaurantJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateRestaurantTest() throws Exception {
        Restaurant restaurant = restaurantRepositorySpringDataJpa.getRestaurantWithMenu(this.restaurantList.get(0).getId());
        String oldName = restaurant.getName();
        restaurant.setName("New test name");

        String restaurantJson = json(restaurant);
        restaurantJson = "{\n  \"id\" : " + restaurant.getId() + ","
                + restaurantJson.substring(1, restaurantJson.length());

        mockMvc.perform(put("/admin/restaurant/update")
                .contentType(contentType)
                .content(restaurantJson))
                .andExpect(status().isOk());

        String newName = restaurantRepositorySpringDataJpa.getRestaurantWithMenu(this.restaurantList.get(0).getId()).getName();
        Assert.assertNotEquals(oldName, newName);
    }

    @After
    public void cleanup() throws Exception{
        userRepositorySpringDataJpa.deleteAll();
        restaurantRepositorySpringDataJpa.deleteAll();
    }




    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
