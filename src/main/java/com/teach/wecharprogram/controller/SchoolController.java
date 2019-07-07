package com.teach.wecharprogram.controller;

import com.teach.wecharprogram.entity.User;
import com.teach.wecharprogram.service.UserService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.teach.wecharprogram.common.ResponseEntity;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.School;
import com.teach.wecharprogram.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;


/**
 * Create by Code Generator
 *
 * @Author ZengMin
 * @Date 2019-06-15 17:59:30
 */

@Api(tags = "学校管理")
@RestController
@RequestMapping("/api/school")
public class SchoolController {

    @Autowired
    SchoolService schoolService;

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
        return ResponseEntity.success(schoolService.getOne(id));
    }

    /**
     * 查全部 可带条件分页
     *
     * @param pager
     * @param school
     * @return
     */
    @ApiOperation(value = "查全部 可带条件分页", response = ResponseEntity.class)
    @PostMapping("/listByPage")
    public ResponseEntity listByPage(Pager pager, School school) {
        return ResponseEntity.success(schoolService.listByPage(pager, school));
    }

    /**
     * 带ID更新 不带ID新增
     *
     * @param school
     * @return
     */
    @ApiOperation(value = "带ID更新 不带ID新增", response = ResponseEntity.class)
    @PostMapping("/save")
    public ResponseEntity saveOrUpdate(School school) {
        return ResponseEntity.success(schoolService.save(school));
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
        return ResponseEntity.success(schoolService.delete(ids));
    }


    /**
     * 校长关联学校
     *
     * @return
     */
    @ApiOperation(value = "校长/教练 关联学校", response = ResponseEntity.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "schoolId", value = "学校id 多个用，隔开", required = true),
            @ApiImplicitParam(name = "userId", value = "用户id（本人操作可不传）")})
    @PostMapping("/relToSchool")
    public ResponseEntity relHeadMasterToSchool(Long userId, String schoolId, HttpServletRequest request) {
        if (Objects.isNull(userId)) {
            User loginUser = userService.getLoginUser(request);
            userId = loginUser.getId();
        }
        return ResponseEntity.success(schoolService.relHeadMasterToSchool(userId, schoolId));
    }

}