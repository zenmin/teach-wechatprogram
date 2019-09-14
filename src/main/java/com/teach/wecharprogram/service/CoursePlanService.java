package com.teach.wecharprogram.service;

import com.teach.wecharprogram.entity.DO.CoursePlanCourseDo;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.CoursePlan;
import java.util.List;
import java.util.Map;

/**
* Create by Code Generator
* @Author ZengMin
* @Date 2019-07-15 20:58:15
*/

public interface CoursePlanService {

    CoursePlan getOne(Long id);

    List<CoursePlan> list(CoursePlan coursePlan);

    Pager listByPage(Pager pager,CoursePlan coursePlan);

    List<CoursePlan> save(List<CoursePlan> coursePlan);

    boolean delete(String ids);

    List<Map> getMyPlan(Pager pager);

    Object getMyPlanByClassesId(Long classesId, Boolean isGroup, Integer day);

    Object getMyPlanDay();
}
