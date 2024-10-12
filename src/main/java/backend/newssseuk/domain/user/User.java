package backend.newssseuk.domain.user;

import backend.newssseuk.domain.common.BaseEntity;
import backend.newssseuk.domain.enums.Category;
import backend.newssseuk.domain.enums.Grade;
import backend.newssseuk.domain.enums.NotificationSetting;
import backend.newssseuk.domain.userAttendance.UserAttendance;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
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

    @ElementCollection(targetClass = Category.class)
    @CollectionTable(name = "user_interests", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Set<Category> interestedCategory = new HashSet<>();

    // todo string
    private NotificationSetting notificationSetting;

    private String role;

    private Grade grade;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<UserAttendance> userAttendanceList = new ArrayList<>();

    public User updateName(String newName) {
        this.name = newName;
        return this;
    }

    public User updateCategory(Set<Category> interestedCategory) {
        this.interestedCategory = interestedCategory;
        return this;
    }

    public User updateGrade(Grade grade) {
        this.grade = grade;
        return this;
    }
}
