package com.teach.wecharprogram.service;

import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.Follow;
import java.util.List;

/**
* Create by Code Generator
* @Author ZengMin
* @Date 2019-06-15 18:37:25
*/

public interface FollowService {

    Follow getOne(Long id);

    List<Follow> list(Follow follow);

    Pager listByPage(Pager pager,Follow follow);

    Follow save(Follow follow);

    boolean delete(String ids);

}
