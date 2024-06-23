package backend.newssseuk.domain.userAttendance.repository;

import backend.newssseuk.domain.userAttendance.UserAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAttendanceRepository extends JpaRepository<UserAttendance,Long> {
    List<UserAttendance> findByUserId(Long userId);
}
