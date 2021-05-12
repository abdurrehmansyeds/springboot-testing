package com.abdur.testing.entity.dto;

import com.abdur.testing.entity.Role;
import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public interface UserRoleView {

    @Value("#{target.firstName+' '+target.lastName}")
    String getFullName();
    Long getPhone();
    Set<Role> getRoles();
}
