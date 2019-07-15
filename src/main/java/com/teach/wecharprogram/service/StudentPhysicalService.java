package com.teach.wecharprogram.service;

import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.StudentPhysical;
import java.util.List;

/**
* Create by Code Generator
* @Author ZengMin
* @Date 2019-07-15 20:15:32
*/

public interface StudentPhysicalService {

    StudentPhysical getOne(Long id);

    List<StudentPhysical> list(StudentPhysical studentPhysical);

    Pager listByPage(Pager pager,StudentPhysical studentPhysical);

    StudentPhysical save(StudentPhysical studentPhysical);

    boolean delete(String ids);

}
