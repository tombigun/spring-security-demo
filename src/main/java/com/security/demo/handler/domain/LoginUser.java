package com.security.demo.handler.domain;


import com.security.demo.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public class LoginUser extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 1919464185097508773L;

    private User user;

    public LoginUser(String username, String password, boolean enabled, boolean accountNonExpired,
                     boolean credentialsNonExpired, boolean accountNonLocked, Collection<GrantedAuthority> authorities)
            throws IllegalArgumentException {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public String getUserEmail(){
        return user.getUserEmail();
    }

    public static LoginUser getCurrentLoginUser() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            if ("anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
                return null;
            }

            return (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }

        return null;
    }


}
