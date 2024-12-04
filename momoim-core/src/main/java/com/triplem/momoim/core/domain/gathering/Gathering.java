package com.triplem.momoim.core.domain.gathering;

import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Gathering {
    private Long id;
    private Long managerId;
    private String category;
    private String subCategory;
    private String name;
    private GatheringType gatheringType;
    private GatheringStatus status;
    private String image;
    private String description;
    private String address;
    private List<String> tags;
    private GatheringLocation location;
    private int capacity;
    private int participantCount;
    private Boolean isPeriodic;
    private LocalDateTime nextGatheringAt;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public void validateJoin() {
        if (!isRecruiting()) {
            throw new BusinessException(ExceptionCode.NOT_RECRUITING_GATHERING);
        }

        if (isFull()) {
            throw new BusinessException(ExceptionCode.FULL_PARTICIPANT_GATHERING);
        }
    }

    public void cancel() {
        this.status = GatheringStatus.DELETED;
    }

    public Boolean isManager(Long userId) {
        return this.managerId.equals(userId);
    }

    public void modify(ModifyGathering modifyGathering) {
        this.subCategory = modifyGathering.getSubCategory().name();
        this.name = modifyGathering.getName();
        this.status = modifyGathering.getStatus();
        this.image = modifyGathering.getImage();
        this.description = modifyGathering.getDescription();
        this.address = modifyGathering.getAddress();
        this.tags = modifyGathering.getTags();
        this.location = modifyGathering.getLocation();
        this.capacity = modifyGathering.getCapacity();
        this.isPeriodic = modifyGathering.getIsPeriodic();
        this.nextGatheringAt = modifyGathering.getNextGatheringAt();
        this.lastModifiedAt = LocalDateTime.now();
    }

    public Boolean isFull() {
        return capacity == participantCount;
    }

    public Boolean isRecruiting() {
        return status == GatheringStatus.RECRUITING;
    }

    public void increaseParticipantCount() {
        participantCount++;
    }

    public void decreaseParticipantCount() {
        participantCount--;
    }
}
