package com.triplem.momoim.core.domain.schedule.infrastructure;

import com.triplem.momoim.core.domain.schedule.dto.GatheringSchedule;
import java.util.List;

public interface GatheringScheduleRepository {
    List<GatheringSchedule> getSchedules(Long userId, int year);
}
