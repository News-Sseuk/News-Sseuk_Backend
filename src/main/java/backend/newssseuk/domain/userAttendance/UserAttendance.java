package backend.newssseuk.domain.userAttendance;

import backend.newssseuk.domain.common.BaseEntity;
import backend.newssseuk.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.YearMonth;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserAttendance extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private YearMonth attendanceDate;

    //각 월별 출석 일수 추가
    private Integer attendance;

    public void increaseAttendance()
    {
        this.attendance++;
    }
}
