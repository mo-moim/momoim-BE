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
    public List<UserActiveLocation> findByUserId(Long userId) {
        return userActiveLocationJpaRepository.findByUserId(userId).stream().map(UserActiveLocationEntity::toModel)
                .toList();
    }
}
