package com.teach.wecharprogram.controller;

import com.teach.wecharprogram.service.UserService;
import com.teach.wecharprogram.util.StaticUtil;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.teach.wecharprogram.common.ResponseEntity;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.StudentAssess;
import com.teach.wecharprogram.service.StudentAssessService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Create by Code Generator
 *
 * @Author ZengMin
 * @Date 2019-07-20 12:21:11
 */

@Api(tags = "每日评议")
@RestController
@RequestMapping("/api/studentAssess")
public class StudentAssessController {

    @Autowired
    StudentAssessService studentAssessService;

    @Autowired
    UserService userService;

    /**
     * 查全部 可带条件分页
     *
     * @param pager
     * @param studentAssess
     * @return
     */
    @ApiOperation(value = "查全部 可带条件分页", response = ResponseEntity.class)
    @PostMapping("/listByPage")
    public ResponseEntity listByPage(Pager pager, StudentAssess studentAssess) {
        return ResponseEntity.success(studentAssessService.listByPage(pager, studentAssess));
    }

    /**
     * 带ID更新 不带ID新增
     *
     * @param studentAssess
     * @return
     */
    @ApiOperation(value = "1、新增/更新评议", response = ResponseEntity.class)
    @PostMapping("/save")
    public ResponseEntity saveOrUpdate(StudentAssess studentAssess) {
        return ResponseEntity.success(studentAssessService.save(studentAssess));
    }

    /**
     * 根据学生id查询评议
     *
     * @param studentId
     * @return
     */
    @ApiOperation(value = "2、根据学生id查询评议", response = ResponseEntity.class)
    @PostMapping("/getAssess")
    public ResponseEntity getAssess(Long studentId) {
        return ResponseEntity.success(studentAssessService.selectOne(studentId));
    }

    /**
     * 家长方：查询当前关联孩子的评议
     *
     * @return
     */
    @ApiOperation(value = "3、家长方：查询当前关联孩子的评议", response = ResponseEntity.class)
    @PostMapping("/getAssessByUser")
    public ResponseEntity getAssessByUser() {
        return ResponseEntity.success(studentAssessService.getAssessByUser());
    }

    /**
     * 根据学生id查询评议
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "4、删除评议", response = ResponseEntity.class)
    @ApiImplicitParam(value = "评议的ids", name = "ids", required = true)
    @PostMapping("/delete")
    public ResponseEntity getAssess(String ids) {
        StaticUtil.validateField(ids);
        return ResponseEntity.success(studentAssessService.delete(ids));
    }


}