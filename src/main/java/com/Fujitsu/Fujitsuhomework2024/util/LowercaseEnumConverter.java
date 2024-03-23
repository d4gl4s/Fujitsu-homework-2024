package com.Fujitsu.Fujitsuhomework2024.util;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;

@RequiredArgsConstructor
public class LowercaseEnumConverter<T extends Enum<T>> implements Converter<String, T> {
    private final Class<T> enumType;
    @Override
    public T convert(String source) {
        return T.valueOf(enumType, source.toUpperCase());
    }
}

