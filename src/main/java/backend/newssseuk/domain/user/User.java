package backend.newssseuk.domain.user;

import backend.newssseuk.domain.common.BaseEntity;
import backend.newssseuk.domain.enums.Category;
import backend.newssseuk.domain.enums.NotificationSetting;
import jakarta.persistence.*;
import lombok.*;

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

    private Integer attendance;

    private String attendanceDate;

    // todo string
    private NotificationSetting notificationSetting;

    private String role;
}
