package com.teach.wecharprogram.controller;

import com.teach.wecharprogram.common.constant.CommonConstant;
import com.teach.wecharprogram.components.annotation.RequireRole;
import com.teach.wecharprogram.entity.Course;
import com.teach.wecharprogram.entity.DO.CoursePlanClassesDo;
import com.teach.wecharprogram.entity.DO.CoursePlanCourseDo;
import com.teach.wecharprogram.service.CourseService;
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

    @Autowired
    CourseService courseService;


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
     * @param course
     * @return
     */
    @ApiOperation(value = "0、查询我添加的课程", response = ResponseEntity.class)
    @RequestMapping("/getMyCourse")
    public ResponseEntity getMyCourse(Pager pager, Course course) {
        course.setUid(userService.getLoginUser().getId());
        return ResponseEntity.success(courseService.listByPage(pager, course));
    }


    /**
     * 带ID更新 不带ID新增
     *
     * @param coursePlan
     * @return
     */
    @ApiOperation(value = "1、安排课程", response = ResponseEntity.class)
    @PostMapping("/save")
    @RequireRole({CommonConstant.ROLE_TRAIN, CommonConstant.ROLE_ADMIN})
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
    @RequireRole({CommonConstant.ROLE_TRAIN})
    public ResponseEntity getMyPlan(Pager pager) {
        return ResponseEntity.success(coursePlanService.getMyPlan(pager));
    }

    /**
     * 查全部 可带条件分页
     *
     * @return
     */
    @ApiOperation(value = "2、查询已安排课程的班级(按星期分组)", response = CoursePlanClassesDo.class)
    @PostMapping("/getMyPlanDay")
    @RequireRole({CommonConstant.ROLE_TRAIN})
    public ResponseEntity getMyPlanDay() {
        return ResponseEntity.success(coursePlanService.getMyPlanDay());
    }


    /**
     * 查全部 可带条件分页
     *
     * @return
     */
    @ApiOperation(value = "3、查询该班级已安排的课程", response = CoursePlanCourseDo.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "classesId", value = "班级id", required = true),
            @ApiImplicitParam(name = "isGroup", value = "是否按星期几分组 默认false", paramType = "boolean"), @ApiImplicitParam(name = "day", value = "星期", paramType = "integer")})
    @PostMapping("/getMyPlanByClassesId")
    public ResponseEntity getMyPlanByClassesId(Long classesId, Boolean isGroup, Integer day) {
        StaticUtil.validateObject(classesId);
        return ResponseEntity.success(coursePlanService.getMyPlanByClassesId(classesId, isGroup, day));
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
    @RequireRole({CommonConstant.ROLE_TRAIN, CommonConstant.ROLE_ADMIN})
    public ResponseEntity delete(String ids) {
        return ResponseEntity.success(coursePlanService.delete(ids));
    }


}