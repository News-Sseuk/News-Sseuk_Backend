package backend.newssseuk.domain.userAttendance.service;

import backend.newssseuk.domain.enums.Grade;
import backend.newssseuk.domain.enums.converter.GradeConverter;
import backend.newssseuk.domain.user.User;
import backend.newssseuk.domain.user.repository.UserRepository;
import backend.newssseuk.domain.userAttendance.UserAttendance;
import backend.newssseuk.domain.userAttendance.repository.UserAttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class UserAttendanceService {
    private final UserAttendanceRepository userAttendanceRepository;
    private final UserRepository userRepository;
    private final GradeConverter gradeConverter;

    @Scheduled(cron = "0 0 0 1 * ?") // 매월 1일 00:00:00에 등급 자동 변경 설정
    @Transactional
    public void updateUserGrades() {
        YearMonth previousMonth = YearMonth.from(LocalDateTime.now().minusMonths(1));

        List<User> users = userRepository.findAll();

        for (User user : users) {
            int attendance = getAttendance(user, previousMonth);
            Grade newGrade = gradeConverter.convertGrade(attendance);
            user.updateGrade(newGrade);
            userRepository.save(user);
        }
    }

    public void increaseAttendance(User user) {
        YearMonth yearMonth = YearMonth.from(LocalDateTime.now());

        UserAttendance attendance = userAttendanceRepository
                .findByUserAndAttendanceDate(user, yearMonth)
                .orElse(null);

        if (attendance == null) {
            attendance = UserAttendance.builder()
                    .user(user)
                    .attendanceDate(yearMonth)
                    .attendance(1)
                    .build();
        } else if(! Objects.equals(attendance.getLastModifiedDate().toLocalDate(), LocalDate.now())) {
            attendance.increaseAttendance();
        }
        userAttendanceRepository.save(attendance);
    }

    public int getAttendance(User user, YearMonth yearMonth)
    {
        UserAttendance attendance = userAttendanceRepository
                .findByUserAndAttendanceDate(user, yearMonth)
                .orElse(null);

        if(attendance == null) {
            return 0;
        } else {
            return attendance.getAttendance();
        }
    }
}
