package com.teach.wecharprogram.service.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.teach.wecharprogram.common.CommonException;
import com.teach.wecharprogram.common.constant.DefinedCode;
import com.teach.wecharprogram.entity.DO.UpScoreDo;
import com.teach.wecharprogram.entity.Student;
import com.teach.wecharprogram.entity.UpScore;
import com.teach.wecharprogram.entity.User;
import com.teach.wecharprogram.mapper.UpScoreMapper;
import com.teach.wecharprogram.service.StudentPhysicalService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.StudentPhysical;
import com.teach.wecharprogram.mapper.StudentPhysicalMapper;
import com.teach.wecharprogram.service.StudentService;
import com.teach.wecharprogram.service.UserService;
import com.teach.wecharprogram.util.StaticUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.teach.wecharprogram.util.DateUtil;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Create Code Generator
 *
 * @Author ZengMin
 * @Date 2019-07-15 20:15:32
 */

@Service
public class StudentPhysicalServiceImpl implements StudentPhysicalService {

    @Autowired
    StudentPhysicalMapper studentPhysicalMapper;

    @Autowired
    UserService userService;

    @Autowired
    StudentService studentService;

    @Autowired
    UpScoreMapper upScoreMapper;


    @Override
    public StudentPhysical getOne(Long id) {
        return studentPhysicalMapper.selectById(id);
    }

    @Override
    public List<StudentPhysical> list(StudentPhysical studentPhysical) {
        List<StudentPhysical> studentPhysicals = studentPhysicalMapper.selectList(new QueryWrapper<>(studentPhysical));
        return studentPhysicals;
    }

    @Override
    public Pager listByPage(Pager pager, StudentPhysical studentPhysical) {
        IPage<StudentPhysical> studentPhysicalIPage = studentPhysicalMapper.selectPage(new Page<>(pager.getNum(), pager.getSize()), new QueryWrapper<>(studentPhysical).orderByDesc("createTime"));
        return Pager.of(studentPhysicalIPage);
    }

    @Override
    @Transactional
    public StudentPhysical save(StudentPhysical studentPhysical) {
        User loginUser = userService.getLoginUser();
        Long id = loginUser.getId();
        String realName = loginUser.getRealName();
        studentPhysical.setUpdateTime(new Date());
        if (Objects.nonNull(studentPhysical.getId())) {
            studentPhysicalMapper.updateById(studentPhysical);
        } else {
            // 查询学生所在班级
            Student one = studentService.getOne(studentPhysical.getStudentId());
            studentPhysical.setClassesId(one.getClassesId());
            studentPhysical.setCreateUid(id);
            studentPhysical.setCreateUserName(realName);
            studentPhysical.setDate(DateUtil.getNowDate());
            studentPhysical.setStudentName(one.getName());
            studentPhysicalMapper.insert(studentPhysical);
        }
        return studentPhysical;
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
        int i = studentPhysicalMapper.deleteBatchIds(list);
        return i > 0;
    }

    @Override
    public List<Map> topFive(User user) {
        List<Map> result = Lists.newArrayList();
        // 查询家长关联孩子
        Object myRelInfo = userService.getMyRelInfo(user);
        if (Objects.isNull(myRelInfo)) {
            throw new CommonException(DefinedCode.NOTFOUND, "你没有关联的孩子，请先关联孩子！");
        }

        // 缓存班级名称
        Map<Long, String> classNameMap = Maps.newHashMap();
        List<Student> students = (List<Student>) myRelInfo;
        students.stream().forEach(o -> {
            classNameMap.put(o.getClassesId(), o.getClassesName());
        });
        // 取班级id
        Set<Long> classesId = students.stream().map(Student::getClassesId).collect(Collectors.toSet());
        // 每个班级五条
        List<StudentPhysical> studentPhysicals = studentPhysicalMapper.selectPage(new Page<>(0, classesId.size() * 5), new QueryWrapper<StudentPhysical>().in("classesId", classesId).orderByDesc("allScore", "date")).getRecords();
        // 分组
        Map<Long, List<StudentPhysical>> group = studentPhysicals.stream().collect(Collectors.groupingBy(StudentPhysical::getClassesId));
        Set<Map.Entry<Long, List<StudentPhysical>>> entries = group.entrySet();
        for (Map.Entry<Long, List<StudentPhysical>> m : entries) {
            List<StudentPhysical> value = m.getValue();
            if (value.size() > 5) {
                value = value.subList(0, 5);
            }
            result.add(ImmutableMap.of("classesId", m.getKey(), "classesName", classNameMap.get(m.getKey()), "values", value));
        }
        return result;
    }

    @Override
    public List<Map> topUpFive(User loginUser) {
        List<Map> result = Lists.newArrayList();
        // 查询家长关联孩子
        Object myRelInfo = userService.getMyRelInfo(loginUser);
        if (Objects.isNull(myRelInfo)) {
            throw new CommonException(DefinedCode.NOTFOUND, "你没有关联的孩子，请先关联孩子！");
        }
        // 缓存班级名称
        Map<Long, String> classNameMap = Maps.newHashMap();
        List<Student> students = (List<Student>) myRelInfo;
        students.stream().forEach(o -> {
            classNameMap.put(o.getClassesId(), o.getClassesName());
        });
        // 取班级id
        Set<Long> classesId = students.stream().map(Student::getClassesId).collect(Collectors.toSet());
        List<UpScoreDo> records = upScoreMapper.selectTopUpFive(classesId, classesId.size() * 5);
        // 分组
        Map<Long, List<UpScoreDo>> group = records.stream().collect(Collectors.groupingBy(UpScoreDo::getClassesId));
        Set<Map.Entry<Long, List<UpScoreDo>>> entries = group.entrySet();
        for (Map.Entry<Long, List<UpScoreDo>> m : entries) {
            List<UpScoreDo> value = m.getValue();
            if (value.size() > 5) {
                value = value.subList(0, 5);
            }
            result.add(ImmutableMap.of("classesId", m.getKey(), "classesName", classNameMap.get(m.getKey()), "values", value));
        }
        return result;
    }

    @Override
    public List<StudentPhysical> getOneByStudent(Long studentId, Boolean queryNow) {
        List<StudentPhysical> records = null;
        if (queryNow) {
            records = studentPhysicalMapper.selectPage(new Page<>(0, 1), new QueryWrapper<StudentPhysical>().eq("studentId", studentId).orderByDesc("date")).getRecords();
        } else {
            records = studentPhysicalMapper.selectList(new QueryWrapper<StudentPhysical>().eq("studentId", studentId).orderByDesc("date"));
        }
        return records;
    }


}
