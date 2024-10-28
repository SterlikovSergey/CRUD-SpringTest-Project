package ru.clevertec.crudspringtestproject.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.clevertec.crudspringtestproject.entity.User;
import ru.clevertec.crudspringtestproject.service.IAdminUserService;
import ru.clevertec.crudspringtestproject.service.IGodService;
import ru.clevertec.crudspringtestproject.service.IUserService;


@Service
@Qualifier("godUserService")
public class GodUserService extends UserServiceDecorator implements IGodService {

    private final IAdminUserService adminUserService;

    public GodUserService(@Qualifier("userService") IUserService iUserService,
                          @Qualifier("adminUserService") IAdminUserService adminUserService) {
        super(iUserService);
        this.adminUserService = adminUserService;
    }
    @Override
    public User createGodUser(User user) {
        user.god(true);
        return adminUserService.createAdminUser(user);
    }

    @Override
    public User createAdminUser(User user) {
        user.admin(true);
        return iUserService.createUser(user);
    }
}
