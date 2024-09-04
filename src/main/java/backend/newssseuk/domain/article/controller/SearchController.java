package backend.newssseuk.domain.article.controller;

import backend.newssseuk.domain.article.service.SearchService;
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

    //검색 기능 구현
    @PostMapping("/{keyword}/{onOff}/{sort}/{cursorTime}")
    @Operation(summary = "검색 api입니다")
    public ApiResponse<List<ArticleThumbnailDTO>> searchByKeyword (@PathVariable("cursorTime") LocalDateTime cursorTime,
                                                                   @PathVariable("keyword") String keyword,
                                                                   @PathVariable("onOff") String onOff,
                                                                   @PathVariable("sort") String sort) {
        return ApiResponse.onSuccess(searchService.searchByKeyword(cursorTime, keyword, onOff, sort));
    }
}
