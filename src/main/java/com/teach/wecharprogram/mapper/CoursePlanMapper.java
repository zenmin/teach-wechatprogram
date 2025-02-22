package com.teach.wecharprogram.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.teach.wecharprogram.entity.CoursePlan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teach.wecharprogram.entity.DO.CoursePlanClassesDo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * Create by Code Generator
 *
 * @Author ZengMin
 * @Date 2019-07-15 20:58:15
 */

public interface CoursePlanMapper extends BaseMapper<CoursePlan> {

    @Select("SELECT p.id,p.createTime,p.classesId,p.courseId,c.name as classesName,c.schoolName,c.schoolId,p.day from course_plan p LEFT JOIN classes c on p.classesId = c.id where p.uid = #{uid} group by classesId,day order by p.createTime desc,p.day asc")
    IPage<CoursePlanClassesDo> findByUid(Page page,@Param("uid") Long uid);

}
