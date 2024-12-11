package com.triplem.momoim.core.domain.wishlist.infrastructure;

import static com.triplem.momoim.core.domain.wishlist.infrastructure.QWishlistEntity.wishlistEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.triplem.momoim.core.common.PaginationInformation;
import com.triplem.momoim.core.domain.wishlist.model.Wishlist;
import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WishlistRepositoryImpl implements WishlistRepository {
    private final WishlistJpaRepository wishlistJpaRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Wishlist save(Wishlist wishlist) {
        return wishlistJpaRepository.save(WishlistEntity.from(wishlist)).toModel();
    }

    @Override
    public List<Wishlist> getMyWishlist(Long userId, PaginationInformation paginationInformation) {
        return jpaQueryFactory.select(wishlistEntity)
            .from(wishlistEntity)
            .where(wishlistEntity.userId.eq(userId))
            .offset(paginationInformation.getOffset())
            .limit(paginationInformation.getLimit())
            .orderBy(wishlistEntity.id.desc())
            .fetch()
            .stream()
            .map(WishlistEntity::toModel)
            .collect(Collectors.toList());
    }

    @Override
    public Boolean isAlreadyAppended(Long userId, Long gatheringId) {
        return wishlistJpaRepository.existsByUserIdAndGatheringId(userId, gatheringId);
    }

    @Override
    public Wishlist findByUserIdAndGatheringId(Long userId, Long gatheringId) {
        return wishlistJpaRepository.findByUserIdAndGatheringId(userId, gatheringId)
            .orElseThrow(() -> new BusinessException(ExceptionCode.NOT_FOUND_WISHLIST))
            .toModel();
    }

    @Override
    public void deleteById(Long id) {
        wishlistJpaRepository.deleteById(id);
    }
}
