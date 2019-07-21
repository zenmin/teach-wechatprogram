package com.teach.wecharprogram.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.teach.wecharprogram.entity.StudentAssess;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Create by Code Generator
 *
 * @Author ZengMin
 * @Date 2019-07-20 12:21:11
 */

public interface StudentAssessMapper extends BaseMapper<StudentAssess> {

    @Select("SELECT a.*,s.name as studentName from student_assess a LEFT JOIN student s on a.studentId = s.id where a.studentId in (SELECT otherId from rel_user_typeid r where r.userId = #{uid} and r.type = 3)")
    List<StudentAssess> findByStudent(Long uid);

}
