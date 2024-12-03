package com.triplem.momoim.core.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        return userJpaRepository.save(UserEntity.from(user)).toModel();
    }

    @Override
    public User findById(Long id) {
        return userJpaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."))
            .toModel();
    }
}
