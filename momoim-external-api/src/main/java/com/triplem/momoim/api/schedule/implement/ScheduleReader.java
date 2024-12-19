package com.triplem.momoim.api.schedule.implement;

import com.triplem.momoim.core.domain.schedule.dto.GatheringSchedule;
import com.triplem.momoim.core.domain.schedule.infrastructure.GatheringScheduleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduleReader {
    private final GatheringScheduleRepository gatheringScheduleRepository;

    public List<GatheringSchedule> getMySchedule(Long userId, int year) {
        return gatheringScheduleRepository.getSchedules(userId, year);
    }
}
