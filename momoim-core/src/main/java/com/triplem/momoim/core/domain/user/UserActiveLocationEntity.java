package com.triplem.momoim.core.domain.user;

import com.triplem.momoim.core.domain.gathering.GatheringLocation;
import com.triplem.momoim.core.domain.member.GatheringMember;
import com.triplem.momoim.core.domain.member.GatheringMemberEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "user_active_location")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserActiveLocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    private String activeLocationType;

    public static UserActiveLocationEntity from(UserActiveLocation userActiveLocation) {
        return UserActiveLocationEntity.builder()
                .id(userActiveLocation.getId())
                .userId(userActiveLocation.getUserId())
                .activeLocationType(userActiveLocation.getActiveLocationType())
                .build();
    }

    public UserActiveLocation toModel() {
        return UserActiveLocation.builder()
                .id(id)
                .userId(userId)
                .activeLocationType((activeLocationType))
                .build();
    }
}
