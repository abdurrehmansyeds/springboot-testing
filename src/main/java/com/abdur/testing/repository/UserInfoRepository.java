package com.abdur.testing.repository;

import com.abdur.testing.entity.UserInfo;
import com.abdur.testing.entity.dto.UserInfoDTO;
import com.abdur.testing.entity.dto.UserInfoView;
import com.abdur.testing.entity.dto.UserRoleView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long>, JpaSpecificationExecutor<UserInfo> {

    @Lock(value = LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Optional<UserInfo> getUserInfoById(Long id);

    <T> T findByPhone(Long phoneNumber, Class<T> type);

    UserInfoDTO findByIdAndPhone(Long id, Long phone);

    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD,
            attributePaths = {"addresses"})
    @Query("select u from UserInfo u where u.id = ?1")
    UserInfoView findAddressesUsingEntityGraph(Long id);

    @EntityGraph(value = "User.role")
    @Query("select u from UserInfo u where u.id  = ?1")
    UserRoleView findRolesUsingEntityGraph(Long id);

    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD,
    attributePaths = {"addresses","profession"})
    Page<UserInfoView> findAllProjectedBy(Pageable pageable);

}
