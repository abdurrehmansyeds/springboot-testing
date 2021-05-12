package com.abdur.testing.controller;

import com.abdur.testing.entity.UserInfo;
import com.abdur.testing.entity.dto.AddressView;
import com.abdur.testing.entity.dto.UserInfoView;
import com.abdur.testing.service.impl.UserInfoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// As of Spring Boot 2.1, we no longer need to load the SpringExtension
// because it's included as a meta annotation in the Spring Boot test annotations like
// @DataJpaTest, @WebMvcTest, and @SpringBootTest.
@ExtendWith(SpringExtension.class)
//If we leave away the controllers parameter,
//Spring Boot will include all controllers in the application context.
@WebMvcTest(controllers = {UserInfoController.class})
public class UserInfoControllerTest {
    ObjectMapper objectMapper = new ObjectMapper();
    @MockBean
    private UserInfoServiceImpl userInfoService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnUserInfo_WhenIdIsGiven() throws Exception {
        Optional<UserInfo> actualData = Optional.of(UserInfo.builder()
                .email("abdur.rehman@avaali.com").build());
        when(userInfoService.getUserInfo(1L)).thenReturn(actualData);

        mockMvc.perform(get("/user/get")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", String.valueOf(1L))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(actualData)));


    }

    @Test
    public void whenValidInput_thenReturns200() throws Exception {
        UserInfo actualBody = UserInfo.builder()
                .email("Abdur@gmail,com")
                .firstName("Abdur")
                .id(1L)
                .phone(987654321L)
                .build();
        mockMvc.perform(post("/user/add")
                .content(objectMapper.writeValueAsString(actualBody))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenValidPhone_thenReturnUserInfoView() throws Exception {
        when(userInfoService.findByPhone(1L)).thenReturn(stubUserInfoView());
        mockMvc.perform(get("/user/get/{phone}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phone").value(1L))
                .andExpect(content().json(objectMapper.writeValueAsString(stubUserInfoView())));
    }

    private UserInfoView stubUserInfoView() {
        return new UserInfoView() {
            @Override
            public String getFullName() {
                return "stubUser";
            }

            @Override
            public String getEmail() {
                return "stubEmail";
            }

            @Override
            public Long getPhone() {
                return 1L;
            }

            @Override
            public List<AddressView> getAddresses() {
                return null;
            }
        };
    }

}
