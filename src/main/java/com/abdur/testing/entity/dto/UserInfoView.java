package com.abdur.testing.entity.dto;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface UserInfoView {


    @Value("#{target.firstName+' '+target.lastName}")
    String getFullName();
    String getEmail();
    Long getPhone();
    List<AddressView> getAddresses();

}
