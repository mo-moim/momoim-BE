package com.triplem.momoim.core.domain.gathering;

import jakarta.persistence.Column;
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

    @Column(nullable = false)
    private Long managerId;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String subCategory;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecruitStatus recruitStatus;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private int participantCount;

    @Column(nullable = false)
    private LocalDateTime startAt;

    @Column(nullable = false)
    private LocalDateTime endAt;

    @Column(nullable = false)
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
            .participantCount(gathering.getParticipantCount())
            .startAt(gathering.getStartAt())
            .endAt(gathering.getEndAt())
            .createdAt(gathering.getCreatedAt())
            .build();
    }

    public Gathering toModel() {
        return Gathering.builder()
            .id(id)
            .managerId(managerId)
            .category(category)
            .subCategory(subCategory)
            .recruitStatus(recruitStatus)
            .name(name)
            .image(image)
            .capacity(capacity)
            .participantCount(participantCount)
            .startAt(startAt)
            .endAt(endAt)
            .createdAt(createdAt)
            .build();
    }
}
