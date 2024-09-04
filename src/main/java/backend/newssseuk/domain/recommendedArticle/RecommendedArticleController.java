package backend.newssseuk.domain.recommendedArticle;

import backend.newssseuk.config.auth.AuthUser;
import backend.newssseuk.domain.user.User;
import backend.newssseuk.springbootmongodb.dto.ArticleResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class RecommendedArticleController {
    private final RecommendedArticleService recommendedArticleService;

    @GetMapping("api/recommending")
    public List<ArticleResponseDto> personalRecommendedArticles(@AuthUser User user) {
        return recommendedArticleService.findPersonalRecommendedArticles(user);
    }
}
