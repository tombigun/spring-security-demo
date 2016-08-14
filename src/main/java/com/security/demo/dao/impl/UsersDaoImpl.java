package com.security.demo.dao.impl;

import com.security.demo.dao.UserDao;
import com.security.demo.model.User;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;


public class UsersDaoImpl implements UserDao {

    private String demopro;
//    @Resource
//    private SessionFactory sessionFactory;

    @Override
//    @Transactional(readOnly = true)
    public User getUserByUserName(String userName) {
//        Session session = sessionFactory.getCurrentSession();
//        Criteria criteria = session.createCriteria(User.class);
//        criteria.add(Restrictions.eq("userName", userName));
//        List<User> list = criteria.list();
//        if (list == null || list.size() == 0) return null;
//
//        return list.get(0);


        User user = new User();

        user.setUserId(1);
        user.setUserName("admin");
        user.setUserPassword(new Md5PasswordEncoder().encodePassword("123456", null));
        user.setUserType("admin");

        return user;
    }

    public void setDemopro(String demopro) {
        this.demopro = demopro;
    }
}
