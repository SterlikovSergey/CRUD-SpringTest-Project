package ru.clevertec.crudspringtestproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.crudspringtestproject.dto.UserDto;
import ru.clevertec.crudspringtestproject.entity.User;
import ru.clevertec.crudspringtestproject.mapper.UserMapper;
import ru.clevertec.crudspringtestproject.service.IGodService;

@RestController
@RequestMapping("/api/god")
public class GodController {

    private final IGodService iGodService;
    private final UserMapper userMapper;

    @Autowired
    public GodController(UserMapper userMapper, @Qualifier("godUserService") IGodService iGodService) {
        this.userMapper = userMapper;
        this.iGodService = iGodService;
    }

    @PostMapping("/create")
    public ResponseEntity<User> createGodUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(iGodService.createGodUser(userMapper.toEntity(userDto)), HttpStatus.CREATED);
    }

    @PostMapping("/create_admin")
    public ResponseEntity<User> createAdminUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(iGodService.createAdminUser(userMapper.toEntity(userDto)), HttpStatus.CREATED);
    }

    @PostMapping("/create_user")
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(iGodService.createUser(userMapper.toEntity(userDto)), HttpStatus.CREATED);
    }
}
