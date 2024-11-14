package backend.newssseuk.domain.scrap.controller;

import backend.newssseuk.config.auth.AuthUser;
import backend.newssseuk.domain.enums.Category;
import backend.newssseuk.domain.enums.converter.CategoryConverter;
import backend.newssseuk.domain.scrap.dto.ScrapResponseDTO;
import backend.newssseuk.domain.scrap.service.ScrapService;
import backend.newssseuk.domain.user.User;
import backend.newssseuk.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ScrapController {
    private final ScrapService scrapService;
    private final CategoryConverter categoryConverter;

    @PostMapping("scrap/{article_id}")
    public ApiResponse<String> scrapArticle(@AuthUser User user, @PathVariable("article_id") String article_id){
        return ApiResponse.onSuccess(scrapService.scrapArticleByArticleId(user, article_id));
    }

    @GetMapping("scrap/")
    public ApiResponse<ScrapResponseDTO> getScrapArticles(@AuthUser User user, @RequestParam(value = "articleId", required = false) Long articleId, @RequestParam("category") String category){
        Category categoryEnum = categoryConverter.fromKrCategory(category);
        return ApiResponse.onSuccess(scrapService.getScrapArticlesByUser(user, categoryEnum, articleId)); // 마지막으로 조회한 articleId가 필요함.
    }

    @GetMapping("scrap/categories")
    public ApiResponse<List<String>> getScrapCategories(@AuthUser User user){
        return ApiResponse.onSuccess(scrapService.getScrapCategories(user));
    }
}