package com.teach.wecharprogram.service;

import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.Role;
import java.util.List;

/**
* Create by Code Generator
* @Author ZengMin
* @Date 2019-06-15 17:46:36
*/

public interface RoleService {

    Role getOne(Long id);

    List<Role> list(Role role);

    Pager listByPage(Pager pager,Role role);

    Role save(Role role);

    boolean delete(String ids);

}
