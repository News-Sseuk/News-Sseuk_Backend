package backend.newssseuk.domain.articleHashTag.web;

import backend.newssseuk.config.auth.AuthUser;
import backend.newssseuk.domain.articleHashTag.service.ArticleHashTagService;
import backend.newssseuk.domain.user.User;
import backend.newssseuk.payload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/trending")
public class ArticleHashTagController {
    private final ArticleHashTagService articleHashTagService;

    @GetMapping("/keywords")
    @Operation(summary = "검색창 화면 하단의 '지금 뜨는 뉴쓱' api입니다")
    public ApiResponse<SearchingUIDto> getTrendingKeywords(@AuthUser User user) {
        return ApiResponse.onSuccess(articleHashTagService.getTrending(user));
    }
}
