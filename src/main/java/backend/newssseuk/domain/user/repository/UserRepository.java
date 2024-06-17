package backend.newssseuk.domain.user.repository;

import backend.newssseuk.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Boolean existsByEmail(String email);

    User findByName(String username);

    Optional<User> findByEmail(String email);
}
