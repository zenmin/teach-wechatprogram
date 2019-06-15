package com.teach.wecharprogram.controller;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.teach.wecharprogram.common.ResponseEntity;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.Student;
import com.teach.wecharprogram.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;


/**
* Create by Code Generator
* @Author ZengMin
* @Date 2019-06-15 18:17:01
*/

@Api(tags = "学生管理")
@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    /**
     * 根据id查询一条数据
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询一条数据", response = ResponseEntity.class)
    @ApiImplicitParam(name = "id",value = "主键",required = true)
    @PostMapping("/getOne")
    public ResponseEntity getOne(Long id){
        return ResponseEntity.success(studentService.getOne(id));
    }

    /**
     * 查询全部 可带条件
     * @param student
     * @return
     */
    @ApiOperation(value = "查询全部 可带条件", response = ResponseEntity.class)
    @PostMapping("/list")
    public ResponseEntity list(Student student){
        return ResponseEntity.success(studentService.list(student));
    }

    /**
     * 查全部 可带条件分页
     * @param pager
     * @param student
     * @return
     */
    @ApiOperation(value = "查全部 可带条件分页", response = ResponseEntity.class)
    @PostMapping("/listByPage")
    public ResponseEntity listByPage(Pager pager,Student student){
        return ResponseEntity.success(studentService.listByPage(pager,student));
    }

    /**
     * 带ID更新 不带ID新增
     * @param student
     * @return
     */
    @ApiOperation(value = "带ID更新 不带ID新增", response = ResponseEntity.class)
    @PostMapping("/save")
    public ResponseEntity saveOrUpdate(Student student){
        return ResponseEntity.success(studentService.save(student));
    }

    /**
     * 根据id删除   多个用,隔开
     * @param ids
     * @return
     */
    @ApiOperation(value = "根据id删除 多个用,隔开", response = ResponseEntity.class)
    @ApiImplicitParam(name = "ids",value = "主键 多个用,隔开",required = true)
    @PostMapping("/delete")
    public ResponseEntity delete(String ids){
        return ResponseEntity.success(studentService.delete(ids));
    }


}