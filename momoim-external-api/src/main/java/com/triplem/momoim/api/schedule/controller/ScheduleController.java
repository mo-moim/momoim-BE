package com.triplem.momoim.api.schedule.controller;

import com.triplem.momoim.api.common.ApiResponse;
import com.triplem.momoim.api.schedule.service.ScheduleService;
import com.triplem.momoim.auth.utils.SecurityUtil;
import com.triplem.momoim.core.domain.schedule.dto.GatheringSchedule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청 성공")
    })
    @Operation(operationId = "연도 별 모임 일정 조회", summary = "연도 별 모임 일정 조회", tags = {"schedule"}, description = "연도 별 모임 일정 조회")
    public ApiResponse<List<GatheringSchedule>> getSchedules(
        @Parameter(description = "조회 할 연도") @RequestParam int year
    ) {
        Long userId = SecurityUtil.getMemberIdByPrincipal();
        List<GatheringSchedule> schedules = scheduleService.getMySchedules(userId, year);
        return ApiResponse.success(schedules);
    }
}
