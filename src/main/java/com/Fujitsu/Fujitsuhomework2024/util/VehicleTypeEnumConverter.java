package com.Fujitsu.Fujitsuhomework2024.util;

import com.Fujitsu.Fujitsuhomework2024.enums.VehicleType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class VehicleTypeEnumConverter implements Converter<String, VehicleType> {
    @Override
    public VehicleType convert(String source) {
        return VehicleType.valueOf(source.toUpperCase());
    }
}

