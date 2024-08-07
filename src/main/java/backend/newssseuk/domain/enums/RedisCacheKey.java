package backend.newssseuk.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RedisCacheKey {
    RECOMMENDED("RecommendedArticleByContent"),
    PERSONAL_RECOMMENDED("RecommendedArticleByHistory");

    private final String type;

}
