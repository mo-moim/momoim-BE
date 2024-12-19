package com.triplem.momoim.api.gathering;

import static org.assertj.core.api.Assertions.assertThat;

import com.triplem.momoim.api.gathering.service.GatheringService;
import com.triplem.momoim.core.common.PaginationInformation;
import com.triplem.momoim.core.common.SortOrder;
import com.triplem.momoim.core.domain.gathering.dto.GatheringPreview;
import com.triplem.momoim.core.domain.gathering.dto.GatheringSearchOption;
import com.triplem.momoim.core.domain.gathering.enums.GatheringSortType;
import com.triplem.momoim.core.domain.gathering.infrastructure.GatheringRepository;
import java.util.ArrayList;
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
        Long userId = 1L;

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
            .categories(new ArrayList<>())
            .subCategories(List.of("COOK"))
            .paginationInformation(new PaginationInformation(0, 10))
            .sortType(GatheringSortType.UPDATE_AT)
            .sortOrder(SortOrder.ASC)
            .build();

        //when
        List<GatheringPreview> gatheringPreviews = gatheringService.searchGatherings(userId, searchOption);

        //then
        assertThat(gatheringPreviews).hasSize(2)
            .extracting("name")
            .contains("gathering1", "gathering2");
    }
}