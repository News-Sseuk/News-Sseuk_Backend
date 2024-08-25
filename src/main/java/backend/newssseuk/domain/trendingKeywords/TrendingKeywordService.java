package backend.newssseuk.domain.trendingKeywords;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrendingKeywordService {
private final TrendingKeywordsRepository trendingKeywordsRepository;

    public List<String> getTrendingKeywords() {
        return trendingKeywordsRepository.findAll().stream()
                .map(TrendingKeywords::getTrendingKeyWord)
                .toList();
    }
}
