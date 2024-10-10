package backend.newssseuk.domain.user.repository;

import backend.newssseuk.domain.enums.Category;
import backend.newssseuk.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User,Long> {

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long userId);

    @Query("SELECT u.interestedCategory FROM User u WHERE u.id = :userId")
    Set<Category> findInterestedCategoriesByUserId(@Param("userId") Long userId);
}
