package com.teach.wecharprogram.service;

import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.User;

import java.util.List;

/**
* Create by Code Generator
* @Author ZengMin
* @Date 2019-04-17 15:10:45
*/

public interface UserService {

    User getOne(Long id);

    List<User> list(User user);

    Pager listByPage(Pager pager, User user);

    User save(User user);

    boolean delete(String ids);

    User findByInviteCode(Long code);

    User getLoginUser(String token);

    User findByPhone(String phone);

    void updateLoginTime(User user);

    User findByOpenId(String openid);
}
