package backend.newssseuk.domain.scrap.service;

import backend.newssseuk.domain.article.Article;
import backend.newssseuk.domain.article.repository.JpaArticleRepository;
import backend.newssseuk.domain.scrap.Scrap;
import backend.newssseuk.domain.scrap.repository.ScrapRepository;
import backend.newssseuk.domain.user.User;
import backend.newssseuk.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ScrapService {
    private final JpaArticleRepository jpaArticleRepository;
    private final UserRepository userRepository;
    private final ScrapRepository scrapRepository;

    public String scrapArticleByArticleId(Long user_id, Long article_id){
        User user = userRepository.findById(user_id).get();
        Article article = jpaArticleRepository.findById(article_id).get();

        Scrap scrapEntity = Scrap.builder()
                .user(user)
                .article(article)
                .build();
        scrapRepository.save(scrapEntity);
        return article.getCategory().getKorean();
    }
}