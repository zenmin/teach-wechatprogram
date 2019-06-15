package com.teach.wecharprogram.service;

import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.Logs;
import java.util.List;

/**
* Create by Code Generator
* @Author ZengMin
* @Date 2019-06-15 17:29:59
*/

public interface LogsService {

    Logs getOne(Long id);

    List<Logs> list(Logs logs);

    Pager listByPage(Pager pager,Logs logs);

    void save(Logs logs);

    boolean delete(String ids);

}
