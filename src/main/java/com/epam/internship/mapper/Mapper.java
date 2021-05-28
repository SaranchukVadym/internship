package com.epam.internship.mapper;

public interface Mapper {
    <T, V> T convertTo(V v, Class destination);
}
