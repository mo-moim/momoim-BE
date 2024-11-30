package com.triplem.momoim.core.domain.gathering;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
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
    private String description;

    @Column
    @Convert(converter = GatheringTagConverter.class)
    private List<String> tags;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GatheringLocation location;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private int participantCount;

    @Column
    private LocalDateTime nextGatheringAt;

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
            .description(gathering.getDescription())
            .tags(gathering.getTags())
            .location(gathering.getLocation())
            .capacity(gathering.getCapacity())
            .participantCount(gathering.getParticipantCount())
            .nextGatheringAt(gathering.getNextGatheringAt())
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
            .description(description)
            .tags(tags)
            .location(location)
            .capacity(capacity)
            .participantCount(participantCount)
            .nextGatheringAt(nextGatheringAt)
            .startAt(startAt)
            .endAt(endAt)
            .createdAt(createdAt)
            .build();
    }
}
