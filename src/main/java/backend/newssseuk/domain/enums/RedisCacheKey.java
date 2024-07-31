package backend.newssseuk.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RedisCacheKey {
    RECOMMENDED("기사에 따른 추천"),
    PERSONAL_RECOMMENDED("개인 히스토리에 따른 추천");
    private final String type;

}
