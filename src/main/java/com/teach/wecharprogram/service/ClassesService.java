package com.teach.wecharprogram.service;

import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.Classes;
import com.teach.wecharprogram.entity.Student;

import java.util.List;

/**
* Create by Code Generator
* @Author ZengMin
* @Date 2019-06-15 17:59:22
*/

public interface ClassesService {

    Classes getOne(Long id);

    List<Classes> list(Classes classes);

    Pager listByPage(Pager pager,Classes classes);

    Classes save(Classes classes);

    boolean delete(String ids);

    List<Classes> getClasses(Long id);

    List<Student> getStudents(Long id, Long classesId, String name);
}
