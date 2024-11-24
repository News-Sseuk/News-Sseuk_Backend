package backend.newssseuk.domain.articleHashTag.service;

import backend.newssseuk.domain.articleHashTag.repository.ArticleHashTagRepository;
import backend.newssseuk.domain.articleHashTag.web.SearchingUIDto;
import backend.newssseuk.domain.hashTag.HashTag;
import backend.newssseuk.domain.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleHashTagService {
    private final ArticleHashTagRepository articleHashTagRepository;

    @Transactional
    public List<String> getHashTagListByArticleId(Long articleId) {
        List<HashTag> hashTagList = articleHashTagRepository.findAllHashTagsByArticleId(articleId);
        return hashTagList.stream()
                .map(HashTag::getName)
                .distinct()
                .collect(Collectors.toList());
    }

    public SearchingUIDto getTrending(User user) {
        LocalDateTime twoHoursAgo = LocalDateTime.now().minusHours(24); //2시간에서 24시간으로 변경
        List<HashTag> hashTagList = articleHashTagRepository.findTop8HashTagsInLast2Hours(twoHoursAgo);

        List<String> trending = hashTagList.stream()
                .map(HashTag::getName)
                .toList();
        return SearchingUIDto.builder()
                .name(user.getName())
                .trending(trending)
                .build();
    }

}
