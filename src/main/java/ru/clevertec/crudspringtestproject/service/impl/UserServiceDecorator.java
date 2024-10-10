package ru.clevertec.crudspringtestproject.service.impl;

import ru.clevertec.crudspringtestproject.entity.User;
import ru.clevertec.crudspringtestproject.service.IUserService;


public class UserServiceDecorator implements IUserService {
    public final IUserService decoratedUserService;

    public UserServiceDecorator(IUserService decoratedUserService) {
        this.decoratedUserService = decoratedUserService;
    }

    @Override
    public User createUser(User user) {
        return decoratedUserService.createUser(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        return decoratedUserService.updateUser(id, user);
    }

    @Override
    public void deleteUser(Long id) {
        decoratedUserService.deleteUser(id);
    }

    @Override
    public User getUserById(Long id) {
        return decoratedUserService.getUserById(id);
    }
}
