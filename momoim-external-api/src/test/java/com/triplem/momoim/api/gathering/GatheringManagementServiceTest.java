package com.triplem.momoim.api.gathering;

import static org.assertj.core.api.Assertions.assertThat;

import com.triplem.momoim.api.gathering.service.GatheringManagementService;
import com.triplem.momoim.core.domain.gathering.Gathering;
import com.triplem.momoim.core.domain.gathering.GatheringRepository;
import com.triplem.momoim.core.domain.gathering.GatheringStatus;
import com.triplem.momoim.core.domain.member.GatheringMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class GatheringManagementServiceTest {
    @Autowired
    private GatheringManagementService gatheringManagementService;

    @Autowired
    private GatheringMemberRepository gatheringMemberRepository;

    @Autowired
    private GatheringRepository gatheringRepository;

    @Test
    @DisplayName("모임을 등록할 수 있다.")
    void gatheringRegister() {
        //given
        Gathering gathering = GatheringBuilder.builder().build().toGathering();

        //when
        Gathering savedGathering = gatheringManagementService.register(gathering);

        //then
        Boolean isSuccessRegisteredMaster = gatheringMemberRepository.isGatheringMember(
            gathering.getManagerId(),
            savedGathering.getId());

        assertThat(isSuccessRegisteredMaster).isTrue();
    }

    @Test
    @DisplayName("모임을 취소할 수 있다.")
    void cancelGathering() {
        //given
        Long requesterId = 1L;
        Gathering gathering = gatheringRepository.save(
            GatheringBuilder.builder()
                .managerId(requesterId)
                .build()
                .toGathering()
        );

        //when
        gatheringManagementService.cancel(requesterId, gathering.getId());

        //then
        Gathering canceledGathering = gatheringRepository.findById(gathering.getId());
        assertThat(canceledGathering.getStatus()).isEqualTo(GatheringStatus.DELETED);
    }
}