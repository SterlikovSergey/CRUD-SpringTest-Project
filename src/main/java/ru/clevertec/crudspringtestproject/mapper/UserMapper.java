package ru.clevertec.crudspringtestproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.crudspringtestproject.dto.UserDto;
import ru.clevertec.crudspringtestproject.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "god", expression = "java(false)")
    @Mapping(target = "admin", expression = "java(false)")
    User toEntity(UserDto userDto);
}