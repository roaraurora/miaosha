package com.imooc.miaosha.service;

import com.imooc.miaosha.dao.UserDao;
import com.imooc.miaosha.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getById(int id) {
        return userDao.getById(id);
    }

    //@Transactional
    public void tx() {
        User user1 = new User();
        user1.setId(3);
        user1.setName("temp");
        userDao.insert(user1);
        User user2 = new User();
        user2.setId(1);
        user2.setName("temp");
        userDao.insert(user2);
    }
}
