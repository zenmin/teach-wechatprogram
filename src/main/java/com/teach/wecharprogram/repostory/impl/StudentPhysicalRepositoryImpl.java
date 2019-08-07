package com.teach.wecharprogram.repostory.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.DO.StudentPhysicalDo;
import com.teach.wecharprogram.repostory.StudentPhysicalRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/8/7 19:14
 */
@Repository
public class StudentPhysicalRepositoryImpl implements StudentPhysicalRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Pager<StudentPhysicalDo> selectPage(Pager pager, StudentPhysicalDo studentPhysical) {
        Long schoolId = studentPhysical.getSchoolId();
        Integer no = studentPhysical.getNo();
        Long classesId = studentPhysical.getClassesId();
        String studentName = studentPhysical.getStudentName();
        String createUserName = studentPhysical.getCreateUserName();
        String date = studentPhysical.getDate();
        Integer gender = studentPhysical.getGender();
        Long id = studentPhysical.getId();
        Long studentId = studentPhysical.getStudentId();
        String sortFiled = studentPhysical.getSortFiled();
        Integer sortType = studentPhysical.getSortType();

        StringBuilder sql = new StringBuilder("SELECT p.*,TIMESTAMPDIFF(YEAR,s.birthday,CURRENT_DATE) as age,s.gender FROM student_physical p left JOIN student s on p.studentId = s.id WHERE 1=1 ");
        StringBuilder countSql = new StringBuilder("SELECT count(*) FROM student_physical p left JOIN student s on p.studentId = s.id WHERE 1=1 ");

        if (Objects.nonNull(schoolId)) {
            sql.append(String.format(" and p.classesId in (SELECT id from classes where schoolId = %s) ", schoolId));
            countSql.append(String.format(" and p.classesId in (SELECT id from classes where schoolId = %s) ", schoolId));
        }

        if (Objects.nonNull(no)) {
            sql.append(String.format(" and p.no = '%s' ", no));
            countSql.append(String.format(" and p.no = '%s' ", no));
        }

        if (Objects.isNull(schoolId) && Objects.nonNull(classesId)) {
            sql.append(String.format(" and p.classesId = %s ", classesId));
            countSql.append(String.format(" and p.classesId = %s ", classesId));
        }

        if (StringUtils.isNotBlank(studentName)) {
            sql.append(String.format(" and p.studentName like %s", "'%" + studentName + "%'"));
            countSql.append(String.format(" and p.studentName like %s", "'%" + studentName + "%'"));
        }

        if (StringUtils.isNotBlank(createUserName)) {
            sql.append(String.format(" and p.createUserName like %s", "'%" + createUserName + "%'"));
            countSql.append(String.format(" and p.createUserName like %s", "'%" + createUserName + "%'"));
        }

        if (StringUtils.isNotBlank(date)) {
            sql.append(String.format(" and p.date = '%s'", date));
            countSql.append(String.format(" and p.date = '%s'", date));
        }

        if (Objects.nonNull(gender)) {
            sql.append(String.format(" and s.gender = %s", gender));
            countSql.append(String.format(" and s.gender = %s", gender));
        }

        if (Objects.nonNull(id)) {
            sql.append(String.format(" and p.id = %s", id));
            countSql.append(String.format(" and p.id = %s", id));
        }

        if (Objects.nonNull(studentId)) {
            sql.append(String.format(" and p.studentId = %s", studentId));
            countSql.append(String.format(" and p.studentId = %s", studentId));
        }

        if (StringUtils.isNotBlank(sortFiled)) {
            sql.append(String.format(" order by %s %s ", sortFiled, sortType == 1 ? "asc" : "desc"));
        }

        sql.append(String.format(" limit %s,%s", pager.getSize() * pager.getNum() - 1 < 0 ? 0 : 1, pager.getSize()));
        List<StudentPhysicalDo> list = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(StudentPhysicalDo.class));
        Long count = jdbcTemplate.queryForObject(countSql.toString(), Long.class);
        Page<StudentPhysicalDo> studentPhysicalDoPage = new Page<>();
        studentPhysicalDoPage.setSize(pager.getSize());
        studentPhysicalDoPage.setCurrent(pager.getNum() == 0 ? 1 : pager.getNum());
        studentPhysicalDoPage.setRecords(list);
        studentPhysicalDoPage.setTotal(count);
        studentPhysicalDoPage.setPages(count <= pager.getSize() ? 1 : (int) (count / pager.getSize()));
        return Pager.of(studentPhysicalDoPage);
    }
}
