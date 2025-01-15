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

    @Override
    public User findUserByEmail(String email) {
        return userJpaRepository.findUserByEmail(email)
                .orElseThrow(() ->  new BusinessException(ExceptionCode.NOT_FOUND_MEMBER))
                .toModel();
    }

    @Override
    public void checkDuplicatedUserEmail(String email) {
        if (userJpaRepository.existsByEmail(email)) {
            throw new BusinessException(ExceptionCode.INVALID_MEMBER_HAS_DUPLICATED_EMAIL);
        }
    }

    @Override
    public void checkDuplicatedUserName(String name) {
        if (userJpaRepository.existsByName(name)) {
            throw new BusinessException(ExceptionCode.INVALID_MEMBER_HAS_DUPLICATED_NAME);
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByEmailAndGoogleAccountType(String email) {
        return userJpaRepository.existsByEmailAndAccountType(email, AccountType.GOOGLE);
    }

    @Override
    public boolean existsByEmailAndKakaoAccountType(String email) {
        return userJpaRepository.existsByEmailAndAccountType(email, AccountType.KAKAO);
    }

    @Override
    public void checkDuplicatedUserEmailAndEmailAccountType(String email) {
        if (userJpaRepository.existsByEmailAndAccountType(email, AccountType.EMAIL)) {
            throw new BusinessException(ExceptionCode.INVALID_MEMBER_HAS_DUPLICATED_EMAIL);
        }
    }
}
