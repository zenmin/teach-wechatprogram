package com.teach.wecharprogram.service.impl;

import com.teach.wecharprogram.entity.User;
import com.teach.wecharprogram.service.StudentPhysicalService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.StudentPhysical;
import com.teach.wecharprogram.mapper.StudentPhysicalMapper;
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
* @Author ZengMin
* @Date 2019-07-15 20:15:32
*/

@Service
public class StudentPhysicalServiceImpl implements StudentPhysicalService {

    @Autowired
    StudentPhysicalMapper studentPhysicalMapper;

    @Autowired
    UserService userService;

    @Override
    public StudentPhysical getOne(Long id){
        return studentPhysicalMapper.selectById(id);
    }

    @Override
    public List<StudentPhysical> list(StudentPhysical studentPhysical) {
        List<StudentPhysical> studentPhysicals = studentPhysicalMapper.selectList(new QueryWrapper<>(studentPhysical));
        return studentPhysicals;
    }

    @Override
    public Pager listByPage(Pager pager, StudentPhysical studentPhysical) {
        IPage<StudentPhysical> studentPhysicalIPage = studentPhysicalMapper.selectPage(new Page<>(pager.getNum(), pager.getSize()), new QueryWrapper<>(studentPhysical));
        return Pager.of(studentPhysicalIPage);
    }

    @Override
    @Transactional
    public StudentPhysical save(StudentPhysical studentPhysical) {
        User loginUser = userService.getLoginUser();
        Long id = loginUser.getId();
        String realName = loginUser.getRealName();
        studentPhysical.setUpdateTime(new Date());
        if(Objects.nonNull(studentPhysical.getId())){
            studentPhysical.setUpdateUid(id);
            studentPhysical.setUpdateUserName(realName);
            studentPhysicalMapper.updateById(studentPhysical);
        }else {
            studentPhysical.setCreateUid(id);
            studentPhysical.setCreateUserName(realName);
            studentPhysical.setDate(DateUtil.getNowDate());
            studentPhysicalMapper.insert(studentPhysical);
        }
        return studentPhysical;
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
        int i = studentPhysicalMapper.deleteBatchIds(list);
        return i > 0;
    }


}
