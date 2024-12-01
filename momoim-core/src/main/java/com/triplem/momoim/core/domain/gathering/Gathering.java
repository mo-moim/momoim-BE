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
    private String image;
    private String description;
    private List<String> tags;
    private GatheringLocation location;
    private int capacity;
    private int participantCount;
    private LocalDateTime nextGatheringAt;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private LocalDateTime createdAt;

    public void validateJoin() {
        if (isFull()) {
            throw new RuntimeException("인원이 다 찬 모임입니다.");
        }

        if (isEnd()) {
            throw new RuntimeException("종료 된 모임입니다.");
        }
    }

    public Boolean isFull() {
        return capacity == participantCount;
    }

    public Boolean isEnd() {
        return endAt.isBefore(LocalDateTime.now());
    }

    public void increaseParticipantCount() {
        participantCount++;
    }

    public void decreaseParticipantCount() {
        participantCount--;
    }
}
