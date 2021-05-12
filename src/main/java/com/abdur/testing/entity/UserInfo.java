package com.abdur.testing.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@NamedEntityGraph(name = "User.role",
    attributeNodes = @NamedAttributeNode("roles"))
//L2 cache
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class UserInfo {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long phone;
    @Version
    private Long version;
    @OneToMany(mappedBy = "userInfo",
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    //@JoinColumn(name = "user_info_id")
    @JsonManagedReference
    @BatchSize(size = 1)
    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private List<Address> addresses = new ArrayList<>();

    @OneToOne(mappedBy = "userInfo",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
    fetch = FetchType.LAZY)
    @JsonManagedReference
    //@LazyToOne(LazyToOneOption.NO_PROXY)
    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private Profession profession;

    @ManyToMany(cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST})

    @JoinTable(name = "USER_ROLES",
    joinColumns = @JoinColumn(name = "USER_INFO_ID"),
    inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    Set<Role> roles = new HashSet<>();


    public void addAddress(Address address){
        addresses.add(address);
        address.setUserInfo(this);
    }

    public void removeAddress(Address address){
        addresses.remove(address);
        address.setUserInfo(null);
    }

}
