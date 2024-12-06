package com.triplem.momoim.core.domain.member;

import static org.assertj.core.api.Assertions.assertThat;

import com.triplem.momoim.core.domain.gathering.Gathering;
import com.triplem.momoim.core.domain.gathering.GatheringBuilder;
import com.triplem.momoim.core.domain.gathering.infrastructure.GatheringRepository;
import com.triplem.momoim.core.domain.user.User;
import com.triplem.momoim.core.domain.user.UserBuilder;
import com.triplem.momoim.core.domain.user.UserRepository;
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
class GatheringMemberRepositoryTest {
    @Autowired
    private GatheringMemberRepository gatheringMemberRepository;

    @Autowired
    private GatheringRepository gatheringRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("모임 멤버를 저장할 수 있다.")
    void saveGatheringMember() {
        //given
        GatheringMember gatheringMember = GatheringMember.create(1L, 5L);

        //when
        GatheringMember savedGatheringMember = gatheringMemberRepository.save(gatheringMember);

        //then
        assertThat(savedGatheringMember)
            .extracting("userId", "gatheringId")
            .containsExactly(gatheringMember.getUserId(), gatheringMember.getGatheringId());
    }

    @Test
    @DisplayName("userId와 gatheringId를 통해 등록된 멤버인지 확인하고 등록 된 멤버라면 True를 반환한다.")
    void returnTrueWhenIsRegisteredMember() {
        //given
        Long gatheringId = 1L;
        Long userId = 1L;
        GatheringMember gatheringMember = GatheringMember.create(userId, gatheringId);
        gatheringMemberRepository.save(gatheringMember);

        //when
        Boolean result = gatheringMemberRepository.isGatheringMember(userId, gatheringId);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("userId와 gatheringId를 통해 등록된 멤버인지 확인하고 등록 되지 않은 멤버라면 False를 반환한다.")
    void returnFalseWhenIsNotRegisteredMember() {
        //given
        Long gatheringId = 1L;
        Long userId = 1L;

        //when
        Boolean result = gatheringMemberRepository.isGatheringMember(userId, gatheringId);

        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("모임에 참여 중인 멤버를 조회할 수 있다.")
    void getGatheringMembers() {
        //given
        Gathering gathering = gatheringRepository.save(GatheringBuilder.builder().build().toGathering());

        User user1 = userRepository.save(UserBuilder.builder().name("user1").build().toUser());
        User user2 = userRepository.save(UserBuilder.builder().name("user2").build().toUser());
        User user3 = userRepository.save(UserBuilder.builder().name("user3").build().toUser());
        User user4 = userRepository.save(UserBuilder.builder().name("user4").build().toUser());
        User user5 = userRepository.save(UserBuilder.builder().name("user5").build().toUser());

        gatheringMemberRepository.save(GatheringMember.create(user1.getId(), gathering.getId()));
        gatheringMemberRepository.save(GatheringMember.create(user2.getId(), gathering.getId()));
        gatheringMemberRepository.save(GatheringMember.create(user3.getId(), gathering.getId()));
        gatheringMemberRepository.save(GatheringMember.create(user4.getId(), gathering.getId()));
        gatheringMemberRepository.save(GatheringMember.create(user5.getId(), gathering.getId()));

        //when
        List<GatheringMemberDetail> members = gatheringMemberRepository.getGatheringMembers(gathering.getId());

        //then
        assertThat(members)
            .hasSize(5)
            .extracting("name")
            .containsExactly("user1", "user2", "user3", "user4", "user5");
    }
}