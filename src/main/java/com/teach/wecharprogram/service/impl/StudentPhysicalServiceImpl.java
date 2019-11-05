package com.teach.wecharprogram.service.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.teach.wecharprogram.common.CommonException;
import com.teach.wecharprogram.common.constant.DefinedCode;
import com.teach.wecharprogram.entity.*;
import com.teach.wecharprogram.entity.DO.StudentDo;
import com.teach.wecharprogram.entity.DO.StudentPhysicalDo;
import com.teach.wecharprogram.entity.DO.UpScoreDo;
import com.teach.wecharprogram.entity.vo.StudentPhysicalTextVO;
import com.teach.wecharprogram.entity.vo.StudentPhysicalVO;
import com.teach.wecharprogram.mapper.ClassesMapper;
import com.teach.wecharprogram.mapper.UpScoreMapper;
import com.teach.wecharprogram.repostory.StudentPhysicalRepository;
import com.teach.wecharprogram.service.StudentPhysicalService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.mapper.StudentPhysicalMapper;
import com.teach.wecharprogram.service.StudentService;
import com.teach.wecharprogram.service.UpScoreService;
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

    @Autowired
    UpScoreService upScoreService;

    @Autowired
    ClassesMapper classesMapper;

    @Autowired
    StudentPhysicalRepository studentPhysicalRepository;

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
    public Pager listByPage(Pager pager, StudentPhysicalDo studentPhysical) {
        Pager<StudentPhysicalDo> studentPhysicalIPage = studentPhysicalRepository.selectPage(pager, studentPhysical);
        return studentPhysicalIPage;
    }

    @Override
    @Transactional
    public StudentPhysical save(StudentPhysical studentPhysical) {
        User loginUser = userService.getLoginUser();
        Long id = loginUser.getId();
        String realName = loginUser.getRealName();
        studentPhysical.setUpdateTime(new Date());
        String date = studentPhysical.getDate();
        if (Objects.nonNull(studentPhysical.getId())) {
            studentPhysical.setCreateUid(null);
            studentPhysical.setCreateUserName(null);
            studentPhysical.setDate(null);
            // 更新主表
            studentPhysicalMapper.updateById(studentPhysical);
            //更新分数
            // 查本次评测是否存在分数进步记录表
            UpScore upScore = upScoreMapper.selectOne(new QueryWrapper<UpScore>().eq("physicalId", studentPhysical.getId()));
            if (Objects.isNull(upScore)) {
                upScore = upScoreMapper.selectOne(new QueryWrapper<UpScore>().eq("studentId", studentPhysical.getStudentId()).eq("date", date));
            }
            if (Objects.nonNull(upScore)) {
                Double lastScore = upScore.getLastScore();
                if (Objects.isNull(lastScore)) {
                    lastScore = 0d;
                }
                Double score = StaticUtil.subtract(studentPhysical.getAllScore(), lastScore);
                if (!score.equals(upScore.getScore())) {
                    upScore.setScore(score);
                    upScoreService.save(upScore);
                }
            }
        } else {
            // 查询学生所在班级
            Student one = studentService.getOne(studentPhysical.getStudentId());
            studentPhysical.setClassesId(one.getClassesId());
            studentPhysical.setClassesName(one.getClassesName());
            studentPhysical.setNo(one.getNo());
            studentPhysical.setCreateUid(id);
            studentPhysical.setCreateUserName(realName);
            studentPhysical.setDate(DateUtil.getNowDate());
            studentPhysical.setStudentName(one.getName());
            studentPhysicalMapper.insert(studentPhysical);
            // 先查询上一次的分数
            List<StudentPhysical> oneByStudent = this.getOneByStudent(studentPhysical.getStudentId(), date, true);
            // 计算本次分数和上次分数之差
            Double changeScore = 0d;
            Double lastScore = 0d;
            if (Objects.nonNull(oneByStudent) && oneByStudent.size() > 0) {
                lastScore = oneByStudent.get(0).getAllScore();
                changeScore = StaticUtil.subtract(studentPhysical.getAllScore(), Objects.nonNull(lastScore) ? lastScore : 0d);
            }
            upScoreService.save(new UpScore(DateUtil.getNowDate(), studentPhysical.getStudentId(), studentPhysical.getClassesId(),
                    changeScore, new Date(), lastScore, studentPhysical.getId()));
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
        List<StudentDo> students = (List<StudentDo>) myRelInfo;
        students.stream().forEach(o -> {
            classNameMap.put(o.getClassesId(), o.getClassesName());
        });
        // 取班级id
        Set<Long> classesId = students.stream().map(StudentDo::getClassesId).collect(Collectors.toSet());
        // 每个班级五条
        List<StudentPhysicalVO> studentPhysicals = studentPhysicalMapper.selectToFive(classesId.size() * 3, classesId);
        // 分组
        Map<Long, List<StudentPhysicalVO>> group = studentPhysicals.stream().collect(Collectors.groupingBy(StudentPhysicalVO::getClassesId));
        Set<Map.Entry<Long, List<StudentPhysicalVO>>> entries = group.entrySet();
        for (Map.Entry<Long, List<StudentPhysicalVO>> m : entries) {
            List<StudentPhysicalVO> value = m.getValue();
            if (value.size() > 3) {
                value = value.subList(0, 3);
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
        List<StudentDo> students = (List<StudentDo>) myRelInfo;
        students.stream().forEach(o -> {
            classNameMap.put(o.getClassesId(), o.getClassesName());
        });
        // 取班级id
        Set<Long> classesId = students.stream().map(StudentDo::getClassesId).collect(Collectors.toSet());
        List<UpScoreDo> records = upScoreMapper.selectTopUpFive(classesId, classesId.size() * 3);
        // 分组
        Map<Long, List<UpScoreDo>> group = records.stream().collect(Collectors.groupingBy(UpScoreDo::getClassesId));
        Set<Map.Entry<Long, List<UpScoreDo>>> entries = group.entrySet();
        for (Map.Entry<Long, List<UpScoreDo>> m : entries) {
            List<UpScoreDo> value = m.getValue();
            if (value.size() > 3) {
                value = value.subList(0, 3);
            }
            result.add(ImmutableMap.of("classesId", m.getKey(), "classesName", classNameMap.get(m.getKey()), "values", value));
        }
        return result;
    }

    @Override
    public List<StudentPhysical> getOneByStudent(Long studentId, String date, Boolean queryNow) {
        List<StudentPhysical> records = null;
        if (queryNow) {
            StudentPhysical oneByStudent = studentPhysicalMapper.getOneByStudent(studentId, date);
            records = Objects.nonNull(oneByStudent) ? Lists.newArrayList(oneByStudent) : null;
        } else {
            records = studentPhysicalMapper.selectList(new QueryWrapper<StudentPhysical>().eq("studentId", studentId).orderByDesc("date"));
        }
        return records;
    }

    @Override
    public List<StudentPhysicalTextVO> getPhysicalByStudentIds(String studentIds) {
        List<StudentPhysicalTextVO> list = Lists.newArrayList();
        List<String> ids = Arrays.asList(studentIds.split(","));
        ids.stream().forEach(o -> {
            list.add(studentPhysicalMapper.getPhysicalByStudentIds(Long.valueOf(o)));
        });
        return list;
    }


}
