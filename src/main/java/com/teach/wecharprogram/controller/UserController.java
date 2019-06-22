package com.teach.wecharprogram.controller;

import com.teach.wecharprogram.entity.vo.UpdateUserVo;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.teach.wecharprogram.common.ResponseEntity;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.User;
import com.teach.wecharprogram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Create by Code Generator
 *
 * @Author ZengMin
 * @Date 2019-06-15 18:17:10
 */

@Api(tags = "用户管理")
@RestController
@RequestMapping("/api/user")
public class UserController {

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
        return ResponseEntity.success(userService.getOne(id));
    }

    /**
     * 查询全部 可带条件
     *
     * @param user
     * @return
     */
    @ApiOperation(value = "查询全部 可带条件", response = ResponseEntity.class)
    @PostMapping("/list")
    public ResponseEntity list(User user) {
        return ResponseEntity.success(userService.list(user));
    }

    /**
     * 查全部 可带条件分页
     *
     * @param pager
     * @param user
     * @return
     */
    @ApiOperation(value = "查全部 可带条件分页", response = ResponseEntity.class)
    @PostMapping("/listByPage")
    public ResponseEntity listByPage(Pager pager, User user) {
        return ResponseEntity.success(userService.listByPage(pager, user));
    }

    /**
     * 带ID更新 不带ID新增
     *
     * @param user
     * @return
     */
    @ApiOperation(value = "带ID更新 不带ID新增", response = ResponseEntity.class)
    @PostMapping("/save")
    public ResponseEntity saveOrUpdate(User user) {
        return ResponseEntity.success(userService.save(user));
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
        return ResponseEntity.success(userService.delete(ids));
    }

    /**
     * 带ID更新 不带ID新增
     *
     * @return
     */
    @ApiOperation(value = "更新用户信息/提交角色审批", response = ResponseEntity.class)
    @PostMapping("/updateUserData")
    public ResponseEntity updateUserData(UpdateUserVo updateUserVo, @ApiParam(hidden = true) @RequestHeader String token) {
        User loginUser = userService.getLoginUser(token);
        updateUserVo.setId(loginUser.getId());
        return ResponseEntity.success(userService.updateUserData(updateUserVo, token));
    }

    /**
     * 带ID更新 不带ID新增
     *
     * @return
     */
    @ApiOperation(value = "获取当前登录用户信息", response = ResponseEntity.class)
    @PostMapping("/getMyInfo")
    public ResponseEntity getMyInfo(@ApiParam(hidden = true) @RequestHeader String token) {
        User loginUser = userService.getLoginUser(token);
        loginUser.setOpenid(null);
        loginUser.setUsername(null);
        return ResponseEntity.success(loginUser);
    }

}