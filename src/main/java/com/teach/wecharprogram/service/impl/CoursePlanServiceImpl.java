package com.teach.wecharprogram.service.impl;

import com.google.common.collect.ImmutableMap;
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

import java.util.*;
import java.util.stream.Collectors;


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
    public List<CoursePlan> save(List<CoursePlan> coursePlan) {
        coursePlan.stream().forEach(o -> {
            if (Objects.nonNull(o.getId())) {
                coursePlanMapper.updateById(o);
            } else {
                o.setUid(userService.getLoginUser().getId());
                // 查询当前班级当前时间 当前星期 有没有数据
                CoursePlan one = coursePlanMapper.selectOne(new QueryWrapper<CoursePlan>().eq("classesId", o.getClassesId()).eq("startTime", o.getStartTime()).eq("endTime", o.getEndTime()).eq("day", o.getDay()));
                if (Objects.isNull(one)) {
                    coursePlanMapper.insert(o);
                } else {
                    o.setId(one.getId());
                    coursePlanMapper.updateById(o);
                }
            }
        });
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
    public Object getMyPlanByClassesId(Long classesId, Boolean isGroup) {
        List<CoursePlanCourseDo> myPlanByClassesId = coursePlanMapper.getMyPlanByClassesId(classesId, userService.getLoginUser().getId());
        List<Map> groupMap = Lists.newArrayList();
        if (Objects.nonNull(isGroup) && isGroup) {
            Map<Integer, List<CoursePlanCourseDo>> collect = myPlanByClassesId.stream().collect(Collectors.groupingBy(CoursePlanCourseDo::getDay));
            Set<Map.Entry<Integer, List<CoursePlanCourseDo>>> entries = collect.entrySet();
            for (Map.Entry<Integer, List<CoursePlanCourseDo>> m : entries) {
                groupMap.add(ImmutableMap.of("day", m.getKey(), "values", m.getValue()));
            }
            return groupMap;
        }
        return myPlanByClassesId;
    }


}
