package ru.clevertec.crudspringtestproject.service.impl;

import ru.clevertec.crudspringtestproject.entity.User;
import ru.clevertec.crudspringtestproject.service.IUserService;


public class UserServiceDecorator implements IUserService {
    public final IUserService iUserService;

    public UserServiceDecorator(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @Override
    public User createUser(User user) {
        return iUserService.createUser(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        return iUserService.updateUser(id, user);
    }

    @Override
    public void deleteUser(Long id) {
        iUserService.deleteUser(id);
    }

    @Override
    public User getUserById(Long id) {
        return iUserService.getUserById(id);
    }
}
