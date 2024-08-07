package backend.newssseuk.domain.recommendedArticle;

import backend.newssseuk.domain.article.Article;
import backend.newssseuk.domain.article.ArticleHelper;
import backend.newssseuk.domain.article.repository.JpaArticleRepository;
import backend.newssseuk.domain.recommendedArticle.dto.RecommendedArticleUpdateDto;
import backend.newssseuk.domain.recommendedArticle.redis.RecommendedArticleRedisCachingService;
import backend.newssseuk.domain.recommendedArticle.redis.RecommendedArticleRedisEntity;
import backend.newssseuk.domain.recommendedArticle.redis.RecommendedArticleRedisRepository;
import backend.newssseuk.domain.relatedArticle.RelatedArticleRepository;
import backend.newssseuk.domain.user.User;
import backend.newssseuk.domain.userHistory.UserHistoryRepository;
import backend.newssseuk.springbootmongodb.dto.ArticleResponseDto;
import backend.newssseuk.springbootmongodb.redis.ArticleRedisEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendedArticleService {
    private final JpaArticleRepository jpaArticleRepository;
    private final UserHistoryRepository userHistoryRepository;
    private final RecommendedArticleRedisRepository recommendedArticleRedisRepository;
    private final RecommendedArticleRepository recommendedArticleRepository;
    private final RecommendedArticleRedisCachingService recommendedArticleRedisCachingService;
    private final ArticleHelper articleHelper;
    private final RelatedArticleRepository relatedArticleRepository;

    @Transactional
    public List<Article> collectingPersonalRecommendedArticles(User user) {      // 개인 추천기사 (검색 화면)
        List<Long> relatedArticleList = new ArrayList<>();
        List<Article> articleList = userHistoryRepository.findByUser(user).getArticleList();

        for (Article article : articleList) {
            if (relatedArticleRepository.findByArticle(article) != null) {
                List<Long> eachRelatedArticleList = article.getRelatedArticle().getArticleList();
                relatedArticleList.addAll(eachRelatedArticleList);
            }
        }

        List<Article> articleResponseList = relatedArticleList.stream()
                .distinct() // 중복 제거
                .map(articleId -> jpaArticleRepository.findById(articleId)
                        .orElseThrow()) // 예외 발생 시, 기본적으로 NoSuchElementException 던짐.
                .collect(Collectors.toList());

        // recommendedArticle 엔티티가 존재하면, 수정
        try{
            RecommendedArticleUpdateDto recommendedArticleUpdateDto = new RecommendedArticleUpdateDto(articleResponseList);
            RecommendedArticle recommendedArticle = recommendedArticleRepository.findByUser(user);
            recommendedArticle.update(recommendedArticleUpdateDto);
            recommendedArticleRepository.save(recommendedArticle);
        } catch (NullPointerException e) {
            // recommendedArticle 엔티티가 없으면, 생성
            RecommendedArticle recommendedArticle = recommendedArticleRepository.save(RecommendedArticle.builder()
                    .user(user)
                    .build());
            recommendedArticle.setArticleList(articleResponseList);
        }
        return articleResponseList;
    }

    @Transactional
    public List<ArticleResponseDto> findPersonalRecommendedArticles(User user) {
        List<ArticleResponseDto> articleResponseDtos;
        // redis에 있는 지 찾아보고
        // 등록 되어 있으면, 10개 기사 랜덤으로 뽑아서 보내줌.
        // 등록 되어 있지 않으면, cashing 후 동일 과정 진행.
        RecommendedArticleRedisEntity recommendedArticleRedisEntity = recommendedArticleRedisRepository.findByUserId(user.getId()).orElse(null);
        if (recommendedArticleRedisEntity != null) {
            articleResponseDtos = recommendedArticleRedisEntity.getArticleList();
        } else {
            List<Article> articleList = collectingPersonalRecommendedArticles(user);
            System.out.println(articleList.size());

            List<ArticleRedisEntity> articleRedisList = articleList.stream()
                    .map(mysqlArticle -> mysqlArticle.getMongoEntity(articleHelper))
                    .collect(Collectors.toList());
            articleResponseDtos = recommendedArticleRedisCachingService.cashingPersonalRecommendedArticles(user, articleRedisList).getArticleList();
        }
        // 최대 10개 기사 랜덤으로 뽑기
        // 리스트를 셔플하고 처음 10개의 요소를 선택
        return articleResponseDtos
                .stream()
                .collect(Collectors.collectingAndThen(Collectors.toList(), collected -> {
                    Collections.shuffle(collected);
                    return collected.stream().limit(10).collect(Collectors.toList());
                }));
    }
}
