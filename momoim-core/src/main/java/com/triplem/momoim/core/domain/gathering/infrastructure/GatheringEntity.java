package com.triplem.momoim.core.domain.gathering.infrastructure;

import com.triplem.momoim.core.domain.gathering.enums.GatheringLocation;
import com.triplem.momoim.core.domain.gathering.enums.GatheringStatus;
import com.triplem.momoim.core.domain.gathering.model.Gathering;
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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GatheringStatus status;

    @Column(nullable = false, length = 500)
    private String image;

    @Column(nullable = false, length = 5000)
    private String description;

    @Column
    private String address;

    @Column(length = 1000)
    @Convert(converter = GatheringTagConverter.class)
    private List<String> tags;

    @Enumerated(EnumType.STRING)
    private GatheringLocation location;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private int participantCount;

    @Column(nullable = false)
    private int wishlistCount;

    @Column(nullable = false)
    private Boolean isPeriodic;

    @Column
    private LocalDateTime nextGatheringAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime lastModifiedAt;

    public static GatheringEntity from(Gathering gathering) {
        return GatheringEntity.builder()
            .id(gathering.getId())
            .managerId(gathering.getManagerId())
            .category(gathering.getCategory())
            .subCategory(gathering.getSubCategory())
            .name(gathering.getName())
            .status(gathering.getStatus())
            .image(gathering.getImage())
            .description(gathering.getDescription())
            .address(gathering.getAddress())
            .tags(gathering.getTags())
            .location(gathering.getLocation())
            .capacity(gathering.getCapacity())
            .participantCount(gathering.getParticipantCount())
            .wishlistCount(gathering.getWishlistCount())
            .isPeriodic(gathering.getIsPeriodic())
            .nextGatheringAt(gathering.getNextGatheringAt())
            .createdAt(gathering.getCreatedAt())
            .lastModifiedAt(gathering.getLastModifiedAt())
            .build();
    }

    public Gathering toModel() {
        return Gathering.builder()
            .id(id)
            .managerId(managerId)
            .category(category)
            .subCategory(subCategory)
            .name(name)
            .status(status)
            .image(image)
            .description(description)
            .address(address)
            .tags(tags)
            .location(location)
            .capacity(capacity)
            .participantCount(participantCount)
            .wishlistCount(wishlistCount)
            .isPeriodic(isPeriodic)
            .nextGatheringAt(nextGatheringAt)
            .createdAt(createdAt)
            .lastModifiedAt(lastModifiedAt)
            .build();
    }
}
