package com.teach.wecharprogram.controller;

import com.teach.wecharprogram.entity.DO.CoursePlanClassesDo;
import com.teach.wecharprogram.entity.DO.CoursePlanCourseDo;
import com.teach.wecharprogram.service.UserService;
import com.teach.wecharprogram.util.StaticUtil;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.teach.wecharprogram.common.ResponseEntity;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.CoursePlan;
import com.teach.wecharprogram.service.CoursePlanService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * Create by Code Generator
 *
 * @Author ZengMin
 * @Date 2019-07-15 20:58:15
 */

@Api(tags = "课程安排")
@RestController
@RequestMapping("/api/coursePlan")
public class CoursePlanController {

    @Autowired
    CoursePlanService coursePlanService;

    @Autowired
    UserService userService;


    /**
     * 查全部 可带条件分页
     *
     * @param pager
     * @param coursePlan
     * @return
     */
    @ApiOperation(value = "查全部 可带条件分页", response = ResponseEntity.class)
    @PostMapping("/listByPage")
    public ResponseEntity listByPage(Pager pager, CoursePlan coursePlan) {
        return ResponseEntity.success(coursePlanService.listByPage(pager, coursePlan));
    }


    /**
     * 带ID更新 不带ID新增
     *
     * @param coursePlan
     * @return
     */
    @ApiOperation(value = "1、安排课程", response = ResponseEntity.class)
    @PostMapping("/save")
    public ResponseEntity saveOrUpdate(@RequestBody List<CoursePlan> coursePlan) {
        return ResponseEntity.success(coursePlanService.save(coursePlan));
    }

    /**
     * 查全部 可带条件分页
     *
     * @param pager
     * @return
     */
    @ApiOperation(value = "2、查询已安排课程的班级", response = CoursePlanClassesDo.class)
    @PostMapping("/getMyPlan")
    public ResponseEntity getMyPlan(Pager pager) {
        return ResponseEntity.success(coursePlanService.getMyPlan(pager));
    }

    /**
     * 查全部 可带条件分页
     *
     * @param pager
     * @return
     */
    @ApiOperation(value = "3、查询该班级已安排的课程", response = CoursePlanCourseDo.class)
    @PostMapping("/getMyPlanByClassesId")
    public ResponseEntity getMyPlanByClassesId(Pager pager, Long classesId) {
        StaticUtil.validateObject(classesId);
        return ResponseEntity.success(coursePlanService.getMyPlanByClassesId(pager, classesId));
    }

    /**
     * 根据id删除   多个用,隔开
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除课程安排", response = ResponseEntity.class)
    @ApiImplicitParam(name = "ids", value = "主键 多个用,隔开", required = true)
    @PostMapping("/delete")
    public ResponseEntity delete(String ids) {
        return ResponseEntity.success(coursePlanService.delete(ids));
    }


}