package com.kams.UserService.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kams.UserService.user.dto.CreateUserDto;
import com.kams.UserService.user.dto.UpdateUserDto;
import com.kams.UserService.user.entity.User;
import com.kams.UserService.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        // Clear the in-memory H2 database before each test
        userRepository.deleteAll();
        // Insert test data into the in-memory H2 database
        User user1 = new User();
        user1.setUserName("User1");
        user1.setAge(25);
        userRepository.save(user1);

        User user2 = new User();
        user2.setUserName("User2");
        user2.setAge(30);
        userRepository.save(user2);
    }

    @Test
    public void getUsers_ReturnsListOfUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.users[0].userName").value("User1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.users[1].userName").value("User2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.users[0].age").value("25"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.users[1].age").value("30"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.last").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.first").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentPage").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value("2"));
    }

    @Test
    public void createUser_ReturnsCreatedUser() throws Exception {
        CreateUserDto createUserDto = new CreateUserDto("User3", 35);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("User3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(35));
    }

    @Test
    public void updateUser_WithValidId_ReturnsUpdatedUser() throws Exception {
        // Get the ID of the first user
        Optional<User> optionalUser = userRepository.findByUserName("User1");
        long userId = optionalUser.map(User::getId).orElse(0L);

        UpdateUserDto updateUserDto = new UpdateUserDto("UpdatedUser", 30);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("UpdatedUser"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(30));
    }

    @Test
    public void updateUser_WithInvalidId_ReturnsNotFound() throws Exception {
        long invalidUserId = 100L;

        UpdateUserDto updateUserDto = new UpdateUserDto("UpdatedUser", 30);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", invalidUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
