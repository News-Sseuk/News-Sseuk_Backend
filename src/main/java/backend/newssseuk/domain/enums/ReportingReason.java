package backend.newssseuk.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportingReason {
    INACCURATE_SUMMARY("부정확한 요약"),
    ILLOGICAL_STRUCTURE("비논리적인 문제제기"),
    INVALID_GROUNDS("타당하지 않은 근거"),
    NARROW_VIEW("편협한 시각"),
    SEVERE_CRITICISM("무차별적 비판");

    private final String reason_detail;
}
