package com.abdur.testing.service;

import com.abdur.testing.entity.Address;
import com.abdur.testing.entity.UserInfo;
import com.abdur.testing.entity.dto.UserInfoDTO;
import com.abdur.testing.entity.dto.UserInfoView;
import com.abdur.testing.entity.dto.UserRoleView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserInfoService {
    void addUserInfo(UserInfo userInfo);
    Optional<UserInfo> getUserInfo(Long id);
    List<UserInfo> getAllUserInfo();
    void updateUserInfo(UserInfo userInfo);
    UserInfoView findByPhone(Long phone);
    UserInfoDTO findByIdAndPhone(Long id, Long phone);
    UserInfoView findUsingEntityGraph(Long id);
    UserRoleView findRoleUsingEntityGraph(Long id);
    void updateUserAddress(Long id, Address address);
    Page<UserInfoView> findUserInfoByPagination(Pageable pageable);
}
