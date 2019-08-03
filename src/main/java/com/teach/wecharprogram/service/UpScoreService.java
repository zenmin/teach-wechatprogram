package com.teach.wecharprogram.service;

import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.UpScore;
import java.util.List;

/**
* Create by Code Generator
* @Author ZengMin
* @Date 2019-07-29 22:14:39
*/

public interface UpScoreService {

    UpScore getOne(Long id);

    List<UpScore> list(UpScore upScore);

    Pager listByPage(Pager pager,UpScore upScore);

    void save(UpScore upScore);

    boolean delete(String ids);

}
