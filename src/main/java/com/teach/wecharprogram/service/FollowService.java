package com.teach.wecharprogram.service;

import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.Follow;
import java.util.List;
import java.util.Map;

/**
* Create by Code Generator
* @Author ZengMin
* @Date 2019-07-13 20:43:30
*/

public interface FollowService {

    Follow getOne(Long id);

    List<Follow> list(Follow follow);

    Pager listByPage(Pager pager,Follow follow);

    boolean save(Follow follow);

    boolean delete(String ids);

    Map getMyFollow(Follow follow);

}
