package ru.clevertec.crudspringtestproject.service.impl;

import org.junit.jupiter.api.BeforeEach;
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
import ru.clevertec.crudspringtestproject.entity.User;
import ru.clevertec.crudspringtestproject.repository.UserRepository;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
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

    static Stream<Arguments> userProvider() {
        return Stream.of(
                Arguments.of(1L, "John Doe", "password123"),
                Arguments.of(2L, "Jane Smith", "password456"),
                Arguments.of(3L, "Bob Johnson", "password789")
        );
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("userProvider")
    void test_create_user_success(Long id, String name, String password) {

        User user = new User();
        user.id(id);
        user.name(name);
        user.password(password);

        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user);

        verify(userRepository).save(userCaptor.capture());
        User capturedUser = userCaptor.getValue();

        assertThat(capturedUser.name(), is(name));
        assertThat(capturedUser.password(), is(password));

        assertNotNull(createdUser);
        assertEquals(id, createdUser.id());
        assertEquals(name, createdUser.name());
        assertEquals(password, createdUser.password());

        verify(userRepository, times(1)).save(user);
    }

    @ParameterizedTest
    @MethodSource("userProvider")
    void test_update_existing_user_successfully(Long id, String name, String password) {

        User existingUser = new User();
        existingUser.id(Long.valueOf(id));
        existingUser.name(name);
        existingUser.password(password);

        when(userRepository.findById(id)).thenReturn(java.util.Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        User updatedUser = userService.updateUser(Long.valueOf(id), existingUser);

        assertNotNull(updatedUser);
        assertEquals(id, updatedUser.id());
        assertEquals(name, updatedUser.name());
        assertEquals(password, updatedUser.password());

        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(existingUser);
    }

    @ParameterizedTest
    @MethodSource("userProvider")
    void test_delete_existing_user_successfully(Long id, String name, String password) {
        User existingUser = new User();
        existingUser.id(id);
        existingUser.name(name);
        existingUser.password(password);

        when(userRepository.findById(id)).thenReturn(java.util.Optional.of(existingUser));

        userService.deleteUser(id);

        verify(userRepository, times(1)).deleteById(id);
    }

    @ParameterizedTest
    @MethodSource("userProvider")
    void test_get_existing_user_successfully(Long id, String name, String password) {
        User existingUser = new User();
        existingUser.id(id);
        existingUser.name(name);
        existingUser.password(password);

        when(userRepository.findById(id)).thenReturn(java.util.Optional.of(existingUser));

        User user = userService.getUserById(id);

        assertNotNull(user);
        assertEquals(id, user.id());
        assertEquals(name, user.name());
        assertEquals(password, user.password());
    }
}