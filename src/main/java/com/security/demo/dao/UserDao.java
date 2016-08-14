package com.security.demo.dao;


import com.security.demo.model.User;


public interface UserDao {
    /**
     * 用户登录
     *
     * @param userName
     * @return
     */
    public User getUserByUserName(String userName);

}
