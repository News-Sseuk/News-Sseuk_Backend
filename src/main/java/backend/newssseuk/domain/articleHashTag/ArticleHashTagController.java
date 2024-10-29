package backend.newssseuk.domain.articleHashTag;

import backend.newssseuk.domain.articleHashTag.service.ArticleHashTagService;
import backend.newssseuk.payload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/trending")
public class ArticleHashTagController {
    private final ArticleHashTagService articleHashTagService;

    @GetMapping("/keywords")
    @Operation(summary = "검색창 화면 하단의 '지금 뜨는 뉴쓱' api입니다")
    public ApiResponse<List<String>> getTrendingKeywords() {
        return ApiResponse.onSuccess(articleHashTagService.getTrending());
    }
}
