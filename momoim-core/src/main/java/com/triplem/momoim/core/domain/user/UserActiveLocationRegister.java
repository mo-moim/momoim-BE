package com.triplem.momoim.core.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserActiveLocationRegister {
    private final UserActiveLocationRepository userActiveLocationRepository;

    public void register(Long userId, List<String> locations) {
        for (String location: locations) {
            UserActiveLocation userActiveLocation = UserActiveLocation.builder()
                    .userId(userId)
                    .activeLocationType(location)
                    .build();
            userActiveLocationRepository.save(userActiveLocation);
        }
    }
}
