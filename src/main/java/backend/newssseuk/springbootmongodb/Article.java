package backend.newssseuk.springbootmongodb;

import backend.newssseuk.domain.enums.Category;
import com.mongodb.lang.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "Articles")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String title;

    private String press;

    @Nullable
    private String journalist;

    private List<String> image;

    private String content;

    @Enumerated(EnumType.STRING)
    private Category category;

    private LocalDateTime publishedDate;
}
