package ru.clevertec.crudspringtestproject.service;

import ru.clevertec.crudspringtestproject.entity.User;

public interface IAdminUserService extends IUserService {
    User createAdminUser(User user);
}
