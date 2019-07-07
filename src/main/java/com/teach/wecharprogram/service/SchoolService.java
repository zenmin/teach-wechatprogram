package com.teach.wecharprogram.service;

import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.School;
import java.util.List;

/**
* Create by Code Generator
* @Author ZengMin
* @Date 2019-06-15 17:59:30
*/

public interface SchoolService {

    School getOne(Long id);

    List<School> list(School school);

    Pager listByPage(Pager pager,School school);

    School save(School school);

    boolean delete(String ids);

    Object relHeadMasterToSchool(Long userId, String schoolId);
}
