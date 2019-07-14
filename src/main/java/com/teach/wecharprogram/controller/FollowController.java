package com.teach.wecharprogram.controller;

import com.teach.wecharprogram.entity.DO.StudentDo;
import com.teach.wecharprogram.service.UserService;
import com.teach.wecharprogram.util.StaticUtil;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.teach.wecharprogram.common.ResponseEntity;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.Follow;
import com.teach.wecharprogram.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;


/**
 * Create by Code Generator
 *
 * @Author ZengMin
 * @Date 2019-07-13 20:43:30
 */

@Api(tags = "特别关注")
@RestController
@RequestMapping("/api/follow")
public class FollowController {

    @Autowired
    FollowService followService;

    @Autowired
    UserService userService;

    /**
     * 带ID更新 不带ID新增
     *
     * @param follow
     * @return
     */
    @ApiOperation(value = "添加我的关注", response = ResponseEntity.class)
    @PostMapping("/save")
    public ResponseEntity saveOrUpdate(Follow follow) {
        StaticUtil.validateObject(follow.getStudentId());
        follow.setUid(userService.getLoginUser("").getId());
        return ResponseEntity.success(followService.save(follow));
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
        return ResponseEntity.success(followService.delete(ids));
    }


    /**
     * 获取我的关注列表
     *
     * @return
     */
    @ApiOperation(value = "获取我的关注列表", notes = "返回参数：3 4 5 6岁  以及大于6岁", response = StudentDo.class)
    @PostMapping("/getMyFollow")
    public ResponseEntity getMyFollow(Follow follow) {
        follow.setUid(userService.getLoginUser("").getId());
        return ResponseEntity.success(followService.getMyFollow(follow));
    }


}