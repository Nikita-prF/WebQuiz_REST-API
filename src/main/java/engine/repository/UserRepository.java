package engine.repository;

import engine.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


/**
 * Management interface of the entity object {@link engine.entity.User} in the database
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String userEmail);
}
