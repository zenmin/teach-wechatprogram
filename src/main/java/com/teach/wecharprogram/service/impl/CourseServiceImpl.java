package com.teach.wecharprogram.service.impl;

import com.teach.wecharprogram.service.CourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.Course;
import com.teach.wecharprogram.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import com.teach.wecharprogram.util.DateUtil;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/**
* Create Code Generator
* @Author ZengMin
* @Date 2019-06-15 18:37:02
*/

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseMapper courseMapper;

    @Override
    public Course getOne(Long id){
        return courseMapper.selectById(id);
    }

    @Override
    public List<Course> list(Course course) {
        if(StringUtils.isNotBlank(course.getCreateTimeQuery())){
            course.setCreateTime(DateUtil.parseToDate(course.getCreateTimeQuery()));
        }
        List<Course> courses = courseMapper.selectList(new QueryWrapper<>(course));
        return courses;
    }

    @Override
    public Pager listByPage(Pager pager, Course course) {
        if(StringUtils.isNotBlank(course.getCreateTimeQuery())){
            course.setCreateTime(DateUtil.parseToDate(course.getCreateTimeQuery()));
        }
        IPage<Course> courseIPage = courseMapper.selectPage(new Page<>(pager.getNum(), pager.getSize()), new QueryWrapper<>(course));
        return pager.of(courseIPage);
    }

    @Override
    @Transactional
    public Course save(Course course) {
        if(Objects.nonNull(course.getId())){
            courseMapper.updateById(course);
        }else {
            courseMapper.insert(course);
        }
        return course;
    }

    @Override
    @Transactional
    public boolean delete(String ids) {
        List<Long> list = Lists.newArrayList();
        if(ids.indexOf(",") != -1){
            List<String> asList = Arrays.asList(ids.split(","));
            asList.stream().forEach(o -> list.add(Long.valueOf(o)));
        }else {
            list.add(Long.valueOf(ids));
        }
        int i = courseMapper.deleteBatchIds(list);
        return i > 0;
    }


}
