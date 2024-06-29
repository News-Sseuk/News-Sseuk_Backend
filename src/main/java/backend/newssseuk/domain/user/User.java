package backend.newssseuk.domain.user;

import backend.newssseuk.domain.common.BaseEntity;
import backend.newssseuk.domain.enums.Category;
import backend.newssseuk.domain.enums.NotificationSetting;
import backend.newssseuk.domain.userAttendance.UserAttendance;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    private String password;

    // todo string
    private Category interestedCategory;

    // todo string
    private NotificationSetting notificationSetting;

    private String role;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<UserAttendance> userAttendanceList = new ArrayList<>();

    public User update(String name, String email) {
        this.name = name;
        this.email = email;
        return this;
    }
}
