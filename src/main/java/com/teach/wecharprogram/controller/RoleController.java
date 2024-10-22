package com.teach.wecharprogram.controller;

import com.teach.wecharprogram.common.constant.CommonConstant;
import com.teach.wecharprogram.components.annotation.RequireRole;
import com.teach.wecharprogram.entity.User;
import com.teach.wecharprogram.service.UserService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.teach.wecharprogram.common.ResponseEntity;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.Role;
import com.teach.wecharprogram.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Create by Code Generator
 *
 * @Author ZengMin
 * @Date 2019-06-15 17:46:36
 */

@Api(tags = "角色管理")
@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    RoleService roleService;

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
        return ResponseEntity.success(roleService.getOne(id));
    }

    /**
     * 查询全部 可带条件
     *
     * @param role
     * @return
     */
    @ApiOperation(value = "查询全部 可带条件", response = ResponseEntity.class)
    @PostMapping("/list")
    public ResponseEntity list(Role role, @ApiParam(hidden = true) @RequestHeader String token) {
        User loginUser = userService.getLoginUser();
        return ResponseEntity.success(roleService.list(loginUser, role));
    }

    /**
     * 查全部 可带条件分页
     *
     * @param pager
     * @param role
     * @return
     */
    @ApiOperation(value = "查全部 可带条件分页", response = ResponseEntity.class)
    @PostMapping("/listByPage")
    @RequireRole({CommonConstant.ROLE_HEADMASTER})
    public ResponseEntity listByPage(Pager pager, Role role) {
        return ResponseEntity.success(roleService.listByPage(pager, role));
    }

    /**
     * 带ID更新 不带ID新增
     *
     * @param role
     * @return
     */
    @ApiOperation(value = "带ID更新 不带ID新增", response = ResponseEntity.class)
    @PostMapping("/save")
    public ResponseEntity saveOrUpdate(Role role) {
        return ResponseEntity.success(roleService.save(role));
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
        return ResponseEntity.success(roleService.delete(ids));
    }


}