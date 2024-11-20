package backend.newssseuk.domain.scrap.repository;

import backend.newssseuk.domain.article.Article;
import backend.newssseuk.domain.article.service.JpaArticleService;
import backend.newssseuk.domain.article.QArticle;
import backend.newssseuk.domain.enums.Category;
import backend.newssseuk.domain.scrap.QScrap;
import backend.newssseuk.domain.user.User;
import backend.newssseuk.springbootmongodb.ArticleService;
import com.querydsl.core.types.Projections;
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
    private final JpaArticleService jpaArticleService;

    @Override
    public List<Article> getUserArticleByCategory(User user, Category category, String lastArticleId){
        QScrap qScrap = QScrap.scrap;
        return jpaQueryFactory
                .select(qScrap.article)
                .from(qScrap)
                .where(
                        qScrap.user.eq(user)
                                .and(qScrap.article.category.eq(category))
                                .and(lastArticleId != null ? getLastArticleIdCondition(qScrap, lastArticleId) : null)
                )
                .orderBy(qScrap.article.id.asc())  // 최신순 정렬
                .limit(3) // 페이지당 몇개의 데이터를 보여줄껀지
                .fetch();
    }

    private BooleanExpression getLastArticleIdCondition(QScrap qScrap, String lastArticleId) {
        Article jpaArticle = jpaArticleService.findByMongoId(lastArticleId);
        return qScrap.article.id.gt(jpaArticle.getId());
    }

    @Override
    public List<Category> getCategoryByUser(User user) {
        QScrap scrap = QScrap.scrap;
        QArticle article = QArticle.article;

        return jpaQueryFactory
                .select(article.category)
                .from(scrap)
                .join(scrap.article, article)
                .where(scrap.user.eq(user))
                .groupBy(article.category)
                .fetch();
    }
}
