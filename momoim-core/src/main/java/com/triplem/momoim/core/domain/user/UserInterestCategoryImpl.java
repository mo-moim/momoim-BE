package com.triplem.momoim.core.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserInterestCategoryImpl implements UserInterestCategoryRepository{
    private final UserInterestCategoryJpaRepository userInterestCategoryJpaRepository;
    @Override
    public UserInterestCategory save(UserInterestCategory userInterestCategory) {
        return userInterestCategoryJpaRepository.save(UserInterestCategoryEntity.from(userInterestCategory)).toModel();
    }
}
