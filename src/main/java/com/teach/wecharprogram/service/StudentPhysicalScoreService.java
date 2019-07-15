package com.teach.wecharprogram.service;

import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.StudentPhysicalScore;
import java.util.List;

/**
* Create by Code Generator
* @Author ZengMin
* @Date 2019-07-15 20:38:53
*/

public interface StudentPhysicalScoreService {

    StudentPhysicalScore getOne(Long id);

    List<StudentPhysicalScore> list(StudentPhysicalScore studentPhysicalScore);

    Pager listByPage(Pager pager,StudentPhysicalScore studentPhysicalScore);

    StudentPhysicalScore save(StudentPhysicalScore studentPhysicalScore);

    boolean delete(String ids);

}
