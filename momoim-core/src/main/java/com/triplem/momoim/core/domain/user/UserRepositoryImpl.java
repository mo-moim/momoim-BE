package com.triplem.momoim.core.domain.user;

import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
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
            .orElseThrow(() -> new BusinessException(ExceptionCode.NOT_FOUND_MEMBER))
            .toModel();
    }
}
