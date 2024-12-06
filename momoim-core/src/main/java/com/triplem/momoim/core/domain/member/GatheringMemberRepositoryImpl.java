package com.triplem.momoim.core.domain.member;

import static com.triplem.momoim.core.domain.member.QGatheringMemberEntity.gatheringMemberEntity;
import static com.triplem.momoim.core.domain.user.QUserEntity.userEntity;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.triplem.momoim.core.domain.member.dto.GatheringMemberDetail;
import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GatheringMemberRepositoryImpl implements GatheringMemberRepository {
    private final GatheringMemberJpaRepository gatheringMemberJpaRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public GatheringMember save(GatheringMember gatheringMember) {
        return gatheringMemberJpaRepository.save(GatheringMemberEntity.from(gatheringMember)).toModel();
    }

    @Override
    public Boolean isGatheringMember(Long userId, Long gatheringId) {
        return gatheringMemberJpaRepository.existsByUserIdAndGatheringId(userId, gatheringId);
    }

    @Override
    public void deleteByUserIdAndGatheringId(Long userId, Long gatheringId) {
        gatheringMemberJpaRepository.deleteByUserIdAndGatheringId(userId, gatheringId);
    }

    @Override
    public List<GatheringMemberDetail> getGatheringMembers(Long gatheringId) {
        return jpaQueryFactory.select(
                Projections.constructor(
                    GatheringMemberDetail.class,
                    gatheringMemberEntity.id,
                    gatheringMemberEntity.userId,
                    userEntity.email,
                    userEntity.name,
                    userEntity.profileImage,
                    gatheringMemberEntity.joinedAt
                )
            )
            .from(gatheringMemberEntity)
            .where(gatheringMemberEntity.gatheringId.eq(gatheringId))
            .leftJoin(userEntity).on(userEntity.id.eq(gatheringMemberEntity.userId))
            .fetch();
    }

    @Override
    public GatheringMember findById(Long id) {
        return gatheringMemberJpaRepository.findById(id)
            .orElseThrow(() -> new BusinessException(ExceptionCode.NOT_FOUND_GATHERING_MEMBER))
            .toModel();
    }

    @Override
    public void deleteById(Long id) {
        gatheringMemberJpaRepository.deleteById(id);
    }
}
