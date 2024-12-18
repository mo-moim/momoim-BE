package com.triplem.momoim.resolver;

import com.triplem.momoim.core.common.PaginationInformation;
import com.triplem.momoim.core.common.SortOrder;
import com.triplem.momoim.core.domain.gathering.dto.GatheringSearchOption;
import com.triplem.momoim.core.domain.gathering.enums.GatheringCategory;
import com.triplem.momoim.core.domain.gathering.enums.GatheringLocation;
import com.triplem.momoim.core.domain.gathering.enums.GatheringSortType;
import com.triplem.momoim.core.domain.gathering.enums.GatheringSubCategory;
import com.triplem.momoim.exception.BusinessException;
import com.triplem.momoim.exception.ExceptionCode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class GatheringSearchOptionResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(GatheringSearchOption.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory) throws Exception {
        return GatheringSearchOption.of(
            getGatheringIds(webRequest),
            getGatheringCategory(webRequest),
            getGatheringSubCategory(webRequest),
            getGatheringLocation(webRequest),
            getGatheringDate(webRequest),
            getPaginationInformation(webRequest),
            getGatheringSortType(webRequest),
            getSortOrder(webRequest)
        );
    }

    private List<Long> getGatheringIds(NativeWebRequest webRequest) {
        String input = webRequest.getParameter("gatheringIds");

        if (input == null) {
            return new ArrayList<>();
        }

        String[] ids = input.split(",");

        return Arrays.stream(ids)
            .map(Long::parseLong)
            .collect(Collectors.toList());
    }

    private List<GatheringCategory> getGatheringCategory(NativeWebRequest webRequest) {
        String[] input = webRequest.getParameterValues("category");
        List<GatheringCategory> categories = new ArrayList<>();

        if (input == null) {
            return categories;
        }

        for (String category : input) {
            categories.add(GatheringCategory.valueOf(category));
        }

        return categories;
    }

    private List<GatheringSubCategory> getGatheringSubCategory(NativeWebRequest webRequest) {
        String[] input = webRequest.getParameterValues("subCategory");
        List<GatheringSubCategory> subCategories = new ArrayList<>();

        if (input == null) {
            return subCategories;
        }

        for (String subCategory : input) {
            System.out.println(subCategory);
            subCategories.add(GatheringSubCategory.valueOf(subCategory));
        }

        return subCategories;
    }

    private GatheringLocation getGatheringLocation(NativeWebRequest webRequest) {
        String input = webRequest.getParameter("location");

        if (input == null) {
            return null;
        }

        return GatheringLocation.valueOf(input);
    }

    private LocalDate getGatheringDate(NativeWebRequest webRequest) {
        String input = webRequest.getParameter("gatheringDate");

        if (input == null) {
            return null;
        }

        return LocalDate.parse(input);
    }

    private PaginationInformation getPaginationInformation(NativeWebRequest webRequest) {
        String offsetInput = webRequest.getParameter("offset");
        String limitInput = webRequest.getParameter("limit");

        if (offsetInput == null || limitInput == null) {
            throw new BusinessException(ExceptionCode.BAD_REQUEST);
        }

        return new PaginationInformation(Integer.parseInt(offsetInput), Integer.parseInt(limitInput));
    }

    private GatheringSortType getGatheringSortType(NativeWebRequest webRequest) {
        String input = webRequest.getParameter("sortType");

        if (input == null) {
            return GatheringSortType.UPDATE_AT;
        }

        return GatheringSortType.valueOf(input);
    }

    private SortOrder getSortOrder(NativeWebRequest webRequest) {
        String input = webRequest.getParameter("sortOrder");

        if (input == null) {
            return SortOrder.DESC;
        }

        return SortOrder.valueOf(input);
    }
}
