package backend.newssseuk.domain.articleHashTag.repository;

import backend.newssseuk.domain.articleHashTag.ArticleHashTag;
import backend.newssseuk.domain.hashTag.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

import java.util.List;

public interface ArticleHashTagRepository extends JpaRepository<ArticleHashTag, Long> {

    @Query("SELECT a.hashTag FROM ArticleHashTag a WHERE a.article.id = :articleId")
    List<HashTag> findAllHashTagsByArticleId(@Param("articleId") Long articleId);

    @Query("SELECT aht.hashTag, COUNT(aht) as count " +
            "FROM ArticleHashTag aht " +
            "WHERE aht.createdTime >= :twoHoursAgo " +
            "GROUP BY aht.hashTag.name " +
            "ORDER BY count DESC " +
            "LIMIT 8")
    List<HashTag> findTop8HashTagsInLast2Hours(@Param("twoHoursAgo") LocalDateTime twoHoursAgo);

}
