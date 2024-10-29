package backend.newssseuk.domain.userHistory.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserHistorySaveDto {
    private List<String> articleList;
}
