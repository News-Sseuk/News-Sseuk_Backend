package backend.newssseuk.domain.refreshToken;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(length = 1000)
    private String accessToken;

    //@Column(length = 1000)
    private String refreshToken;

    private String expiration;
}
