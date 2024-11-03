package backend.newssseuk.domain.userHistory;

import backend.newssseuk.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {
    List<UserHistory> findTop10ByUserOrderByReadAtDesc(User user);
    long countByUser(User user);
}
