package ru.clevertec.crudspringtestproject.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.clevertec.crudspringtestproject.entity.User;
import ru.clevertec.crudspringtestproject.service.IAdminUserService;
import ru.clevertec.crudspringtestproject.service.IUserService;

@Service
@Qualifier("adminUserService")
public class AdminUserService extends UserServiceDecorator implements IAdminUserService {

    public AdminUserService(@Qualifier("userService") IUserService iUserService) {
        super(iUserService);
    }

    @Override
    public User createAdminUser(User user) {
        user.admin(true);
        return decoratedUserService.createUser(user);
    }
}
