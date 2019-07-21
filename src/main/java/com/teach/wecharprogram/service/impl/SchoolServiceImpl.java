package com.teach.wecharprogram.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.teach.wecharprogram.common.CommonException;
import com.teach.wecharprogram.common.constant.CommonConstant;
import com.teach.wecharprogram.entity.Classes;
import com.teach.wecharprogram.entity.RelUserTypeId;
import com.teach.wecharprogram.entity.Student;
import com.teach.wecharprogram.mapper.ClassesMapper;
import com.teach.wecharprogram.mapper.RelUserTypeidMapper;
import com.teach.wecharprogram.mapper.StudentMapper;
import com.teach.wecharprogram.service.SchoolService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.School;
import com.teach.wecharprogram.mapper.SchoolMapper;
import com.teach.wecharprogram.util.StaticUtil;
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
 * @Date 2019-06-15 17:59:30
 */

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    SchoolMapper schoolMapper;

    @Autowired
    RelUserTypeidMapper relUserTypeidMapper;

    @Autowired
    ClassesMapper classesMapper;

    @Autowired
    StudentMapper studentMapper;

    @Override
    public School getOne(Long id) {
        return schoolMapper.selectById(id);
    }

    @Override
    public List<School> list(School school) {
        if (StringUtils.isNotBlank(school.getCreateTimeQuery())) {
            school.setCreateTime(DateUtil.parseToDate(school.getCreateTimeQuery()));
        }
        List<School> schools = schoolMapper.selectList(new QueryWrapper<>(school));
        return schools;
    }

    @Override
    public Pager listByPage(Pager pager, School school) {
        QueryWrapper<School> schoolQueryWrapper = new QueryWrapper<>(school);
        String name = school.getName();
        if (StringUtils.isNotBlank(name)) {
            schoolQueryWrapper.like("name", name);
        }
        IPage<School> schoolIPage = schoolMapper.selectPage(new Page<>(pager.getNum(), pager.getSize()), schoolQueryWrapper);
        return Pager.of(schoolIPage);
    }

    @Override
    @Transactional(rollbackFor = CommonException.class)
    public School save(School school) {
        if (Objects.nonNull(school.getId())) {
            School one = this.getOne(school.getId());
            // 判断是否修改了名称
            schoolMapper.updateById(school);
            if (!one.getName().equals(school.getName())) {
                // 更新所有班级的学校名称
                Classes classes = new Classes();
                classes.setName(school.getName());
                classesMapper.update(classes, new UpdateWrapper<Classes>().eq("schoolId", one.getId()));
            }
        } else {
            StaticUtil.validateField(school.getName());
            if (Objects.isNull(school.getStatus())) {
                school.setStatus(CommonConstant.STATUS_OK);
            }
            schoolMapper.insert(school);
        }
        return school;
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
        int i = schoolMapper.deleteBatchIds(list);
        // 设置班级下的学生未分配班级
        List<Classes> classesList = classesMapper.selectList(new QueryWrapper<Classes>().in("schoolId", ids));
        if (classesList.size() > 0) {
            List<Long> cids = classesList.stream().map(Classes::getId).collect(Collectors.toList());
            studentMapper.updateClassIdNull(StaticUtil.joinQuota(cids));
            // 删除学校对应班级
            classesMapper.deleteBatchIds(cids);
        }
        return i > 0;
    }

    @Override
    public Object relHeadMasterToSchool(Long userId, String schoolId) {
        // 删之前的关联
        relUserTypeidMapper.delete(new UpdateWrapper<>(new RelUserTypeId(userId, null, CommonConstant.REL_SCHOOL)));
        String[] split = schoolId.split(",");
        List<String> asList = Arrays.asList(split);
        asList.stream().forEach(o -> {
            RelUserTypeId relUserTypeId = new RelUserTypeId(userId, Long.valueOf(o), CommonConstant.REL_SCHOOL);
            relUserTypeidMapper.insert(relUserTypeId);
        });
        List<RelUserTypeId> relUserTypeIds = relUserTypeidMapper.selectList(new QueryWrapper<RelUserTypeId>().eq("userId", userId).eq("type", CommonConstant.REL_SCHOOL));
        return relUserTypeIds;
    }


}
