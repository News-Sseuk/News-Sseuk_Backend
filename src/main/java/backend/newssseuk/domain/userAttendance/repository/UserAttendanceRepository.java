package backend.newssseuk.domain.userAttendance.repository;

import backend.newssseuk.domain.user.User;
import backend.newssseuk.domain.userAttendance.UserAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.YearMonth;
import java.util.Optional;

public interface UserAttendanceRepository extends JpaRepository<UserAttendance,Long> {
    Optional<UserAttendance> findByUserAndAttendanceDate(User user, YearMonth date);
}
