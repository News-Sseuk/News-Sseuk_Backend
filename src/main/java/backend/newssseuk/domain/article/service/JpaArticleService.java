package backend.newssseuk.domain.article.service;

import backend.newssseuk.domain.article.Article;
import backend.newssseuk.domain.article.repository.JpaArticleRepository;
import backend.newssseuk.domain.enums.Category;
import backend.newssseuk.payload.exception.GeneralException;
import backend.newssseuk.payload.status.ErrorStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static backend.newssseuk.domain.article.QArticle.article;

@Service
@RequiredArgsConstructor
public class JpaArticleService {
    private final JpaArticleRepository jpaArticleRepository;
    private final JPAQueryFactory jpaQueryFactory;

    public Article findByMongoId(String mongoId) {
        Optional<Article> optionalArticle = jpaArticleRepository.findByNosqlId(mongoId);
        if(optionalArticle.isPresent()) {
            return optionalArticle.get();
        }
        throw new GeneralException(ErrorStatus.ARTICLE_NOT_FOUND, "해당 nosqlId와 일치하는 데이터가 mysql에 존재하지 않습니다.");
    }

    public List<Article> findAllByCategoryOrderByDate(Category category, LocalDateTime time) {
        if(time == null) {
            time=LocalDateTime.now();
        }
        return jpaQueryFactory
                .selectFrom(article)
                .where(
                        ltOrderTime(time),
                        article.category.eq(category)
                ).orderBy(article.crawledTime.desc())
                .limit(20)
                .fetch();
    }

    private BooleanExpression ltOrderTime(LocalDateTime cursorTime) {
        return cursorTime == null ? null : article.crawledTime.lt(cursorTime);
    }
}
