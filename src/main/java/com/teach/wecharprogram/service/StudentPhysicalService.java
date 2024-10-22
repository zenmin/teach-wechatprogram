package com.teach.wecharprogram.service;

import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.DO.StudentPhysicalDo;
import com.teach.wecharprogram.entity.StudentPhysical;
import com.teach.wecharprogram.entity.User;
import com.teach.wecharprogram.entity.vo.StudentPhysicalTextVO;
import com.teach.wecharprogram.entity.vo.StudentPhysicalVO;

import java.util.List;
import java.util.Map;

/**
* Create by Code Generator
* @Author ZengMin
* @Date 2019-07-15 20:15:32
*/

public interface StudentPhysicalService {

    StudentPhysical getOne(Long id);

    List<StudentPhysical> list(StudentPhysical studentPhysical);

    Pager listByPage(Pager pager,StudentPhysicalDo studentPhysical);

    StudentPhysical save(StudentPhysical studentPhysical);

    boolean delete(String ids);

    List<Map> topFive(User userId);

    List<Map> topUpFive(User loginUser);

    List<StudentPhysical> getOneByStudent(Long studentId, String date, Boolean queryNow);

    List<StudentPhysicalTextVO> getPhysicalByStudentIds(String studentIds);

}
