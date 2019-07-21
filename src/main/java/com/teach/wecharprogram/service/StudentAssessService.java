package com.teach.wecharprogram.service;

import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.DO.StudentAssessDo;
import com.teach.wecharprogram.entity.StudentAssess;
import java.util.List;

/**
* Create by Code Generator
* @Author ZengMin
* @Date 2019-07-20 12:21:11
*/

public interface StudentAssessService {

    StudentAssess getOne(Long id);

    List<StudentAssess> list(StudentAssess studentAssess);

    List<StudentAssessDo> listByPage(Pager pager, StudentAssess studentAssess);

    StudentAssess save(StudentAssess studentAssess);

    boolean delete(String ids);

    List<StudentAssess> getAssessByUser();

    StudentAssess selectOne(Long studentId);

}
