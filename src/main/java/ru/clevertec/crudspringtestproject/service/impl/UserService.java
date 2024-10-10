package ru.clevertec.crudspringtestproject.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.clevertec.crudspringtestproject.entity.User;
import ru.clevertec.crudspringtestproject.exception.UserNotFoundException;
import ru.clevertec.crudspringtestproject.repository.UserRepository;
import ru.clevertec.crudspringtestproject.service.IUserService;


@Service
@Qualifier("userService")
@AllArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        User userDb = getUserById(id);
        BeanUtils.copyProperties(user, userDb, "id");
        return userRepository.save(userDb);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
