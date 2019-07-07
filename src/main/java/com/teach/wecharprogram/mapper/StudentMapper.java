package com.teach.wecharprogram.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.teach.wecharprogram.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* Create by Code Generator
* @Author ZengMin
* @Date 2019-06-15 18:17:01
*/

public interface StudentMapper extends BaseMapper<Student> {

    @Select("SELECT * FROM student where classesId in (SELECT id from classes where schoolId = #{schoolId})")
    IPage<Student> getStudentsBySchool(Page<Object> objectPage,@Param("schoolId") Long schoolId);
}
