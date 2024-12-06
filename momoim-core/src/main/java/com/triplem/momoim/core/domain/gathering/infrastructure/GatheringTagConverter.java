package com.triplem.momoim.core.domain.gathering.infrastructure;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Converter
public class GatheringTagConverter implements AttributeConverter<List<String>, String> {
    @Override
    public String convertToDatabaseColumn(List<String> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        for (String element : list) {
            sb.append(element).append(",");
        }

        String result = sb.toString();
        result = result.substring(0, result.length() - 1);

        return result;
    }

    @Override
    public List<String> convertToEntityAttribute(String listString) {
        List<String> list = new ArrayList<>();

        if (listString != null && !listString.isEmpty()) {
            String[] elements = listString.split(",");
            list.addAll(Arrays.asList(elements));
        }

        return list;
    }
}