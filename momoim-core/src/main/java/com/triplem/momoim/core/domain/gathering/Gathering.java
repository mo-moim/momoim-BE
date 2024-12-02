package com.triplem.momoim.core.domain.gathering;

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
    private List<String> tags;
    private GatheringLocation location;
    private int capacity;
    private int participantCount;
    private int viewCount;
    private Boolean isPeriodic;
    private LocalDateTime nextGatheringAt;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public void validateJoin() {
        if (!isRecruiting()) {
            throw new RuntimeException("모집 중인 모임이 아닙니다.");
        }

        if (isFull()) {
            throw new RuntimeException("인원이 다 찬 모임입니다.");
        }
    }

    public void cancel(Long requesterId) {
        if (!managerId.equals(requesterId)) {
            throw new RuntimeException("모임을 취소할 권한이 없습니다.");
        }

        this.status = GatheringStatus.DELETED;
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
