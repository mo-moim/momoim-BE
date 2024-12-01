package com.triplem.momoim.api.gathering;

import static org.assertj.core.api.Assertions.assertThat;

import com.triplem.momoim.core.domain.gathering.Gathering;
import com.triplem.momoim.core.domain.gathering.GatheringLocation;
import com.triplem.momoim.core.domain.member.GatheringMemberRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("local")
@Transactional
class GatheringManagementServiceTest {
    @Autowired
    private GatheringManagementService gatheringManagementService;

    @Autowired
    private GatheringMemberRepository gatheringMemberRepository;

    @Test
    @DisplayName("모임을 등록할 수 있다.")
    void gatheringRegister() {
        //given
        Gathering gathering = Gathering.builder()
            .managerId(5L)
            .category("FOOD")
            .subCategory("COOK")
            .name("요리 모임")
            .image("https://placehold.co/600x400")
            .description("요리를 배우며 즐기는 모임입니다.")
            .tags(List.of("초보 환영", "재료비 지원"))
            .location(GatheringLocation.INCHEON)
            .capacity(10)
            .participantCount(5)
            .nextGatheringAt(LocalDateTime.of(2024, 12, 31, 0, 0))
            .startAt(LocalDateTime.of(2024, 1, 1, 0, 0))
            .endAt(LocalDateTime.of(2024, 12, 31, 23, 59))
            .createdAt(LocalDateTime.now())
            .build();

        //when
        Gathering savedGathering = gatheringManagementService.register(gathering);

        //then
        Boolean isSuccessRegisteredMaster = gatheringMemberRepository.isGatheringMember(
            gathering.getManagerId(),
            savedGathering.getId());

        assertThat(isSuccessRegisteredMaster).isTrue();
    }
}