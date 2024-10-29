package backend.newssseuk.domain.articleHashTag.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchingUIDto {
    private List<String> trending;
    private String name;
}
