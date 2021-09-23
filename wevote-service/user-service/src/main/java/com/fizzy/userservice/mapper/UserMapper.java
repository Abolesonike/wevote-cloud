package com.fizzy.userservice.mapper;

import com.fizzy.core.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    /**
     * 查找所有用户
     */
    List<User> findAllUser();

    /**
     * 通过用户名和密码查找
     * String username：用户名，String password：密码
     */
    User getByUsernameAndPassword(String username,String password);

    /**
     * 通过用户名查找
     * String  name：用户名
     */
    User findUserByName(String  name);

    /**
     * 通过用户名查找
     * String  id：用户id
     */
    User findUserById(int id);

    /**
     * 插入一个用户
     * @param user 传入一个用户对象
     * @return 是否成功
     */
    Boolean insertOneUser(User user);
}
