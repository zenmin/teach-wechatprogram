package com.teach.wecharprogram.service.impl;

import com.teach.wecharprogram.entity.DO.StudentAssessDo;
import com.teach.wecharprogram.repostory.CourseRepository;
import com.teach.wecharprogram.service.StudentAssessService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.StudentAssess;
import com.teach.wecharprogram.mapper.StudentAssessMapper;
import com.teach.wecharprogram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import com.teach.wecharprogram.util.DateUtil;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * Create Code Generator
 *
 * @Author ZengMin
 * @Date 2019-07-20 12:21:11
 */

@Service
public class StudentAssessServiceImpl implements StudentAssessService {

    @Autowired
    StudentAssessMapper studentAssessMapper;

    @Autowired
    UserService userService;

    @Autowired
    CourseRepository courseRepository;

    @Override
    public StudentAssess getOne(Long id) {
        return studentAssessMapper.selectById(id);
    }

    @Override
    public List<StudentAssess> list(StudentAssess studentAssess) {
        List<StudentAssess> studentAssesss = studentAssessMapper.selectList(new QueryWrapper<>(studentAssess));
        return studentAssesss;
    }

    @Override
    public List<StudentAssessDo> listByPage(Pager pager, StudentAssess studentAssess) {
        List<StudentAssessDo> allAssessList = courseRepository.getAllAssessList(pager, studentAssess.getStudentName(), studentAssess.getText(), userService.getLoginUser().getId());
        return allAssessList;
    }

    @Override
    @Transactional
    public StudentAssess save(StudentAssess studentAssess) {
        studentAssess.setUid(userService.getLoginUser().getId());
        studentAssess.setUpdateTime(new Date());
        studentAssess.setDate(DateUtil.getNowDate());
        if (Objects.nonNull(studentAssess.getId())) {
            studentAssessMapper.updateById(studentAssess);
        } else {
            // 查当前孩子的评议  每个孩子只会存在一条记录
            StudentAssess one = studentAssessMapper.selectOne(new QueryWrapper<StudentAssess>().eq("studentId", studentAssess.getStudentId()));
            if (Objects.nonNull(one)) {
                one.setUpdateTime(new Date());
                one.setText(studentAssess.getText());
                studentAssessMapper.updateById(one);
            } else {
                studentAssessMapper.insert(studentAssess);
            }
        }
        return studentAssess;
    }

    @Override
    @Transactional
    public boolean delete(String ids) {
        List<Long> list = Lists.newArrayList();
        if (ids.indexOf(",") != -1) {
            List<String> asList = Arrays.asList(ids.split(","));
            asList.stream().forEach(o -> list.add(Long.valueOf(o)));
        } else {
            list.add(Long.valueOf(ids));
        }
        int i = studentAssessMapper.deleteBatchIds(list);
        return i > 0;
    }

    @Override
    public List<StudentAssess> getAssessByUser() {
        List<StudentAssess> byStudent = studentAssessMapper.findByStudent(userService.getLoginUser().getId());
        return byStudent;
    }

    @Override
    public StudentAssess selectOne(Long studentId) {
        StudentAssess one = studentAssessMapper.selectOne(new QueryWrapper<StudentAssess>().eq("studentId", studentId));
        return one;
    }


}
