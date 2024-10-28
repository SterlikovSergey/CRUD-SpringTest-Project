package ru.clevertec.crudspringtestproject.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.crudspringtestproject.dto.UserDto;
import ru.clevertec.crudspringtestproject.entity.User;
import ru.clevertec.crudspringtestproject.mapper.UserMapper;
import ru.clevertec.crudspringtestproject.repository.UserRepository;
import ru.clevertec.crudspringtestproject.service.IAdminUserService;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    static Stream<Arguments> userProvider() {
        return Stream.of(
                Arguments.of(new UserDto("John Doe", "password123"))
        );
    }

    static Stream<Arguments> userUpdateProvider() {
        return Stream.of(
                Arguments.of(1L, new UserDto("John Doe", "password123"))
        );
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("userProvider")
    void test_create_user_saves_valid_user(UserDto userDto) {
        User user = User.builder()
                .name(userDto.getName())
                .password(userDto.getPassword())
                .admin(false)
                .god(false)
                .build();

        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findById(user.id())).thenReturn(Optional.of(user));

        User savedUser = userService.createUser(user);

        assertNotNull(savedUser);
        assertEquals("John Doe", savedUser.name());
        assertEquals("password123", savedUser.password());
        assertFalse(savedUser.admin());
        assertFalse(savedUser.god());

        verify(userRepository, times(1)).save(user);
    }

    @ParameterizedTest
    @MethodSource("userUpdateProvider")
    void test_update_existing_user_successfully(Long id, UserDto userDto) {
        User user = User.builder()
                .id(id)
                .name("John Doe")
                .password("password123")
                .admin(false)
                .god(false)
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User updatedUser = userService.updateUser(id, userMapper.toEntity(userDto));

        assertNotNull(updatedUser);
        assertEquals(id, updatedUser.id());
        assertEquals(user.name(), updatedUser.name());
        assertEquals(user.password(), updatedUser.password());

        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void test_delete_existing_user_successfully() {
        Long id = 1L;
        User existingUser = new User();
        existingUser.id(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));

        userService.deleteUser(id);

        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    void test_get_existing_user_successfully() {
        Long id = 1L;
        User existingUser = new User();
        existingUser.id(id);
        existingUser.name("John Doe");
        existingUser.password("password123");

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));

        User user = userService.getUserById(id);

        assertNotNull(user);
        assertEquals(id, user.id());
        assertEquals("John Doe", user.name());
        assertEquals("password123", user.password());
    }
}

