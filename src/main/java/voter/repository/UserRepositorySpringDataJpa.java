package voter.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import voter.model.entities.User;

public interface UserRepositorySpringDataJpa extends JpaRepository<User, Integer> {

}
