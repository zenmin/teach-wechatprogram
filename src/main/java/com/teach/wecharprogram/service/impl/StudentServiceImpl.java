package com.teach.wecharprogram.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.teach.wecharprogram.common.CommonException;
import com.teach.wecharprogram.common.constant.CommonConstant;
import com.teach.wecharprogram.common.constant.DefinedCode;
import com.teach.wecharprogram.entity.*;
import com.teach.wecharprogram.entity.DO.StudentDo;
import com.teach.wecharprogram.mapper.*;
import com.teach.wecharprogram.service.ClassesService;
import com.teach.wecharprogram.service.StudentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.util.StaticUtil;
import org.apache.commons.lang3.StringUtils;
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

    // 是否已有学生
    static boolean hasStudent = false;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    RelUserTypeidMapper relUserTypeidMapper;

    @Autowired
    StudentPhysicalMapper studentPhysicalMapper;

    @Autowired
    ClassesService classesService;

    @Autowired
    UpScoreMapper upScoreMapper;

    @Autowired
    StudentAssessMapper studentAssessMapper;

    @Autowired
    FollowMapper followMapper;

    @Override
    public Student getOne(Long id) {
        return studentMapper.selectById(id);
    }

    @Override
    public List<Student> list(Student student) {
        List<Student> students = studentMapper.selectList(new QueryWrapper<>(student).orderByDesc("no"));
        return students;
    }

    @Override
    public Pager listByPage(Pager pager, Student student) {
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>(student);
        String name = student.getName();
        if (StringUtils.isNotBlank(name)) {
            student.setName(null);
            studentQueryWrapper.like("name", name);
        }
        studentQueryWrapper.orderByDesc("no");
        IPage<Student> studentIPage = studentMapper.selectPage(new Page<>(pager.getNum(), pager.getSize()), studentQueryWrapper);
        return Pager.of(studentIPage);
    }

    @Override
    @Transactional(rollbackFor = CommonException.class)
    public Student save(Student student) {
        if (Objects.nonNull(student.getId())) {
            student.setNo(null);    // 学号不更改
            studentMapper.updateById(student);
            Student one = this.getOne(student.getId());
            if (Objects.nonNull(one)) {
                if(StringUtils.isNotBlank(student.getName())) {
                    if (!one.getName().equals(student.getName())) {
                        // 更新评测表
                        StudentPhysical studentPhysical = new StudentPhysical();
                        studentPhysical.setStudentName(student.getName());
                        studentPhysicalMapper.update(studentPhysical, new UpdateWrapper<StudentPhysical>().eq("studentId", student.getId()));
                    }
                }
            }
        } else {
            if (!hasStudent) {
                // 查询数据库有无学生 没有设置一个默认学号
                Integer count = studentMapper.selectCount(null);
                if (count <= 0) {
                    student.setNo(100000);  // 初始学号
                    hasStudent = true;
                }
            }
            if (Objects.isNull(student.getClassesId())) {
                throw new CommonException(DefinedCode.PARAMSERROR, "请选择班级，再添加学生！");
            }
            Classes one = classesService.getOne(student.getClassesId());
            student.setClassesName(one.getName());
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
        // 删除学生对应的评测
        StaticUtil.executorService.execute(() -> {
            // 评测表
            studentPhysicalMapper.delete(new QueryWrapper<StudentPhysical>().in("studentId", list));
            // 分数进步表
            upScoreMapper.delete(new QueryWrapper<UpScore>().in("studentId", list));
            // 每日评议
            studentAssessMapper.delete(new QueryWrapper<StudentAssess>().in("studentId", list));
            // 关联表
            relUserTypeidMapper.delete(new QueryWrapper<RelUserTypeId>().in("otherId", list));
            // 关注表
            followMapper.delete(new QueryWrapper<Follow>().in("studentId", list));
        });
        return i > 0;
    }

    @Override
    public Object relFamilyToStudent(Long userId, String studentsId) {
        // 删之前的关联
        relUserTypeidMapper.delete(new UpdateWrapper<>(new RelUserTypeId(userId, null, CommonConstant.REL_STUDENTS)));
        String[] split = studentsId.split(",");
        List<String> asList = Arrays.asList(split);
        asList.stream().forEach(o -> {
            RelUserTypeId relUserTypeId = new RelUserTypeId(userId, Long.valueOf(o), CommonConstant.REL_STUDENTS);
            relUserTypeidMapper.insert(relUserTypeId);
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

    @Override
    public Object getStudentsBySchool(Pager pager, Long schoolId, Integer status) {
        IPage<Student> studentIPage = studentMapper.getStudentsBySchool(new Page<>(pager.getNum(), pager.getSize()), schoolId, status);
        return Pager.of(studentIPage);
    }

    /**
     * @param student
     * @return
     */
    @Override
    public List<StudentDo> getStudentAllMsg(StudentDo student) {
        List<StudentDo> studentAllMsg = studentMapper.getStudentAllMsg(student.getName(), student.getGender(), student.getStatus(), student.getBirthday(), student.getClassesId(), student.getSchoolId());
        return studentAllMsg;
    }


}
