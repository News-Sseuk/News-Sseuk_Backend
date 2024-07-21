package backend.newssseuk.domain.article;

import backend.newssseuk.springbootmongodb.Article;
import backend.newssseuk.springbootmongodb.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// ArticleHelper를 통해 Mysql 객체로부터 MongoDB 객체를 가져오는 메서드
@Component
@RequiredArgsConstructor
public class ArticleHelper {
    private final ArticleRepository articleRepository;

    public Article getMongoEntityByNosqlId(String nosqlId) {
        return articleRepository.findById(nosqlId).orElse(null);
    }
}
