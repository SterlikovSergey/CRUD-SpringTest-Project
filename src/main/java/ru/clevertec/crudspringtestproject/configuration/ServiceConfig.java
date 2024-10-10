package ru.clevertec.crudspringtestproject.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.crudspringtestproject.service.IUserService;
import ru.clevertec.crudspringtestproject.service.impl.UserServiceDecorator;

@Configuration
public class ServiceConfig {

    @Bean
    public UserServiceDecorator userServiceDecorator(@Qualifier("userService") IUserService userService) {
        return new UserServiceDecorator(userService);
    }
}