package voter.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import voter.model.entities.Restaurant;

import javax.transaction.Transactional;


public interface RestaurantRepositorySpringDataJpa extends JpaRepository<Restaurant, Integer> {

    @Transactional
    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.menu WHERE r.id = :id")
    Restaurant getRestaurantWithMenu(@Param("id") int restaurantId);
}
