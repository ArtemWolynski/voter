package voter.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import voter.model.entities.Restaurant;

public interface RestaurantRepositorySpringDataJpa extends JpaRepository<Restaurant, Long> {

}
