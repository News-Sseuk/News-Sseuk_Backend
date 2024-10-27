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

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static backend.newssseuk.domain.article.QArticle.article;

@Service
@RequiredArgsConstructor
public class JpaArticleService {
    private final JpaArticleRepository jpaArticleRepository;
    private final JPAQueryFactory jpaQueryFactory;

    public void saveArticleDetailByAI(String targetUrl, Long article_id) throws Exception{
        HttpURLConnection conn = null;

        try {
            // articleId를 쿼리 파라미터로 추가
            String fullUrl = targetUrl + "?id=" +URLEncoder.encode(String.valueOf(article_id),"UTF-8");
            URL url = new URL(fullUrl);
            // url 연결
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            // 서버에서 온 데이터를 출력할 수 있는 상태인지
            conn.setDoOutput(true);
            // POST 호출
            conn.getOutputStream().write(new byte[0]);

            // 응답 코드 확인
            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                // 에러 처리
                throw new RuntimeException("Failed : HTTP error code : " + responseCode);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

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
