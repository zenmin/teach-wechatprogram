package com.teach.wecharprogram.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.teach.wecharprogram.entity.DO.StudentDo;
import com.teach.wecharprogram.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Create by Code Generator
 *
 * @Author ZengMin
 * @Date 2019-06-15 18:17:01
 */

public interface StudentMapper extends BaseMapper<Student> {

    @Select("SELECT * FROM student where classesId in (SELECT id from classes where schoolId = #{schoolId})")
    IPage<Student> getStudentsBySchool(Page<Object> objectPage, @Param("schoolId") Long schoolId);

    @Update("update student set classesId = null,classesName = null where classesId in (#{classesIds})")
    void updateClassIdNull(String classesIds);

    @Select("<script>select s.*,TIMESTAMPDIFF(YEAR,s.birthday,CURRENT_DATE) as age,c.schoolName as schoolName from student s left join classes c on s.classesId = c.id where s.classesId = #{classesId} <if test=\"name != null \"> and s.name like concat('%',#{name},'%')  </if> </script>")
    List<StudentDo> selectStudents(@Param("classesId") Long classesId, @Param("name") String name);

    @Select("<script>select s.*,TIMESTAMPDIFF(YEAR,s.birthday,CURRENT_DATE) as age,c.schoolName as schoolName from student s left join classes c on s.classesId = c.id where 1=1 "
            + "<if test=\"name != null \"> and s.name like concat('%',#{name},'%')  </if> " +
            "<if test=\"gender != null \"> and s.gender = #{gender}  </if> " +
            "<if test=\"status != null \"> and s.status = #{status}  </if> " +
            "<if test=\"birthday != null \"> and YEAR(s.birthday) = #{birthday}  </if> " +
            "<if test=\"schoolId != null \"> and s.schoolId = #{schoolId}  </if> " +
            " order by s.birthday asc" +
            "</script>")
    List<StudentDo> getStudentAllMsg(@Param("name") String name, @Param("gender") Integer gender, @Param("status") Integer status, @Param("birthday") String birthday, @Param("classesId") Long classesId, @Param("schoolId") Long schoolId);

    @Select("<script>select s.*,TIMESTAMPDIFF(YEAR,s.birthday,CURRENT_DATE) as age,c.schoolName as schoolName from student s left join classes c on s.classesId = c.id where s.id in " +
            "<foreach collection=\"ids\" item=\"id\" open=\"(\" close=\")\" separator=\",\">#{id}</foreach> </script>")
    List<StudentDo> selectStudentsIdIn(@Param("ids") List<Long> ids);
}
