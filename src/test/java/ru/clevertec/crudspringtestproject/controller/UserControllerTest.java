package ru.clevertec.crudspringtestproject.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.crudspringtestproject.dto.UserDto;
import ru.clevertec.crudspringtestproject.entity.User;
import ru.clevertec.crudspringtestproject.mapper.UserMapper;
import ru.clevertec.crudspringtestproject.service.impl.UserService;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    void testGetUser(Long userId) throws Exception {
        when(userService.getUserById(userId)).thenReturn(new User());

        mockMvc.perform(get("/api/user/" + userId))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setName("John Doe");
        userDto.setPassword("password123");

        User user = new User();
        user.id(1L);
        user.name("John Doe");
        user.password("password123");

        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(userService.createUser(user)).thenReturn(user);

        mockMvc.perform(post("/api/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\", \"password\":\"password123\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateUser() throws Exception {
        Long userId = 1L;
        UserDto userDto = new UserDto();
        userDto.setName("John Doe");
        userDto.setPassword("password123");

        User user = new User();
        user.id(userId);
        user.name("John Doe");
        user.password("password123");

        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(userService.updateUser(userId, user)).thenReturn(user);

        mockMvc.perform(put("/api/user/update/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\", \"password\":\"password123\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteUser() throws Exception {
        Long userId = 1L;

        mockMvc.perform(delete("/api/user/" + userId))
                .andExpect(status().isOk());
    }
}