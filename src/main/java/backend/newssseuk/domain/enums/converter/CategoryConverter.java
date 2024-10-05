package backend.newssseuk.domain.enums.converter;

import backend.newssseuk.domain.enums.Category;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CategoryConverter {
    public Category fromKrCategory(String krName) {
        for (Category category : Category.values()) {
            if (category.getKorean().equals(krName)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid category name: " + krName);
    }

    public Set<Category> fromKrCategories(Set<String> krCategories) {
        return krCategories.stream()
                .map(this::fromKrCategory)
                .collect(Collectors.toSet());
    }

}
