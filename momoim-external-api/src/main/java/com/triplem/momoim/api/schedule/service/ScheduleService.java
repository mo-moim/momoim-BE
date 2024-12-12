package com.triplem.momoim.api.schedule.service;

import com.triplem.momoim.api.schedule.implement.ScheduleReader;
import com.triplem.momoim.core.domain.schedule.dto.GatheringSchedule;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleReader scheduleReader;

    public List<GatheringSchedule> getMySchedules(Long userId, int year) {
        return scheduleReader.getMySchedule(userId, year);
    }
}
