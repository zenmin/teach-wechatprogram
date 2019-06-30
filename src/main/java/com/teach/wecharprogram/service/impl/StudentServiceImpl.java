package com.teach.wecharprogram.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.teach.wecharprogram.common.CommonException;
import com.teach.wecharprogram.common.constant.CommonConstant;
import com.teach.wecharprogram.entity.RelUserTypeId;
import com.teach.wecharprogram.mapper.RelUserTypeidMapper;
import com.teach.wecharprogram.service.StudentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.Student;
import com.teach.wecharprogram.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/**
 * Create Code Generator
 *
 * @Author ZengMin
 * @Date 2019-06-15 18:17:01
 */

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    RelUserTypeidMapper relUserTypeidMapper;

    @Override
    public Student getOne(Long id) {
        return studentMapper.selectById(id);
    }

    @Override
    public List<Student> list(Student student) {
        List<Student> students = studentMapper.selectList(new QueryWrapper<>(student));
        return students;
    }

    @Override
    public Pager listByPage(Pager pager, Student student) {
        IPage<Student> studentIPage = studentMapper.selectPage(new Page<>(pager.getNum(), pager.getSize()), new QueryWrapper<>(student));
        return Pager.of(studentIPage);
    }

    @Override
    @Transactional(rollbackFor = CommonException.class)
    public Student save(Student student) {
        if (Objects.nonNull(student.getId())) {
            studentMapper.updateById(student);
        } else {
            studentMapper.insert(student);
        }
        return student;
    }

    @Override
    @Transactional(rollbackFor = CommonException.class)
    public boolean delete(String ids) {
        List<Long> list = Lists.newArrayList();
        if (ids.indexOf(",") != -1) {
            List<String> asList = Arrays.asList(ids.split(","));
            asList.stream().forEach(o -> list.add(Long.valueOf(o)));
        } else {
            list.add(Long.valueOf(ids));
        }
        int i = studentMapper.deleteBatchIds(list);
        return i > 0;
    }

    @Override
    public Object relFamilyToStudent(Long userId, String studentsId) {
        String[] split = studentsId.split(",");
        List<String> asList = Arrays.asList(split);
        asList.stream().forEach(o -> {
            RelUserTypeId relUserTypeId = new RelUserTypeId(userId, Long.valueOf(o), CommonConstant.REL_STUDENTS);
            RelUserTypeId one = relUserTypeidMapper.selectOne(new QueryWrapper<>(relUserTypeId));
            if (Objects.isNull(one)) {
                relUserTypeidMapper.insert(relUserTypeId);
            }
        });
        List<RelUserTypeId> relUserTypeIds = relUserTypeidMapper.selectList(new QueryWrapper<RelUserTypeId>()
                .eq("userId", userId).eq("type", CommonConstant.REL_STUDENTS));
        return relUserTypeIds;
    }

    @Override
    public boolean delRelFamilyToStudent(Long userId, String studentsId) {
        int delete = relUserTypeidMapper.delete(new UpdateWrapper<RelUserTypeId>().eq("userId", userId).eq("otherId", studentsId));
        return delete > 0;
    }


}
