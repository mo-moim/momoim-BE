package com.triplem.momoim.core.domain.user;

public interface UserRepository {
    User save(User user);
    User findById(Long id);
    User findUserByEmail(String email);
}
