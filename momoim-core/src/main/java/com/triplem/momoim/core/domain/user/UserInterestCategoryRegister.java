package com.triplem.momoim.core.domain.user;

import com.triplem.momoim.core.domain.gathering.enums.GatheringSubCategory;
import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserInterestCategoryRegister {
    private static final String SUB_CATEGORY_ALL_OPTION = "ALL";
    private final UserInterestCategoryRepository userInterestCategoryRepository;

    public void register(Long userId, List<String> categories) {
        validateInterestCategory(categories);

        if (isAllInterestCategory(categories)) {
            GatheringSubCategory[] subCategories = GatheringSubCategory.values();
            for (GatheringSubCategory subCategory : subCategories) {
                UserInterestCategory userInterestCategory = UserInterestCategory.builder()
                        .category(subCategory.getParentCategory().name())
                        .subCategory(subCategory.name())
                        .userId(userId)
                        .build();
                userInterestCategoryRepository.save(userInterestCategory);
            }
        }
        else {
            for (String category: categories) {
                List<GatheringSubCategory> subCategories = Arrays.stream(GatheringSubCategory.values())
                        .filter(subCategory -> subCategory.getParentCategory().name().equals(category))
                        .toList();

                for (GatheringSubCategory subCategory : subCategories) {
                    UserInterestCategory userInterestCategory = UserInterestCategory.builder()
                            .category(category)
                            .subCategory(subCategory.name())
                            .userId(userId)
                            .build();
                    userInterestCategoryRepository.save(userInterestCategory);
                }
            }
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

    private boolean isAllInterestCategory(List<String> categories) {
        return categories.size() == 1 && categories.get(0).equals(SUB_CATEGORY_ALL_OPTION);
    }

    private void validateInterestCategory(List<String> categories) {
        if (categories.contains("ALL") && categories.size() > 1) {
            throw new BusinessException(ExceptionCode.INVALID_MEMBER_INTEREST_CATEGORY);
        }
    }
}
