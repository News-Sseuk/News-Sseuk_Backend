package backend.newssseuk.domain.userAttendance.service;

import backend.newssseuk.domain.user.User;
import backend.newssseuk.domain.user.service.UserService;
import backend.newssseuk.domain.userAttendance.UserAttendance;
import backend.newssseuk.domain.userAttendance.repository.UserAttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserAttendanceService {
    private final UserService userService;
    private final UserAttendanceRepository userAttendanceRepository;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yy-MM");

    //todo 메인 화면 api에서 get마다 호출
    public void increaseAttendance(Long userId, LocalDateTime localDateTime) {
        User user = userService.findUserById(userId);
        List<UserAttendance> attendances = userAttendanceRepository.findByUserId(userId);

        Optional<UserAttendance> latestUserAttendance = attendances.stream()
                .max(Comparator.comparing(attendance -> LocalDate.parse(attendance.getAttendanceDate(), dateFormatter)));

        latestUserAttendance.ifPresent(attendance -> {
            if (attendance.getUpdatedTime().toLocalDate().isBefore(localDateTime.toLocalDate())) {
                attendance.increaseAttendance();
            }
        });

        latestUserAttendance.orElseGet(() -> {
            UserAttendance userAttendance = UserAttendance.builder()
                    .user(user)
                    .attendanceDate(localDateTime.format(dateFormatter))
                    .attendance(1)
                    .build();
            userAttendanceRepository.save(userAttendance);
            return null;
        });
    }

    //todo 추후 마이페이지 구현시 호출
    public int getAttendance(Long userId)
    {
        User user = userService.findUserById(userId);
        List<UserAttendance> attendances = userAttendanceRepository.findByUserId(userId);

        Optional<UserAttendance> latestUserAttendance = attendances.stream()
                .max(Comparator.comparing(attendance -> LocalDate.parse(attendance.getAttendanceDate(), dateFormatter)));
        return latestUserAttendance.get().getAttendance();
    }
}
