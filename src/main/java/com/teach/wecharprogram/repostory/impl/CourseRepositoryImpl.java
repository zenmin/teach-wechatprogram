package com.teach.wecharprogram.repostory.impl;

import com.teach.wecharprogram.entity.DO.CoursePlanCourseDo;
import com.teach.wecharprogram.repostory.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/7/20 18:55
 */
@Repository
public class CourseRepositoryImpl implements CourseRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<CoursePlanCourseDo> getMyPlanByClassesId(Long classesId, Long uid, Integer day) {
        StringBuilder sql = new StringBuilder("SELECT p.id,p.createTime,p.courseId,c.name as courseName,p.startTime,p.endTime,p.day from course_plan p left JOIN course c on p.courseId = c.id where c.uid = %s and p.classesId = %s ");
        if (Objects.nonNull(day) && day != 0) {
            sql.append(" and day = " + day);
        }
        sql.append(" order by day asc,startTime asc");
        String lastSql = String.format(sql.toString(), uid, classesId);
        List<CoursePlanCourseDo> query = jdbcTemplate.query(lastSql, new BeanPropertyRowMapper<>(CoursePlanCourseDo.class));
        return query;
    }
}
