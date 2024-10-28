package ru.clevertec.crudspringtestproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.crudspringtestproject.configuration.ServiceConfig;
import ru.clevertec.crudspringtestproject.dto.UserDto;
import ru.clevertec.crudspringtestproject.entity.User;
import ru.clevertec.crudspringtestproject.mapper.UserMapper;
import ru.clevertec.crudspringtestproject.service.IUserService;
import ru.clevertec.crudspringtestproject.service.impl.UserServiceDecorator;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(ServiceConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService mockIUserService;

    @MockBean
    private UserMapper mockUserMapper;

    @MockBean
    private UserServiceDecorator userServiceDecorator;

    @Autowired
    private UserController userController;

    static Stream<Arguments> provideUserDtos() {
        return Stream.of(
                Arguments.of(new UserDto("John Doe", "password123"))
        );
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

/*
    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    void testGetUser(Long userId) throws Exception {
        User user = new User();
        user.id(userId);
        user.name("John Doe");
        user.password("password123");
        user.admin(false);
        user.god(false);

        when(mockIUserService.getUserById(userId)).thenReturn(user);

        mockMvc.perform(get("/api/user/" + userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.password").value("password123"))
                .andExpect(jsonPath("$.admin").value(false))
                .andExpect(jsonPath("$.god").value(false));
    }
*/
/*@Test
void create_user_returns_201_with_created_user() {
    // Arrange
    UserDto userDto = new UserDto("JohnDoe", "password123");
    User user = User.builder().name("JohnDoe").password("password123").admin(false).god(false).build();
    UserMapper userMapper = mock(UserMapper.class);
    IUserService iUserService = mock(IUserService.class);
    when(userMapper.toEntity(userDto)).thenReturn(user);
    when(iUserService.createUser(user)).thenReturn(user);
    UserController userController = new UserController(userMapper, new UserServiceDecorator(iUserService));

    // Act
    ResponseEntity<User> response = userController.createUser(userDto);

    // Assert
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(user, response.getBody());
}*/

   /* @ParameterizedTest
    @MethodSource("provideUserDtos")
    void create_user_with_valid_data_returns_http_201(UserDto userDto) throws Exception {
        User user = User.builder()
                .name(userDto.getName())
                .password(userDto.getPassword())
                .admin(false)
                .god(false)
                .build();

        when(mockUserMapper.toEntity(userDto)).thenReturn(user);
        when(mockIUserService.createUser(user)).thenReturn(user);

        mockMvc.perform(post("/api/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(userDto.getName()))
                .andExpect(jsonPath("$.password").value(userDto.getPassword()));
    }
*/
    public UserServiceDecorator getUserServiceDecorator() {
        return userServiceDecorator;
    }

    public void setUserServiceDecorator(UserServiceDecorator userServiceDecorator) {
        this.userServiceDecorator = userServiceDecorator;
    }

    public UserController getUserController() {
        return userController;
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }
}

/*    @Test
    void testUpdateUser() throws Exception {
        Long userId = 1L;
        UserDto userDto = new UserDto();
        userDto.setName("John Doe");
        userDto.setPassword("password123");

        User user = User.builder()
                .id(userId)
                .name(userDto.getName())
                .password(userDto.getPassword())
                .admin(false)
                .god(false)
                .build();

        when(mockUserMapper.toEntity(userDto)).thenReturn(user);
        when(mockIUserService.updateUser(userId, user)).thenReturn(user);

        mockMvc.perform(put("/api/user/update/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @ParameterizedTest
    @ValueSource(longs = {1L})
    void testDeleteUser(Long userId) throws Exception {

        mockMvc.perform(delete("/api/user/" + userId))
                .andExpect(status().isNoContent());
    }*/

