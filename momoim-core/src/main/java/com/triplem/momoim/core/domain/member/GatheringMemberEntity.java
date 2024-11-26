package com.triplem.momoim.core.domain.member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "gathering_member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GatheringMemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long gatheringId;

    public static GatheringMemberEntity from(GatheringMember gatheringMember) {
        return GatheringMemberEntity.builder()
            .id(gatheringMember.getId())
            .userId(gatheringMember.getUserId())
            .gatheringId(gatheringMember.getGatheringId())
            .build();
    }

    public GatheringMember toModel() {
        return GatheringMember.builder()
            .id(id)
            .userId(userId)
            .gatheringId(gatheringId)
            .build();
    }
}
