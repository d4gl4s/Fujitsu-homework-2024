package com.Fujitsu.Fujitsuhomework2024.util;

import com.Fujitsu.Fujitsuhomework2024.enums.City;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CityEnumConverter implements Converter<String, City> {
    @Override
    public City convert(String source) {
        System.out.println("here");
        return City.valueOf(source.toUpperCase());
    }
}
