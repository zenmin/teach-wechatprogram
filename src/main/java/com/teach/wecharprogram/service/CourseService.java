package com.teach.wecharprogram.service;

import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.Course;
import java.util.List;

/**
* Create by Code Generator
* @Author ZengMin
* @Date 2019-07-20 11:50:30
*/

public interface CourseService {

    Course getOne(Long id);

    List<Course> list(Course course);

    Pager listByPage(Pager pager,Course course);

    Course save(Course course);

    boolean delete(String ids);

}
