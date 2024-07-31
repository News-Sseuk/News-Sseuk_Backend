package backend.newssseuk.domain.userHistory;

import backend.newssseuk.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {
    UserHistory findByUser(User user);
}
