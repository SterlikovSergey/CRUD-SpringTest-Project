package ru.clevertec.crudspringtestproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.crudspringtestproject.dto.UserDto;
import ru.clevertec.crudspringtestproject.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);

    @Mapping(target = "id", ignore = true)
    User toUser(UserDto userDto);
}
