package backend.newssseuk.domain.recommendedArticle;

import backend.newssseuk.config.auth.AuthUser;
import backend.newssseuk.domain.user.User;
import backend.newssseuk.springbootmongodb.dto.ArticleResponseDto;
import backend.newssseuk.springbootmongodb.dto.ArticleThumbnailDTO;
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
    public List<ArticleThumbnailDTO> personalRecommendedArticles(@AuthUser User user) throws Exception {
        return recommendedArticleService.findPersonalRecommendedArticles(user);
    }
}
