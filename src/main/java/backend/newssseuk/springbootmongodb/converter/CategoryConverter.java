package backend.newssseuk.springbootmongodb.converter;

import backend.newssseuk.domain.enums.Category;
import org.springframework.stereotype.Component;

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
}
