package com.triplem.momoim.core.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public void modify(Long userId, List<String> categories) {
        userInterestCategoryRepository.deleteAllByUserId(userId);
        for (String category: categories) {
            UserInterestCategory userInterestCategory = UserInterestCategory.builder()
                    .category(category)
                    .userId(userId)
                    .build();
            userInterestCategoryRepository.save(userInterestCategory);
        }
    }
}
