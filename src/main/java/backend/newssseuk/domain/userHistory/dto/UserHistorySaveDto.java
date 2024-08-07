package backend.newssseuk.domain.userHistory.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserHistorySaveDto {
    private Long userId;
    private List<Long> articleList;

    public UserHistorySaveDto(Long Id, List<Long> List){
        this.userId=Id;
        this.articleList=List;
    }

}
