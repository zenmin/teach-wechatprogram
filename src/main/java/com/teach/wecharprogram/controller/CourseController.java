package com.teach.wecharprogram.controller;

import com.teach.wecharprogram.service.UserService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.teach.wecharprogram.common.ResponseEntity;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.Course;
import com.teach.wecharprogram.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Create by Code Generator
 *
 * @Author ZengMin
 * @Date 2019-07-20 11:50:30
 */

@Api(tags = "课程管理")
@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    /**
     * 根据id查询一条数据
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询一条数据", response = ResponseEntity.class)
    @ApiImplicitParam(name = "id", value = "主键", required = true)
    @PostMapping("/getOne")
    public ResponseEntity getOne(Long id) {
        return ResponseEntity.success(courseService.getOne(id));
    }

    /**
     * 查全部 可带条件分页
     *
     * @param pager
     * @param course
     * @return
     */
    @ApiOperation(value = "查全部 可带条件分页", response = ResponseEntity.class)
    @PostMapping("/listByPage")
    public ResponseEntity listByPage(Pager pager, Course course) {
        return ResponseEntity.success(courseService.listByPage(pager, course));
    }

    /**
     * 带ID更新 不带ID新增
     *
     * @param course
     * @return
     */
    @ApiOperation(value = "带ID更新 不带ID新增", response = ResponseEntity.class)
    @PostMapping("/save")
    public ResponseEntity saveOrUpdate(Course course) {
        course.setUid(userService.getLoginUser().getId());
        return ResponseEntity.success(courseService.save(course));
    }

    /**
     * 根据id删除   多个用,隔开
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "根据id删除 多个用,隔开", response = ResponseEntity.class)
    @ApiImplicitParam(name = "ids", value = "主键 多个用,隔开", required = true)
    @PostMapping("/delete")
    public ResponseEntity delete(String ids) {
        return ResponseEntity.success(courseService.delete(ids));
    }

    /**
     * 带ID更新 不带ID新增
     *
     * @param course
     * @return
     */
    @ApiOperation(value = "查询我添加的课程", response = ResponseEntity.class)
    @PostMapping("/getMyAddCourse")
    public ResponseEntity getMyCourse(Pager pager, Course course) {
        course.setUid(userService.getLoginUser().getId());
        return ResponseEntity.success(courseService.listByPage(pager, course));
    }


}