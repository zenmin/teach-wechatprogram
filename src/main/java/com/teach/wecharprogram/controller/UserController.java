package com.teach.wecharprogram.controller;

import com.teach.wecharprogram.common.CommonException;
import com.teach.wecharprogram.common.constant.CommonConstant;
import com.teach.wecharprogram.common.constant.DefinedCode;
import com.teach.wecharprogram.entity.vo.UpdateUserVo;
import com.teach.wecharprogram.util.StaticUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.teach.wecharprogram.common.ResponseEntity;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.User;
import com.teach.wecharprogram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;


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

    @Value("${spring.profiles.active}")
    String env;

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
    @ApiOperation(value = "更新用户信息/提交关联审批", response = ResponseEntity.class)
    @PostMapping("/updateUserData")
    public ResponseEntity updateUserData(UpdateUserVo updateUserVo, @ApiParam(hidden = true) @RequestHeader String token) {
        User loginUser = userService.getLoginUser(token);
        updateUserVo.setId(loginUser.getId());
        if (loginUser.getStatus() == CommonConstant.STATUS_ERROR) {
            StaticUtil.validateField(updateUserVo.getPhone(), updateUserVo.getRealName(), updateUserVo.getRoleId());
            StaticUtil.validateObject(updateUserVo.getType());
        } else {
            updateUserVo.setRealName(loginUser.getRealName());
        }
        return ResponseEntity.success(userService.updateUserData(updateUserVo, token));
    }

    /**
     * 带ID更新 不带ID新增
     *
     * @return
     */
    @ApiOperation(value = "获取当前登录用户信息", response = ResponseEntity.class)
    @PostMapping("/getMyInfo")
    public ResponseEntity getMyInfo(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        loginUser.setOpenid(null);
        loginUser.setUsername(null);
        loginUser.setPassword(null);
        return ResponseEntity.success(loginUser);
    }

    /**
     * 带ID更新 不带ID新增
     *
     * @return
     */
    @ApiOperation(value = "获取当前登录用户关联的学校 / 班级 / 学生", response = ResponseEntity.class)
    @PostMapping("/getMyRelInfo")
    public ResponseEntity getMyRelInfo(HttpServletRequest request) {
        User user = userService.getLoginUser(request);
        return ResponseEntity.success(userService.getMyRelInfo(user));
    }

    /**
     * 带ID更新 不带ID新增
     *
     * @return
     */
    @ApiOperation(value = "切换当前用户角色(开发环境)", response = ResponseEntity.class)
    @PostMapping("/selectRole")
    public ResponseEntity selectRole(Long roleId, HttpServletRequest request) {
        if ("dev".equals(env)) {
            User user = userService.getLoginUser(request);
            return ResponseEntity.success(userService.selectRole(roleId, user.getId()));
        } else {
            throw new CommonException(DefinedCode.NOTAUTH_OPTION, "非开发环境不允许切换角色！");
        }
    }

}