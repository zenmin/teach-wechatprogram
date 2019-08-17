package com.teach.wecharprogram.service;

import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.User;
import com.teach.wecharprogram.entity.vo.UpdateUserVo;

import javax.servlet.http.HttpServletRequest;
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

    User getLoginUser();

    User getLoginUser(HttpServletRequest request);

    User findByPhone(String phone);

    void updateLoginTime(User user);

    User findByOpenId(String openid);

    Object updateUserData(UpdateUserVo updateUserVo,String token);

    Object getMyRelInfo(User user);

    boolean selectRole(Long roleId, Long userId);

    User findByUserNameAndPwd(String username,String password);

    boolean updateMyPwd(String oldPwd, String newPwd);
}
