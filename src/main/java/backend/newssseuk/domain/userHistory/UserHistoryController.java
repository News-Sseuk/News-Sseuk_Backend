package backend.newssseuk.domain.userHistory;

import backend.newssseuk.domain.relatedArticle.dto.RelatedArticleSaveDto;
import backend.newssseuk.domain.user.User;
import backend.newssseuk.domain.user.repository.UserRepository;
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
    private final UserRepository userRepository;
    @PostMapping("api/userhistory/")
    public void saveUserHistory(@RequestBody UserHistorySaveDto request){
        User user = userRepository.findById(request.getUserId()).orElse(null);
        if (user != null)
            userHistoryService.addUserHistory(user,request.getArticleList());
    }
}
