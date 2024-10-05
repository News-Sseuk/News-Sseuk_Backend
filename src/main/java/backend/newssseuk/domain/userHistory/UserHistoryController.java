package backend.newssseuk.domain.userHistory;

import backend.newssseuk.config.auth.AuthUser;
import backend.newssseuk.domain.user.User;
import backend.newssseuk.domain.userHistory.dto.UserHistorySaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class UserHistoryController {
    private final UserHistoryService userHistoryService;

    @PostMapping("/api/userHistory")
    public void saveUserHistory(@AuthUser User user, @RequestBody UserHistorySaveDto request){
        userHistoryService.addUserHistory(user, request.getArticleList());
    }
}
