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
import com.teach.wecharprogram.entity.StudentPhysical;
import com.teach.wecharprogram.service.StudentPhysicalService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Create by Code Generator
 *
 * @Author ZengMin
 * @Date 2019-07-15 20:15:32
 */

@Api(tags = "体适能及评测详情")
@RestController
@RequestMapping("/api/studentPhysical")
public class StudentPhysicalController {

    @Autowired
    StudentPhysicalService studentPhysicalService;

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
        return ResponseEntity.success(studentPhysicalService.getOne(id));
    }

    /**
     * 查全部 可带条件分页
     *
     * @param pager
     * @param studentPhysical
     * @return
     */
    @ApiOperation(value = "查全部 可带条件分页", response = ResponseEntity.class)
    @PostMapping("/listByPage")
    public ResponseEntity listByPage(Pager pager, StudentPhysical studentPhysical) {
        return ResponseEntity.success(studentPhysicalService.listByPage(pager, studentPhysical));
    }

    /**
     * 带ID更新 不带ID新增
     *
     * @param studentPhysical
     * @return
     */
    @ApiOperation(value = "带ID更新 不带ID新增", response = ResponseEntity.class)
    @PostMapping("/save")
    public ResponseEntity saveOrUpdate(StudentPhysical studentPhysical) {
        return ResponseEntity.success(studentPhysicalService.save(studentPhysical));
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
        return ResponseEntity.success(studentPhysicalService.delete(ids));
    }

    /**
     * 家长：表现五佳
     *
     * @return
     */
    @ApiOperation(value = "家长：表现五佳", response = ResponseEntity.class)
    @PostMapping("/topFive")
    @RequireRole({CommonConstant.ROLE_FAMILY})
    public ResponseEntity topFive() {
        User loginUser = userService.getLoginUser();
        return ResponseEntity.success(studentPhysicalService.topFive(loginUser));
    }

    /**
     * 家长：表现五佳
     *
     * @return
     */
    @ApiOperation(value = "家长：进步五佳", response = ResponseEntity.class)
    @PostMapping("/topUpFive")
    @RequireRole({CommonConstant.ROLE_FAMILY})
    public ResponseEntity topUpFive() {
        User loginUser = userService.getLoginUser();
        return ResponseEntity.success(studentPhysicalService.topUpFive(loginUser));
    }


}