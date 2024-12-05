package com.triplem.momoim.core.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserInterestCategoryJpaRepository extends JpaRepository<UserInterestCategoryEntity, Long> {
    List<UserInterestCategoryEntity> findAllByUserId(Long userId);
    void deleteAllByUserId(Long userId);

}
