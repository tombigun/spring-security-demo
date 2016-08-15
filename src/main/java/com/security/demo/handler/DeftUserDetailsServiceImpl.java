package com.security.demo.handler;

import com.security.demo.dao.UserDao;
import com.security.demo.handler.domain.BadCredentialsEnum;
import com.security.demo.handler.domain.LoginUser;
import com.security.demo.model.User;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DeftUserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserDao userDao;

    public UserDetails loadUserByUsername(String username) {
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        if (username == null || username.length() == 0) {
            throw new BadCredentialsException(BadCredentialsEnum.USERNAME_IS_REQUIRED.toString());
        }

        User user = userDao.getUserByUserName(username);

        if (null == user)
            throw new BadCredentialsException(BadCredentialsEnum.USER_IS_NOT_EXIST.toString());

        List<String> roles = new ArrayList<String>();
        roles.add("ROLE_USER");

        if("admin".equals(user.getUserType()))
            roles.add("ROLE_ADMIN");

        LoginUser userDetails = new LoginUser(user.getUserName(), user.getUserPassword().toLowerCase(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, obtainGrantedAuthorities(roles));
        userDetails.setUser(user);

        return userDetails;
    }

    private Set<GrantedAuthority> obtainGrantedAuthorities(List<String> roleList) {
        Set<GrantedAuthority> authSet = new HashSet<>();
        if (roleList != null) {
            for (int i = 0; i < roleList.size(); i++) {
                String roleName = roleList.get(i);
                authSet.add(new ComparableGrantedAuthority(roleName));
            }
        }
        return authSet;
    }

}
