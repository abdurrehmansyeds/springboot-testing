package com.abdur.testing.controller;

import com.abdur.testing.entity.Address;
import com.abdur.testing.entity.UserInfo;
import com.abdur.testing.service.UserInfoService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserInfoController {

    private final UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @PostMapping("/add")
    public ResponseEntity addUserInfo(@RequestBody UserInfo userInfo){
        userInfoService.addUserInfo(userInfo);
        return ResponseEntity.ok("User Added Successfully");
    }

    @GetMapping("/get")
    public ResponseEntity getUserInfo(@RequestParam Long id){
       return ResponseEntity.ok(userInfoService.getUserInfo(id));

    }

    @GetMapping("/all")
    public ResponseEntity getALlUserInfo(){
        return ResponseEntity.ok(userInfoService.getAllUserInfo());
    }

    @PutMapping("/update")
    public ResponseEntity uppdateUserInfo(@RequestBody UserInfo userInfo){
        userInfoService.updateUserInfo(userInfo);
        return ResponseEntity.ok("User Updated Successfully");
    }

    @GetMapping("/get/{phone}")
    public ResponseEntity getUserInfoByPhone(@PathVariable Long phone){
        return ResponseEntity.ok(userInfoService.findByPhone(phone));
    }

    @GetMapping("/get/{id}/{phone}")
    public ResponseEntity getUserInfoByIdAndPhone(@PathVariable Long id,@PathVariable Long phone){
        return ResponseEntity.ok(userInfoService.findByIdAndPhone(id,phone));
    }

    @GetMapping("/get/user/entitygraph")
    public ResponseEntity getUserUsingEntityGraph(@RequestParam Long id){
        return ResponseEntity.ok(userInfoService.findUsingEntityGraph(id));
    }

    @GetMapping("/get/role/entitygraph")
    public ResponseEntity getRoleUsingEntityGraph(@RequestParam Long id){
        return ResponseEntity.ok(userInfoService.findRoleUsingEntityGraph(id));
    }

    @PutMapping("update/address")
    public ResponseEntity updateUserAddress(@RequestParam Long id, @RequestBody Address address){
        userInfoService.updateUserAddress(id,address);
        return ResponseEntity.ok("Address Updated Successfully");
    }

    @GetMapping("/get/user/paginated")
    public ResponseEntity getUserUsingPagination(Pageable pageable){
        return ResponseEntity.ok(userInfoService.findUserInfoByPagination(pageable));
    }
}
