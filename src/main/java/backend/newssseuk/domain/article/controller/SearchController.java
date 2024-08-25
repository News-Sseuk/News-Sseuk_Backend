package backend.newssseuk.domain.article.controller;

import backend.newssseuk.config.auth.AuthUser;
import backend.newssseuk.domain.article.service.SearchService;
import backend.newssseuk.domain.user.User;
import backend.newssseuk.payload.ApiResponse;
import backend.newssseuk.springbootmongodb.dto.ArticleThumbnailDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;

    //회원 히스토리 기반 기사 추천 표시
    @GetMapping("/recommendings")
    @Operation(summary = "검색창 화면 하단의 '회원님을 위한 추천 기사 리스트 api입니다")
    public ApiResponse<List<ArticleThumbnailDTO>> getRecommendedArticles(@AuthUser User user) {
        return null;
    }

    //검색 기능 구현
    @PostMapping("/{cursorTime}")
    @Operation(summary = "검색 api입니다")
    public ApiResponse<List<ArticleThumbnailDTO>> searchByKeyword (@PathVariable("cursorTime") LocalDateTime cursorTime,
                                                                   @RequestParam("keyword") String keyword,
                                                                   @RequestParam("onOff") String onOff,
                                                                   @RequestParam("sort") String sort) {
        return ApiResponse.onSuccess(searchService.searchByKeyword(cursorTime, keyword, onOff, sort));
    }

    //신뢰도 on, off별 화면 구현
    //최신순, 신뢰도순별 화면 구

}
