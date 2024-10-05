package backend.newssseuk.domain.enums.converter;

import backend.newssseuk.domain.enums.Grade;
import org.springframework.stereotype.Component;

@Component
public class GradeConverter {
    public Grade convertGrade(int gradeValue) {
        if (gradeValue >= 0 && gradeValue <= 10) {
            return Grade.뉴싹;
        } else if (gradeValue >= 11 && gradeValue <= 20) {
            return Grade.쓱싹;
        } else if (gradeValue >= 21) {
            return Grade.싹싹;
        } else {
            throw new IllegalArgumentException("Invalid grade value: " + gradeValue);
        }
    }
}
