package com.teach.wecharprogram.service.impl;

import com.teach.wecharprogram.common.CommonException;
import com.teach.wecharprogram.common.constant.CommonConstant;
import com.teach.wecharprogram.entity.RelUserTypeId;
import com.teach.wecharprogram.mapper.RelUserTypeidMapper;
import com.teach.wecharprogram.service.SchoolService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.School;
import com.teach.wecharprogram.mapper.SchoolMapper;
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
* @Date 2019-06-15 17:59:30
*/

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    SchoolMapper schoolMapper;

    @Autowired
    RelUserTypeidMapper relUserTypeidMapper;

    @Override
    public School getOne(Long id){
        return schoolMapper.selectById(id);
    }

    @Override
    public List<School> list(School school) {
        if(StringUtils.isNotBlank(school.getCreateTimeQuery())){
            school.setCreateTime(DateUtil.parseToDate(school.getCreateTimeQuery()));
        }
        List<School> schools = schoolMapper.selectList(new QueryWrapper<>(school));
        return schools;
    }

    @Override
    public Pager listByPage(Pager pager, School school) {
        if(StringUtils.isNotBlank(school.getCreateTimeQuery())){
            school.setCreateTime(DateUtil.parseToDate(school.getCreateTimeQuery()));
        }
        IPage<School> schoolIPage = schoolMapper.selectPage(new Page<>(pager.getNum(), pager.getSize()), new QueryWrapper<>(school));
        return Pager.of(schoolIPage);
    }

    @Override
    @Transactional(rollbackFor = CommonException.class)
    public School save(School school) {
        if(Objects.nonNull(school.getId())){
            schoolMapper.updateById(school);
        }else {
            schoolMapper.insert(school);
        }
        return school;
    }

    @Override
    @Transactional(rollbackFor = CommonException.class)
    public boolean delete(String ids) {
        List<Long> list = Lists.newArrayList();
        if(ids.indexOf(",") != -1){
            List<String> asList = Arrays.asList(ids.split(","));
            asList.stream().forEach(o -> list.add(Long.valueOf(o)));
        }else {
            list.add(Long.valueOf(ids));
        }
        int i = schoolMapper.deleteBatchIds(list);
        return i > 0;
    }

    @Override
    public Object relHeadMasterToSchool(Long userId, Long schoolId) {
        RelUserTypeId relUserTypeId = new RelUserTypeId(userId, null, CommonConstant.REL_SCHOOL);
        // 删除之前关联的
        relUserTypeidMapper.delete(new QueryWrapper<>(relUserTypeId));
        // 新增
        relUserTypeId.setOtherId(schoolId);
        relUserTypeidMapper.insert(relUserTypeId);
        return relUserTypeId;
    }


}
