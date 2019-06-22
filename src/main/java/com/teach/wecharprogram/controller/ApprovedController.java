package com.teach.wecharprogram.controller;

import com.teach.wecharprogram.entity.User;
import com.teach.wecharprogram.entity.vo.ApprovedVo;
import com.teach.wecharprogram.service.UserService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.teach.wecharprogram.common.ResponseEntity;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.Approved;
import com.teach.wecharprogram.service.ApprovedService;
import org.springframework.beans.factory.annotation.Autowired;


/**
* Create by Code Generator
* @Author ZengMin
* @Date 2019-06-22 16:36:34
*/

@Api(tags = "角色审批")
@RestController
@RequestMapping("/api/approved")
public class ApprovedController {

    @Autowired
    ApprovedService approvedService;

    @Autowired
    UserService userService;

    /**
     * 根据id查询一条数据
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询一条数据", response = ResponseEntity.class)
    @ApiImplicitParam(name = "id",value = "主键",required = true)
    @PostMapping("/getOne")
    public ResponseEntity getOne(Long id){
        return ResponseEntity.success(approvedService.getOne(id));
    }

    /**
     * 查询全部 可带条件
     * @param approved
     * @return
     */
    @ApiOperation(value = "查询全部 可带条件", response = ResponseEntity.class)
    @PostMapping("/list")
    public ResponseEntity list(Approved approved){
        return ResponseEntity.success(approvedService.list(approved));
    }

    /**
     * 查全部 可带条件分页
     * @param pager
     * @param approved
     * @return
     */
    @ApiOperation(value = "查全部 可带条件分页", response = ResponseEntity.class)
    @PostMapping("/listByPage")
    public ResponseEntity listByPage(Pager pager,Approved approved){
        return ResponseEntity.success(approvedService.listByPage(pager,approved));
    }

    /**
     * 带ID更新 不带ID新增
     * @param approved
     * @return
     */
    @ApiOperation(value = "带ID更新 不带ID新增", response = ResponseEntity.class)
    @PostMapping("/save")
    public ResponseEntity saveOrUpdate(Approved approved){
        return ResponseEntity.success(approvedService.save(approved));
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
        return ResponseEntity.success(approvedService.delete(ids));
    }

    /**
     * 同意审批
     * @return
     */
    @ApiOperation(value = "同意审批", response = ResponseEntity.class)
    @PostMapping("/agree")
    public ResponseEntity approve(ApprovedVo approvedVo,@ApiParam(hidden = true) @RequestHeader String token){
        User loginUser = userService.getLoginUser(token);
        approvedVo.setApprovedUserName(loginUser.getRealName());
        approvedVo.setApprovedUserId(loginUser.getId());
        return ResponseEntity.success(approvedService.agree(approvedVo));
    }

}