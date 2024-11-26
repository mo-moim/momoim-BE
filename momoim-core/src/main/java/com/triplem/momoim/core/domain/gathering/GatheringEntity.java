package com.triplem.momoim.core.domain.gathering;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "gathering")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GatheringEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long managerId;

    @Enumerated(EnumType.STRING)
    private GatheringCategory category;

    @Enumerated(EnumType.STRING)
    private GatheringSubCategory subCategory;

    @Enumerated(EnumType.STRING)
    private RecruitStatus recruitStatus;

    private String name;

    private String image;

    private int capacity;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    private LocalDateTime createdAt;

    public static GatheringEntity from(Gathering gathering) {
        return GatheringEntity.builder()
            .id(gathering.getId())
            .managerId(gathering.getManagerId())
            .category(gathering.getCategory())
            .subCategory(gathering.getSubCategory())
            .recruitStatus(gathering.getRecruitStatus())
            .name(gathering.getName())
            .image(gathering.getImage())
            .capacity(gathering.getCapacity())
            .startAt(gathering.getStartAt())
            .endAt(gathering.getEndAt())
            .createdAt(gathering.getCreatedAt())
            .build();
    }
}
