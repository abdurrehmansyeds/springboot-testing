package com.abdur.testing.service;

import com.abdur.testing.entity.Role;
import com.abdur.testing.entity.UserInfo;
import com.abdur.testing.entity.dto.AddressView;
import com.abdur.testing.entity.dto.UserInfoView;
import com.abdur.testing.entity.dto.UserRoleView;
import com.abdur.testing.repository.UserInfoRepository;
import com.abdur.testing.service.impl.UserInfoServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testing User info service class")
public class UserInfoServiceTest {

    @Mock
    private UserInfoRepository userInfoRepository;

    @InjectMocks
    private UserInfoServiceImpl userInfoService;

    @BeforeEach
    public void init() {
        System.out.println("Before Each called");
    }

    @AfterEach
    public void afterInit() {
        System.out.println("After each called");
    }


    @Test
    @DisplayName("Test to check if userinfo when given Id")
    public void shouldReturnUserInfo_whenIdIsGiven() throws Exception {
        Optional<UserInfo> actualData = Optional.of(UserInfo.builder()
                .email("abdur.rehman@avaali.com").build());
        when(userInfoRepository.getUserInfoById(1L)).thenReturn(actualData);

        Optional<UserInfo> result = userInfoService.getUserInfo(1L);
        verify(userInfoRepository).getUserInfoById(1L);

        assertEquals(actualData, result);
    }

    @Test
    @DisplayName("Test to check if userinfo returns all users")
    public void shouldReturnAllUserInfo() throws Exception {
        List<UserInfo> actualData = new ArrayList<>();
        actualData.add(UserInfo.builder().email("A").build());
        actualData.add(UserInfo.builder().email("B").build());

        when(userInfoRepository.findAll()).thenReturn(actualData);

        assertThat(actualData).isEqualTo(userInfoService.getAllUserInfo());

    }

    @Test
    @DisplayName("Test to check if userinfo returns userinfo when phone no is given")
    public void shouldReturnUserInfoViewWhenPhoneGiven() {
        UserInfoView expectedData = new UserInfoView() {
            @Override
            public String getFullName() {
                return "Abdur Rehman";
            }

            @Override
            public String getEmail() {
                return "A";
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

        when(userInfoRepository.findByPhone(1L, UserInfoView.class)).thenReturn(expectedData);

        UserInfoView actualData = userInfoService.findByPhone(1L);
        verify(userInfoRepository).findByPhone(1L, UserInfoView.class);
        assertThat(expectedData).isEqualTo(actualData);
    }

    @Nested
    @Disabled("Disabled for testing purpose")
    @DisplayName("testing the user role")
    class UserRoleTest {
        @Test
        public void shouldReturnUserRoleView_whenIdIsGiven() {
            UserRoleView expected = new UserRoleView() {
                @Override
                public String getFullName() {
                    return "Abdur Rehman";
                }

                @Override
                public Long getPhone() {
                    return 12345678L;
                }

                @Override
                public Set<Role> getRoles() {
                    return Collections.singleton(new Role(1L, "ROLE_ADMIN"));
                }
            };

            when(userInfoRepository.findRolesUsingEntityGraph(1L)).thenReturn(expected);
            UserRoleView actual = userInfoService.findRoleUsingEntityGraph(1L);
            assertThat(expected).isEqualTo(actual);
            assertThat(actual).isInstanceOf(UserRoleView.class);
            assertThat(actual.getRoles()).contains(new Role(1L, "ROLE_ADMIN"));
        }
    }

}
