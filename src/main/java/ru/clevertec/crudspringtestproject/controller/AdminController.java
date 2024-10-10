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
import ru.clevertec.crudspringtestproject.service.IAdminUserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final IAdminUserService iAdminUserService;
    private final UserMapper userMapper;

    @Autowired
    public AdminController(UserMapper userMapper, @Qualifier("adminUserService") IAdminUserService iAdminUserService) {
        this.userMapper = userMapper;
        this.iAdminUserService = iAdminUserService;
    }

    @PostMapping("/create")
    public ResponseEntity<User> createAdminUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(iAdminUserService.createAdminUser(userMapper.toEntity(userDto)), HttpStatus.CREATED);
    }

    @PostMapping("/create_user")
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(iAdminUserService.createUser(userMapper.toEntity(userDto)), HttpStatus.CREATED);
    }
}