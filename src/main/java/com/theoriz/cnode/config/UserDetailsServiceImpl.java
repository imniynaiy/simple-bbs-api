package com.theoriz.cnode.config;

import com.theoriz.cnode.domain.entity.User;
import com.theoriz.cnode.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public CnodeUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByLoginname(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("USER"));
        return new CnodeUserDetails(user.getId(),
                user.getLoginname(), user.getPassword(), enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, authorityList
        );
    }
}
