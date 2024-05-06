package backend.newssseuk.domain.hashTag;

import backend.newssseuk.domain.articleHashTag.ArticleHashTag;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class HashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "hashTag", cascade = CascadeType.ALL)
    private List<ArticleHashTag> articleHashTagList = new ArrayList<>();
}
