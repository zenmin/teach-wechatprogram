package com.teach.wecharprogram.repostory.impl;

import com.teach.wecharprogram.entity.DO.StudentDo;
import com.teach.wecharprogram.entity.Follow;
import com.teach.wecharprogram.repostory.FollowRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/7/14 11:48
 */
@Repository
public class FollowRepositoryImpl implements FollowRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<StudentDo> getMyFollow(Follow follow) {
        StringBuilder sql = new StringBuilder(String.format("SELECT f.id,s.*,c.schoolName,c.schoolId,TIMESTAMPDIFF(YEAR,s.birthday,CURRENT_DATE) as age  FROM follow f LEFT JOIN student s ON f.studentId = s.id LEFT JOIN classes c ON s.classesId = c.id where f.uid = %s ", follow.getUid()));

        String studentName = follow.getStudentName();
        if (StringUtils.isNotBlank(studentName)) {
            sql.append(" and s.name like '%" + studentName + "%' ");
        }
        sql.append(" ORDER BY age asc ");
        List<StudentDo> studentDoList = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(StudentDo.class));
        return studentDoList;
    }
}
