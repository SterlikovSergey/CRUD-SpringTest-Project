package ru.clevertec.crudspringtestproject.service;

import ru.clevertec.crudspringtestproject.entity.User;

public interface IUserService {
    User createUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    User getUserById(Long id);
}
