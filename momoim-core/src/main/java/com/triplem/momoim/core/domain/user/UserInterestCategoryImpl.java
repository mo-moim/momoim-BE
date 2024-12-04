package com.triplem.momoim.core.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserInterestCategoryImpl implements UserInterestCategoryRepository{
    private final UserInterestCategoryJpaRepository userInterestCategoryJpaRepository;
    @Override
    public UserInterestCategory save(UserInterestCategory userInterestCategory) {
        return userInterestCategoryJpaRepository.save(UserInterestCategoryEntity.from(userInterestCategory)).toModel();
    }

    @Override
    public List<UserInterestCategory> findAllByUserId(Long userId) {
        return userInterestCategoryJpaRepository.findAllByUserId(userId).stream().map(UserInterestCategoryEntity::toModel)
                .toList();
    }
}
