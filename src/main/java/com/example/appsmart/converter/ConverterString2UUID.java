package com.example.appsmart.converter;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;

import java.util.UUID;

public class ConverterString2UUID implements Converter<String, UUID> {
    @Override
    public UUID convert(@NotNull String source) {
        return UUID.fromString(source);
    }
}
