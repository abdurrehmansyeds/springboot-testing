package com.abdur.testing.service.impl;

import com.abdur.testing.entity.Address;
import com.abdur.testing.entity.UserInfo;
import com.abdur.testing.entity.dto.UserInfoDTO;
import com.abdur.testing.entity.dto.UserInfoView;
import com.abdur.testing.entity.dto.UserRoleView;
import com.abdur.testing.repository.Filter;
import com.abdur.testing.repository.UserInfoRepository;
import com.abdur.testing.repository.UserInfoSpecificationRepo;
import com.abdur.testing.service.UserInfoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@EnableTransactionManagement
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final UserInfoSpecificationRepo userInfoSpecificationRepo;

    public UserInfoServiceImpl(UserInfoRepository userInfoRepository, UserInfoSpecificationRepo userInfoSpecificationRepo) {
        this.userInfoRepository = userInfoRepository;
        this.userInfoSpecificationRepo = userInfoSpecificationRepo;
    }

    @Override
    @Transactional
    public void addUserInfo(UserInfo userInfo) {
       /* List<Address> addresses = userInfo.getAddresses();
        for (Address address:addresses){
            address.setUserInfo(userInfo);
        }

        userInfo.getProfession().setUserInfo(userInfo);*/

        UserInfo returnedEntity = userInfoRepository.save(userInfo);
        UserInfo proxyEntity = userInfoRepository.getOne(userInfo.getId());
        Optional<UserInfo> managedEntity = userInfoRepository.findById(userInfo.getId());

        //the FK in address table is not populated as
        //userinfo has mappedBy and we are trying to set in tenant entity instead we need to set these
        //in owner entity which is address
        //userInfo.setAddresses(addresses);


        //save method doesn't return managed entity hence dirty checking mechanism
        //doesn't apply
        //returnedEntity.setEmail("proxyEntity@example.com");


        //this will not generate insert query for other associated entities
        //but it will fire just one update query for email column
        //proxyEntity.setEmail("proxyEntity@example.com");
        //userInfoRepository.save(proxyEntity);

        //dirty checking mechanism is not working unless I annotate the method
        //with @Transactional
        //managedEntity.ifPresent(user-> user.setPhone(1234567890L));


        // userInfo's profession is not set
        /*userInfo.setProfession(Profession.builder()
                .companyAddress("Bangalore")
                .companyName("Avaali")
                .designation("Software")
                .userInfo(userInfo)
                .build());*/
    }

    @Override
    @Transactional
    public Optional<UserInfo> getUserInfo(final Long id) {
        Optional<UserInfo> userInfo = userInfoRepository.getUserInfoById(id);
        return userInfo;
    }

    @Override
    public List<UserInfo> getAllUserInfo() {
        return userInfoRepository.findAll();
    }

    @Override
    public void updateUserInfo(UserInfo userInfo) {
        userInfoRepository.findById(userInfo.getId())
                .ifPresent(user->{
                    user.setEmail(userInfo.getEmail());
                });
    }

    @Override
    public UserInfoView findByPhone(Long phone) {
        return userInfoRepository.findByPhone(phone, UserInfoView.class);
    }

    @Override
    public UserInfoDTO findByIdAndPhone(Long id, Long phone) {
        return userInfoRepository.findByIdAndPhone(id,phone);
    }

    @Override
    public UserInfoView findUsingEntityGraph(Long id){
      return userInfoRepository.findAddressesUsingEntityGraph(id);
    }

    @Override
    public UserRoleView findRoleUsingEntityGraph(Long id) {
        return userInfoRepository.findRolesUsingEntityGraph(id);
    }

    @Transactional
    @Override
    public void updateUserAddress(Long id,Address address){
        /*userInfoRepository.findById(id)
                .map(UserInfo::getAddresses)
                .map(getAddress->getAddress.get(0))
                .map(firstAddress->{
                    firstAddress.setCity(address.getCity());
                    firstAddress.setCountry(address.getCountry());
                    firstAddress.setPincode(address.getPincode());
                    firstAddress.setState(address.getState());
                    return firstAddress;
                });*/
        UserInfo userInfo = userInfoRepository.getOne(id);
        Address firstAddress = userInfo.getAddresses().get(0);
        firstAddress.setCity(address.getCity());
        firstAddress.setCountry(address.getCountry());
        firstAddress.setPincode(address.getPincode());
        firstAddress.setState(address.getState());
        userInfoRepository.save(userInfo);

    }

    @Override
    public Page<UserInfoView> findUserInfoByPagination(Pageable pageable) {
        return userInfoRepository.findAllProjectedBy(pageable);
    }

    @Override
    public List<UserInfo> getAllUserNameLike(String name) {
        return userInfoSpecificationRepo.findAllUserNameLike(name);
    }

    @Override
    public List<UserInfo> findUserNameAndPhoneLike(String name, Long phone) {
        return userInfoSpecificationRepo.findUserNameAndPhoneLike(name, phone);
    }

    @Override
    public List<UserInfo> findUserByDynamicFiltering(List<Filter> filters) {
        return userInfoSpecificationRepo.findUserByDynamicFilter(filters);
    }


}
