package ru.clevertec.crudspringtestproject.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.crudspringtestproject.entity.User;
import ru.clevertec.crudspringtestproject.exception.UserNotFoundException;
import ru.clevertec.crudspringtestproject.repository.UserRepository;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @ParameterizedTest
    @MethodSource("provideUserDataForCreateTest")
    @DisplayName("Test parameterized user creation")
    void test_create_user_with_parameters(User inputUser, Long expectedId, String expectedName) {
        when(userRepository.save(any(User.class))).thenReturn(inputUser);

        User createdUser = userService.createUser(inputUser);

        assertNotNull(createdUser);
        assertEquals(expectedId, createdUser.getId());
        assertEquals(expectedName, createdUser.getName());
    }

    static Stream<Arguments> provideUserDataForCreateTest() {
        return Stream.of(
                Arguments.of(new User(1L, "John Doe", "password123"), 1L, "John Doe"),
                Arguments.of(new User(2L, "Jane Doe", "password456"), 2L, "Jane Doe")
        );
    }

    @ParameterizedTest
    @MethodSource("provideUserDataForUpdateTest")
    @DisplayName("Test parameterized user update")
    void test_update_user_with_parameters(Long userId, User existingUser, User updatedUser, String expectedName, String expectedPassword) {
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(userId, updatedUser);

        assertEquals(expectedName, result.getName());
        assertEquals(expectedPassword, result.getPassword());
        verify(userRepository).findById(userId);
        verify(userRepository).save(updatedUser);
    }

    static Stream<Arguments> provideUserDataForUpdateTest() {
        return Stream.of(
                Arguments.of(1L, new User(1L, "John Doe", "password123"), new User(1L, "John Doe Updated", "newpassword"), "John Doe Updated", "newpassword"),
                Arguments.of(2L, new User(2L, "Jane Doe", "password456"), new User(2L, "Jane Doe Updated", "newpassword"), "Jane Doe Updated", "newpassword")
        );
    }

    @ParameterizedTest
    @MethodSource("provideUserIdForDeleteTest")
    @DisplayName("Test parameterized user deletion")
    void test_delete_user_with_parameters(Long userId) {
        doNothing().when(userRepository).deleteById(userId);

        userService.deleteUser(userId);

        verify(userRepository).deleteById(userId);
    }

    static Stream<Arguments> provideUserIdForDeleteTest() {
        return Stream.of(
                Arguments.of(1L),
                Arguments.of(2L),
                Arguments.of(3L)
        );
    }

    @ParameterizedTest
    @MethodSource("provideUserIdForGetTest")
    @DisplayName("Test parameterized get user by id")
    void test_get_user_by_id_with_parameters(Long userId, User expectedUser) {
        // Симулируем возвращение Optional.empty() для отсутствующего пользователя
        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(expectedUser));

        if (expectedUser != null) {
            // Если пользователь найден, проверяем данные
            User result = userService.getUserById(userId);

            assertEquals(userId, result.getId());
            assertEquals(expectedUser.getName(), result.getName());
            assertEquals(expectedUser.getPassword(), result.getPassword());
        } else {
            // Если пользователя нет, проверяем, что выбрасывается исключение
            assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId));
        }
    }

    static Stream<Arguments> provideUserIdForGetTest() {
        return Stream.of(
                Arguments.of(1L, new User(1L, "John Doe", "password123")),
                Arguments.of(2L, new User(2L, "Jane Doe", "password456")),
                Arguments.of(3L, null) // Симулируем отсутствие пользователя для теста исключения
        );
    }
}
