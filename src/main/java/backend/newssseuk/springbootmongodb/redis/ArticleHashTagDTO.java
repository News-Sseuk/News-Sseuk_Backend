package backend.newssseuk.springbootmongodb.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleHashTagDTO {
    private Long id;
    private Long articleId; // Article의 ID만 저장
    private String hashTagName; // HashTag 이름만 저장
    private LocalDateTime createdTime;
}
