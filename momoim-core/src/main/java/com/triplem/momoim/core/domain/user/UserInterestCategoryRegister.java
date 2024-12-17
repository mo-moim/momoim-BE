package com.triplem.momoim.core.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserInterestCategoryRegister {
    private final UserInterestCategoryRepository userInterestCategoryRepository;

    public void register(Long userId, List<String> categories) {
        for (String category: categories) {
            UserInterestCategory userInterestCategory = UserInterestCategory.builder()
                    .category(category)
                    .userId(userId)
                    .build();
            userInterestCategoryRepository.save(userInterestCategory);
        }
    }

    public void modify(Long userId, Map<String, List<String>> categories) {
        userInterestCategoryRepository.deleteAllByUserId(userId);

        for (Map.Entry<String, List<String>> entry : categories.entrySet()) {
            String category = entry.getKey();
            List<String> subCategories = entry.getValue();

            for (String subCategory : subCategories) {
                UserInterestCategory userInterestCategory = UserInterestCategory.builder()
                        .category(category)
                        .subCategory(subCategory)
                        .userId(userId)
                        .build();
                userInterestCategoryRepository.save(userInterestCategory);
            }
        }
    }
}
