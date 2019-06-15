package com.teach.wecharprogram.service;

import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.User;
import java.util.List;

/**
* Create by Code Generator
* @Author ZengMin
* @Date 2019-06-15 18:17:10
*/

public interface UserService {

    User getOne(Long id);

    List<User> list(User user);

    Pager listByPage(Pager pager,User user);

    User save(User user);

    boolean delete(String ids);

}
