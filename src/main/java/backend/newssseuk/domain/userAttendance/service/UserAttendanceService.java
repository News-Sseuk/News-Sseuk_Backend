package backend.newssseuk.domain.userAttendance.service;

import backend.newssseuk.domain.user.User;
import backend.newssseuk.domain.user.repository.UserRepository;
import backend.newssseuk.domain.userAttendance.UserAttendance;
import backend.newssseuk.domain.userAttendance.repository.UserAttendanceRepository;
import backend.newssseuk.payload.exception.GeneralException;
import backend.newssseuk.payload.status.ErrorStatus;
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
    private final UserRepository userRepository;
    private final UserAttendanceRepository userAttendanceRepository;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yy-MM");

    //todo 메인 화면 api에서 get마다 호출
    public void increaseAttendance(Long userId, LocalDateTime localDateTime) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new GeneralException(ErrorStatus.USER_NOT_FOUND);
        }
        List<UserAttendance> attendances = userAttendanceRepository.findByUserId(userId);

        Optional<UserAttendance> latestUserAttendance = attendances.stream()
                .max(Comparator.comparing(attendance -> LocalDate.parse(attendance.getAttendanceDate(), dateFormatter)));

        if (latestUserAttendance.isPresent()) {
            if(latestUserAttendance.get().getUpdatedTime().toLocalDate().isBefore(localDateTime.toLocalDate())) {
                latestUserAttendance.get().increaseAttendance();
            }
            else {
                return;
            }
        }
        else {
            UserAttendance userAttendance = UserAttendance.builder()
                                                        .user(user.get())
                                                        .attendanceDate(localDateTime.format(dateFormatter))
                                                        .attendance(1)
                                                        .build();
            userAttendanceRepository.save(userAttendance);
        }
    }
}
