package backend.newssseuk.domain.articleHashTag.service;

import backend.newssseuk.domain.articleHashTag.repository.ArticleHashTagRepository;
import backend.newssseuk.domain.hashTag.HashTag;
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
                .collect(Collectors.toList());
    }

    public List<String> getTrending() {
        LocalDateTime twoHoursAgo = LocalDateTime.now().minusHours(2);
        List<HashTag> hashTagList = articleHashTagRepository.findTop8HashTagsInLast2Hours(twoHoursAgo);

        return hashTagList.stream()
                .map(HashTag::getName)
                .collect(Collectors.toList());
    }

}
