package com.kams.UserService.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kams.UserService.user.entity.User;
import com.kams.UserService.user.repository.UserRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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
        userRepository.deleteAll();
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
    public void getUsersTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.page.totalElements").value("2"));
    }

    @Test
    public void createUserTest() throws Exception {
        String newUserName = "User3";
        int newAge = 35;
        User user = User.builder()
                .userName(newUserName)
                .age(newAge)
                .build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        JSONObject object = new JSONObject(content);
        long idOfNewUser = Long.parseLong(object.get("Id").toString());
        User newUser = userRepository.findById(idOfNewUser).get();
        Assertions.assertEquals(newUser.getUserName(), newUserName);
        Assertions.assertEquals(newUser.getAge(), newAge);
    }

    @Test
    public void updateUserWithValidIdTest() throws Exception {
        Optional<User> optionalUser = userRepository.findByUserName("User1");
        long userId = optionalUser.get().getId();

        String newUserName = "User3";
        int newAge = 35;
        User updateUser = User.builder()
                .id(userId)
                .userName(newUserName)
                .age(newAge)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUser)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        User updatedUser = userRepository.findById(userId).get();
        Assertions.assertEquals(updatedUser, updateUser);
    }

    @Test
    public void updateUserWithInvalidIdTest() throws Exception {
        long invalidUserId = 100L;

        User updateUser = User.builder()
                .id(invalidUserId)
                .userName("User3")
                .age(35)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", invalidUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUser)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
