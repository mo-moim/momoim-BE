package com.triplem.momoim.core.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserActiveLocationRepositoryImpl implements UserActiveLocationRepository{
    private final UserActiveLocationJpaRepository userActiveLocationJpaRepository;

    @Override
    public UserActiveLocation save(UserActiveLocation userActiveLocation) {
        return userActiveLocationJpaRepository.save(UserActiveLocationEntity.from(userActiveLocation)).toModel();
    }

    @Override
    public List<UserActiveLocation> findAllByUserId(Long userId) {
        return userActiveLocationJpaRepository.findAllByUserId(userId).stream().map(UserActiveLocationEntity::toModel)
                .toList();
    }

    @Override
    public void deleteAllByUserId(Long userId) {
        userActiveLocationJpaRepository.deleteAllByUserId(userId);
    }
}
