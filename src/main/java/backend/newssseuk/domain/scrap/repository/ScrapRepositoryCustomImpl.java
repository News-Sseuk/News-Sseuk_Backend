package backend.newssseuk.domain.scrap.repository;

import backend.newssseuk.domain.article.Article;
import backend.newssseuk.domain.enums.Category;
import backend.newssseuk.domain.scrap.QScrap;
import backend.newssseuk.domain.user.User;
import backend.newssseuk.springbootmongodb.ArticleService;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ScrapRepositoryCustomImpl implements ScrapRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Article> getUserArticleByCategory(User user, Category category, Long lastArticleId){
        QScrap qScrap = QScrap.scrap;
        return jpaQueryFactory
                .select(qScrap.article)
                .from(qScrap)
                .where(
                        qScrap.user.eq(user)
                                .and(qScrap.article.category.eq(category))
                                .and(getLastArticleIdCondition(qScrap, lastArticleId))
                )
                .orderBy(qScrap.article.id.asc())  // 최신순 정렬
                .limit(3) // 페이지당 몇개의 데이터를 보여줄껀지
                .fetch();
    }

    private BooleanExpression getLastArticleIdCondition(QScrap qScrap, Long lastArticleId) {
        return lastArticleId != null ? qScrap.article.id.gt(lastArticleId) : null;
    }
}
