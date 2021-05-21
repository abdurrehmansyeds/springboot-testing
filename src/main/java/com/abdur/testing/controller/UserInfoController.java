package com.abdur.testing.controller;

import com.abdur.testing.entity.Address;
import com.abdur.testing.entity.UserInfo;
import com.abdur.testing.repository.Filter;
import com.abdur.testing.service.UserInfoService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity updateUserAddress(@RequestParam Long id, @RequestBody Address address) {
        userInfoService.updateUserAddress(id, address);
        return ResponseEntity.ok("Address Updated Successfully");
    }

    @GetMapping("/get/user/paginated")
    public ResponseEntity getUserUsingPagination(Pageable pageable) {
        return ResponseEntity.ok(userInfoService.findUserInfoByPagination(pageable));
    }

    @GetMapping("/get/name/{name}")
    public ResponseEntity getUserNameLike(@PathVariable String name) {
        return ResponseEntity.ok(userInfoService.getAllUserNameLike(name));
    }

    @GetMapping("/get/name/phone")
    public ResponseEntity getUserNameAndPhoneLike(@RequestParam String name, @RequestParam Long phone) {
        return ResponseEntity.ok(userInfoService.findUserNameAndPhoneLike(name, phone));
    }

    @GetMapping("/get/filter")
    public ResponseEntity getUserByDynamicFilter(@RequestBody List<Filter> filters) {
        return ResponseEntity.ok(userInfoService.findUserByDynamicFiltering(filters));
    }
}
