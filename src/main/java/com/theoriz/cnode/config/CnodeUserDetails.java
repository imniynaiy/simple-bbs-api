package com.theoriz.cnode.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class CnodeUserDetails extends User {
    private Long id;

    public CnodeUserDetails(Long id,String loginname, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, List<SimpleGrantedAuthority> authorityList) {
        super(loginname,password,enabled,accountNonExpired,credentialsNonExpired,accountNonLocked,authorityList);
        this.id = id;
    }
}
