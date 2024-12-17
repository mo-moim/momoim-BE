package com.triplem.momoim.core.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "user_interest_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserInterestCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String subCategory;

    public static UserInterestCategoryEntity from(UserInterestCategory userInterestCategory) {
        return UserInterestCategoryEntity.builder()
            .id(userInterestCategory.getId())
            .userId(userInterestCategory.getUserId())
            .category(userInterestCategory.getCategory())
            .subCategory(userInterestCategory.getSubCategory())
            .build();
    }

    public UserInterestCategory toModel() {
        return UserInterestCategory.builder()
            .id(id)
            .userId(userId)
            .category(category)
            .subCategory(subCategory)
            .build();
    }
}
