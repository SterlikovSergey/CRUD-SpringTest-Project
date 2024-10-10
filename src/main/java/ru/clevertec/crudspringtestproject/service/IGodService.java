package ru.clevertec.crudspringtestproject.service;

import ru.clevertec.crudspringtestproject.entity.User;

public interface IGodService extends IAdminUserService{
    User createGodUser(User user);
}
