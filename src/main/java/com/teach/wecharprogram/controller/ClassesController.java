package com.teach.wecharprogram.controller;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.teach.wecharprogram.common.ResponseEntity;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.Classes;
import com.teach.wecharprogram.service.ClassesService;
import org.springframework.beans.factory.annotation.Autowired;


/**
* Create by Code Generator
* @Author ZengMin
* @Date 2019-06-15 17:59:22
*/

@Api(tags = "班级管理")
@RestController
@RequestMapping("/api/classes")
public class ClassesController {

    @Autowired
    ClassesService classesService;

    /**
     * 根据id查询一条数据
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询一条数据", response = ResponseEntity.class)
    @ApiImplicitParam(name = "id",value = "主键",required = true)
    @PostMapping("/getOne")
    public ResponseEntity getOne(Long id){
        return ResponseEntity.success(classesService.getOne(id));
    }

    /**
     * 查询全部 可带条件
     * @param classes
     * @return
     */
    @ApiOperation(value = "查询全部 可带条件", response = ResponseEntity.class)
    @PostMapping("/list")
    public ResponseEntity list(Classes classes){
        return ResponseEntity.success(classesService.list(classes));
    }

    /**
     * 查全部 可带条件分页
     * @param pager
     * @param classes
     * @return
     */
    @ApiOperation(value = "查全部 可带条件分页", response = ResponseEntity.class)
    @PostMapping("/listByPage")
    public ResponseEntity listByPage(Pager pager,Classes classes){
        return ResponseEntity.success(classesService.listByPage(pager,classes));
    }

    /**
     * 带ID更新 不带ID新增
     * @param classes
     * @return
     */
    @ApiOperation(value = "带ID更新 不带ID新增", response = ResponseEntity.class)
    @PostMapping("/save")
    public ResponseEntity saveOrUpdate(Classes classes){
        return ResponseEntity.success(classesService.save(classes));
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
        return ResponseEntity.success(classesService.delete(ids));
    }


}