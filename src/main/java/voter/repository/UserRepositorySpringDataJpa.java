package voter.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import voter.model.entities.User;

import javax.transaction.Transactional;


public interface UserRepositorySpringDataJpa extends JpaRepository<User, Integer> {

    @Transactional
    @Query("SELECT u FROM User u WHERE u.name = :name")
    User getUserByUserName(@Param("name") String username);

    @Transactional
    @Modifying
    @Query("UPDATE Restaurant r SET r.score = r.score + 1 WHERE r.id = :id")
    int upVote(@Param("id") int id);
}
