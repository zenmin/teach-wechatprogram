package com.teach.wecharprogram.service.impl;

import com.teach.wecharprogram.common.constant.CommonConstant;
import com.teach.wecharprogram.entity.RelUserTypeId;
import com.teach.wecharprogram.entity.Student;
import com.teach.wecharprogram.mapper.RelUserTypeidMapper;
import com.teach.wecharprogram.mapper.StudentMapper;
import com.teach.wecharprogram.service.ClassesService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.Classes;
import com.teach.wecharprogram.mapper.ClassesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import com.teach.wecharprogram.util.DateUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Create Code Generator
 *
 * @Author ZengMin
 * @Date 2019-06-15 17:59:22
 */

@Service
public class ClassesServiceImpl implements ClassesService {

    @Autowired
    ClassesMapper classesMapper;

    @Autowired
    RelUserTypeidMapper userTypeidMapper;

    @Autowired
    StudentMapper studentMapper;

    @Override
    public Classes getOne(Long id) {
        return classesMapper.selectById(id);
    }

    @Override
    public List<Classes> list(Classes classes) {
        if (StringUtils.isNotBlank(classes.getCreateTimeQuery())) {
            classes.setCreateTime(DateUtil.parseToDate(classes.getCreateTimeQuery()));
        }
        List<Classes> classess = classesMapper.selectList(new QueryWrapper<>(classes));
        return classess;
    }

    @Override
    public Pager listByPage(Pager pager, Classes classes) {
        if (StringUtils.isNotBlank(classes.getCreateTimeQuery())) {
            classes.setCreateTime(DateUtil.parseToDate(classes.getCreateTimeQuery()));
        }
        IPage<Classes> classesIPage = classesMapper.selectPage(new Page<>(pager.getNum(), pager.getSize()), new QueryWrapper<>(classes));
        return Pager.of(classesIPage);
    }

    @Override
    @Transactional
    public Classes save(Classes classes) {
        if (Objects.nonNull(classes.getId())) {
            classesMapper.updateById(classes);
        } else {
            classesMapper.insert(classes);
        }
        return classes;
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
        int i = classesMapper.deleteBatchIds(list);
        return i > 0;
    }

    @Override
    public List<Classes> getClasses(Long userId) {
        List<RelUserTypeId> relUserTypeIds = userTypeidMapper.selectList(new QueryWrapper<RelUserTypeId>().eq("userId", userId).eq("type", 2));
        List<Long> collect = relUserTypeIds.stream().map(RelUserTypeId::getOtherId).collect(Collectors.toList());
        List<Classes> classesList = classesMapper.selectList(new QueryWrapper<Classes>().in("id", collect));
        return classesList;
    }

    @Override
    public List<Student> getStudents(Long userId, Long classesId, String name) {
        QueryWrapper<Student> q = new QueryWrapper<Student>().in("classesId", classesId).eq("status", CommonConstant.STATUS_OK);
        if (StringUtils.isNotBlank(name)) {
            q.like("name", name);
        }
        List<Student> students = studentMapper.selectList(q);
        return students;
    }


}
