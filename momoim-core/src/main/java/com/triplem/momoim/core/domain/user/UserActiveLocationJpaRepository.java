package com.triplem.momoim.core.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserActiveLocationJpaRepository extends JpaRepository<UserActiveLocationEntity, Long> {
    List<UserActiveLocationEntity> findByUserId(Long userId);
}
