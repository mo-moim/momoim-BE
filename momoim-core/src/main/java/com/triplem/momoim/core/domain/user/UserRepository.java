package com.triplem.momoim.core.domain.user;

public interface UserRepository {
    User save(User user);
    User findById(Long id);
    User findUserByEmail(String email);
    void checkDuplicatedUserEmail(String email);
    void checkDuplicatedUserName(String name);
    boolean existsByEmail(String email);
    void checkDuplicatedUserEmailAndEmailAccountType(String email);
    boolean existsByEmailAndGoogleAccountType(String email);
    boolean existsByEmailAndKakaoAccountType(String email);
}
