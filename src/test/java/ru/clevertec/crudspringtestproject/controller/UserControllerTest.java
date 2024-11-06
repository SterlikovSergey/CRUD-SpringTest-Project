package ru.clevertec.crudspringtestproject.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.crudspringtestproject.dto.UserDto;
import ru.clevertec.crudspringtestproject.entity.User;
import ru.clevertec.crudspringtestproject.mapper.UserMapper;
import ru.clevertec.crudspringtestproject.service.impl.UserService;

import java.util.stream.Stream;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @Test
    void test_get_user_success() throws Exception {
        Long validId = 1L;
        User expectedUser = new User(validId, "John Doe", "password123");

        // Мокаем вызов сервиса
        when(userService.getUserById(validId)).thenReturn(expectedUser);

        // Выполняем GET-запрос и проверяем результат
        mockMvc.perform(get("/api/user/" + validId))
                .andExpect(status().isOk())  // Ожидаем, что ответ будет успешным (200 OK)
                .andExpect(jsonPath("$.id").value(validId))  // Проверяем, что id соответствует ожидаемому
                .andExpect(jsonPath("$.name").value("John Doe"))  // Проверяем, что имя соответствует ожидаемому
                .andExpect(jsonPath("$.password").value("password123"));  // Проверяем, что пароль соответствует ожидаемому
    }

    @ParameterizedTest
    @MethodSource("provideUserDataForCreateTest")
    @DisplayName("Test parameterized create user")
    void test_create_user_success(UserDto userDto, User expectedUser) throws Exception {

        // Мокаем поведение маппера и сервиса
        when(userMapper.toUser(userDto)).thenReturn(expectedUser);
        when(userService.createUser(expectedUser)).thenReturn(expectedUser);

        // Выполняем POST-запрос на создание пользователя и проверяем результат
        mockMvc.perform(post("/api/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"" + userDto.getName() + "\", \"password\":\"" + userDto.getPassword() + "\"}"))
                .andExpect(status().isOk())  // Ожидаем, что ответ будет успешным (200 OK)
                .andExpect(jsonPath("$.id").value(expectedUser.getId()))  // Проверяем, что id соответствует ожидаемому
                .andExpect(jsonPath("$.name").value(expectedUser.getName()))  // Проверяем, что имя соответствует ожидаемому
                .andExpect(jsonPath("$.password").value(expectedUser.getPassword()));  // Проверяем, что пароль соответствует ожидаемому
    }

    // Параметризированные данные для теста создания пользователя
    static Stream<Arguments> provideUserDataForCreateTest() {
        return Stream.of(
                Arguments.of(UserDto.builder().name("John Doe").password("securePassword").build(), new User(1L, "John Doe", "securePassword")),
                Arguments.of(UserDto.builder().name("Jane Doe").password("secure123").build(), new User(2L, "Jane Doe", "secure123"))
        );
    }

    @ParameterizedTest
    @MethodSource("provideUserDataForUpdateTest")
    @DisplayName("Test parameterized update user")
    void test_update_user_success(Long userId, UserDto userDto, User expectedUser) throws Exception {

        // Мокаем поведение маппера и сервиса
        when(userMapper.toUser(userDto)).thenReturn(expectedUser);
        when(userService.updateUser(userId, expectedUser)).thenReturn(expectedUser);

        // Выполняем PUT-запрос на обновление пользователя и проверяем результат
        mockMvc.perform(put("/api/user/update/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"" + userDto.getName() + "\", \"password\":\"" + userDto.getPassword() + "\"}"))
                .andExpect(status().isOk())  // Ожидаем, что ответ будет успешным (200 OK)
                .andExpect(jsonPath("$.id").value(userId))  // Проверяем, что id соответствует ожидаемому
                .andExpect(jsonPath("$.name").value(userDto.getName()))  // Проверяем, что имя соответствует ожидаемому
                .andExpect(jsonPath("$.password").value(userDto.getPassword()));  // Проверяем, что пароль соответствует ожидаемому
    }

    // Параметризированные данные для теста обновления пользователя
    static Stream<Arguments> provideUserDataForUpdateTest() {
        return Stream.of(
                Arguments.of(1L, UserDto.builder().name("John Doe Updated").password("newpassword").build(), new User(1L, "John Doe Updated", "newpassword")),
                Arguments.of(2L, UserDto.builder().name("Jane Doe Updated").password("newpassword123").build(), new User(2L, "Jane Doe Updated", "newpassword123"))
        );
    }

    @Test
    void test_delete_user_success() throws Exception {
        Long userId = 1L;

        // Мокаем успешное удаление пользователя
        doNothing().when(userService).deleteUser(userId);

        // Выполняем DELETE-запрос на удаление пользователя и проверяем результат
        mockMvc.perform(delete("/api/user/{id}", userId))
                .andExpect(status().isOk());  // Ожидаем, что ответ будет успешным (200 OK)
    }
}
