package backend.newssseuk.springbootmongodb;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "naver-articles")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @Id
    private Long id;

    private String title;

    private String press;

    private String journalist;

    private String image;

    private String content;
}
