package backend.newssseuk.domain.enums.converter;

import backend.newssseuk.domain.enums.ReportingReason;
import org.springframework.stereotype.Component;

@Component
public class ReportingReasonConverter {
    public ReportingReason fromReasonReportingReason(String reason) {
        for (ReportingReason reportingReason : ReportingReason.values()) {
            if (reportingReason.getReason().equals(reason)) {
                return reportingReason;
            }
        }
        throw new IllegalArgumentException("Invalid reporting reason: " + reason);
    }
}
