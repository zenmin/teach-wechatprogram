package com.teach.wecharprogram.controller;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.teach.wecharprogram.common.ResponseEntity;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.Logs;
import com.teach.wecharprogram.service.LogsService;
import org.springframework.beans.factory.annotation.Autowired;


/**
* Create by Code Generator
* @Author ZengMin
* @Date 2019-06-15 17:29:59
*/

@Api(tags = "全局请求日志")
@RestController
@RequestMapping("/api/logs")
public class LogsController {

    @Autowired
    LogsService logsService;

    /**
     * 根据id查询一条数据
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询一条数据", response = ResponseEntity.class)
    @ApiImplicitParam(name = "id",value = "主键",required = true)
    @PostMapping("/getOne")
    public ResponseEntity getOne(Long id){
        return ResponseEntity.success(logsService.getOne(id));
    }

    /**
     * 查询全部 可带条件
     * @param logs
     * @return
     */
    @ApiOperation(value = "查询全部 可带条件", response = ResponseEntity.class)
    @PostMapping("/list")
    public ResponseEntity list(Logs logs){
        return ResponseEntity.success(logsService.list(logs));
    }

    /**
     * 查全部 可带条件分页
     * @param pager
     * @param logs
     * @return
     */
    @ApiOperation(value = "查全部 可带条件分页", response = ResponseEntity.class)
    @PostMapping("/listByPage")
    public ResponseEntity listByPage(Pager pager,Logs logs){
        return ResponseEntity.success(logsService.listByPage(pager,logs));
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
        return ResponseEntity.success(logsService.delete(ids));
    }


}