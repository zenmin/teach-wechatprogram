package com.teach.wecharprogram.service;

import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.Student;
import java.util.List;

/**
* Create by Code Generator
* @Author ZengMin
* @Date 2019-06-15 18:17:01
*/

public interface StudentService {

    Student getOne(Long id);

    List<Student> list(Student student);

    Pager listByPage(Pager pager,Student student);

    Student save(Student student);

    boolean delete(String ids);

}
