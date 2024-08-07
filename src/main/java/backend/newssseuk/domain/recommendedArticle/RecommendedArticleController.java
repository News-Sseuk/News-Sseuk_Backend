package backend.newssseuk.domain.recommendedArticle;

import backend.newssseuk.domain.user.User;
import backend.newssseuk.domain.user.repository.UserRepository;
import backend.newssseuk.springbootmongodb.ArticleService;
import backend.newssseuk.springbootmongodb.dto.ArticleResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class RecommendedArticleController {
    private final RecommendedArticleService recommendedArticleService;
    private final UserRepository userRepository;

    @GetMapping("api/personalrecommend/{userId}")
    public List<ArticleResponseDto> personalRecommendedArticles(@PathVariable("userId") Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return recommendedArticleService.findPersonalRecommendedArticles(user);
    }
}
