package com.triplem.momoim.core.domain.user;

import java.util.List;

public interface UserInterestCategoryRepository {
    UserInterestCategory save(UserInterestCategory userInterestCategory);

    List<UserInterestCategory> findAllByUserId(Long userId);

    void deleteAllByUserId(Long userId);

}
