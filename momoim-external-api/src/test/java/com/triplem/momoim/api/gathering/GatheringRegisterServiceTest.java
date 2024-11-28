package com.triplem.momoim.api.gathering;

import static org.assertj.core.api.Assertions.assertThat;

import com.triplem.momoim.core.domain.gathering.Gathering;
import com.triplem.momoim.core.domain.gathering.RecruitStatus;
import com.triplem.momoim.core.domain.member.GatheringMemberRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("local")
@Transactional
class GatheringRegisterServiceTest {
    @Autowired
    private GatheringRegisterService gatheringRegisterService;

    @Autowired
    private GatheringMemberRepository gatheringMemberRepository;

    @Test
    @DisplayName("모임을 등록할 수 있다.")
    void gatheringRegister() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Gathering gathering = Gathering.builder()
            .managerId(1L)
            .category("FOOD")
            .subCategory("COOK")
            .recruitStatus(RecruitStatus.RECRUITING)
            .name("요리 모임")
            .image("https://placehold.co/600x400")
            .capacity(10)
            .participantCount(0)
            .startAt(LocalDateTime.of(2024, 1, 1, 0, 0))
            .endAt(LocalDateTime.of(2024, 12, 31, 23, 59))
            .createdAt(now)
            .build();

        //when
        Gathering savedGathering = gatheringRegisterService.register(gathering);

        //then
        Boolean isSuccessRegisteredMaster = gatheringMemberRepository.isExistsByUserIdAndGatheringId(
            gathering.getManagerId(),
            savedGathering.getId());

        assertThat(savedGathering)
            .isNotNull()
            .extracting(
                "managerId", "category", "subCategory", "recruitStatus",
                "name", "image", "capacity", "participantCount",
                "startAt", "endAt", "createdAt")
            .containsExactly(
                gathering.getManagerId(), gathering.getCategory(), gathering.getSubCategory(), gathering.getRecruitStatus(),
                gathering.getName(), gathering.getImage(), gathering.getCapacity(), gathering.getParticipantCount(),
                gathering.getStartAt(), gathering.getEndAt(), gathering.getCreatedAt());

        assertThat(isSuccessRegisteredMaster).isTrue();
    }
}