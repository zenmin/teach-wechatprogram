package com.teach.wecharprogram.mapper;

import com.teach.wecharprogram.entity.StudentPhysical;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teach.wecharprogram.entity.vo.StudentPhysicalTextVO;
import com.teach.wecharprogram.entity.vo.StudentPhysicalVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * Create by Code Generator
 *
 * @Author ZengMin
 * @Date 2019-07-15 20:15:32
 */

public interface StudentPhysicalMapper extends BaseMapper<StudentPhysical> {

    @Select("<script>select * from student_physical s where s.studentId = #{studentId} " +
            "<if test=\"date != null \"> and s.date != #{date}  </if>" +
            "order by s.date desc limit 1</script>")
    StudentPhysical getOneByStudent(@Param("studentId") Long studentId, @Param("date") String date);

    @Select("<script>select classesId,studentName,bmi,allScore,date from student_physical s" +
            " where classesId in <foreach collection=\"classesIds\" item=\"id\" open=\"(\" close=\")\" separator=\",\">#{id}</foreach> " +
            " GROUP BY studentId order by s.allScore desc limit #{count}</script>")
    List<StudentPhysicalVO> selectToFive(@Param("count") int count, @Param("classesIds") Set<Long> classesIds);

    @Select("SELECT studentName,bmi,allScore,date,allRemark,createUserName from student_physical where studentId = #{studentId} ORDER BY date desc limit 1")
    StudentPhysicalTextVO getPhysicalByStudentIds(@Param("studentId") Long studentId);
}