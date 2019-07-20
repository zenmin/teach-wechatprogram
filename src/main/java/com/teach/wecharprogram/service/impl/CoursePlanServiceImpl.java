package com.teach.wecharprogram.service.impl;

import com.teach.wecharprogram.entity.Classes;
import com.teach.wecharprogram.entity.DO.CoursePlanClassesDo;
import com.teach.wecharprogram.entity.DO.CoursePlanCourseDo;
import com.teach.wecharprogram.service.CoursePlanService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.CoursePlan;
import com.teach.wecharprogram.mapper.CoursePlanMapper;
import com.teach.wecharprogram.service.UserService;
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
 *
 * @Author ZengMin
 * @Date 2019-07-15 20:58:15
 */

@Service
public class CoursePlanServiceImpl implements CoursePlanService {

    @Autowired
    CoursePlanMapper coursePlanMapper;

    @Autowired
    UserService userService;

    @Override
    public CoursePlan getOne(Long id) {
        return coursePlanMapper.selectById(id);
    }

    @Override
    public List<CoursePlan> list(CoursePlan coursePlan) {
        List<CoursePlan> coursePlans = coursePlanMapper.selectList(new QueryWrapper<>(coursePlan));
        return coursePlans;
    }

    @Override
    public Pager listByPage(Pager pager, CoursePlan coursePlan) {
        IPage<CoursePlan> coursePlanIPage = coursePlanMapper.selectPage(new Page<>(pager.getNum(), pager.getSize()), new QueryWrapper<>(coursePlan));
        return Pager.of(coursePlanIPage);
    }

    @Override
    @Transactional
    public CoursePlan save(CoursePlan coursePlan) {
        if (Objects.nonNull(coursePlan.getId())) {
            coursePlanMapper.updateById(coursePlan);
        } else {
            coursePlanMapper.insert(coursePlan);
        }
        return coursePlan;
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
        int i = coursePlanMapper.deleteBatchIds(list);
        return i > 0;
    }

    @Override
    public Pager getMyPlan(Pager pager) {
        IPage<CoursePlanClassesDo> byUid = coursePlanMapper.findByUid(new Page(pager.getNum(), pager.getSize()), userService.getLoginUser().getId());
        return Pager.of(byUid);
    }

    @Override
    public Pager getMyPlanByClassesId(Pager pager, Long classesId) {
        IPage<CoursePlanCourseDo> myPlanByClassesId = coursePlanMapper.getMyPlanByClassesId(new Page(pager.getNum(), pager.getSize()), classesId, userService.getLoginUser().getId());
        return Pager.of(myPlanByClassesId);
    }


}