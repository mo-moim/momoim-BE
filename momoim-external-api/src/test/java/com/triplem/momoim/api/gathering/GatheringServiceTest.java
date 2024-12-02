package com.triplem.momoim.api.gathering;

import static org.assertj.core.api.Assertions.assertThat;

import com.triplem.momoim.api.gathering.dto.GatheringItem;
import com.triplem.momoim.api.gathering.service.GatheringService;
import com.triplem.momoim.core.common.PaginationInformation;
import com.triplem.momoim.core.domain.gathering.GatheringRepository;
import com.triplem.momoim.core.domain.gathering.GatheringSearchOption;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class GatheringServiceTest {
    @Autowired
    private GatheringService gatheringService;

    @Autowired
    private GatheringRepository gatheringRepository;

    @Test
    @DisplayName("검색 옵션을 통해 모임 목록을 조회할 수 있다.")
    void searchGatheringByGatheringSearchOption() {
        //given
        gatheringRepository.save(
            GatheringBuilder.builder()
                .name("gathering1")
                .subCategory("COOK")
                .build()
                .toGathering()
        );

        gatheringRepository.save(
            GatheringBuilder.builder()
                .name("gathering2")
                .subCategory("COOK")
                .build()
                .toGathering()
        );

        gatheringRepository.save(
            GatheringBuilder.builder()
                .name("gathering3")
                .subCategory("FISHING")
                .build()
                .toGathering()
        );

        GatheringSearchOption searchOption = GatheringSearchOption.builder()
            .subCategory("COOK")
            .paginationInformation(new PaginationInformation(0, 10))
            .build();

        //when
        List<GatheringItem> gatheringItems = gatheringService.searchGathering(searchOption);

        //then
        assertThat(gatheringItems).hasSize(2)
            .extracting("name")
            .contains("gathering1", "gathering2");
    }
}