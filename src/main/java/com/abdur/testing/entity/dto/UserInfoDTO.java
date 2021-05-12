package com.abdur.testing.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoDTO {
    private String firstName;
    private String lastName;
    private String email;
    private Long phone;
}
