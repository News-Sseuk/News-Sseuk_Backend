package backend.newssseuk.domain.user.repository;

import backend.newssseuk.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    Boolean existsByEmail(String email);

    User findByName(String username);

    User findByEmail(String email);
}
