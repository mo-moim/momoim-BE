package com.triplem.momoim.core.domain.user;

import java.util.List;

public interface UserActiveLocationRepository {
    UserActiveLocation save(UserActiveLocation userActiveLocation);
    List<UserActiveLocation> findByUserId(Long userId);
}
