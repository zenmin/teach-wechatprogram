package com.teach.wecharprogram.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/6/22 16:53
 */
@Data
@ApiModel("更新用户信息")
public class UpdateUserVo {

    @ApiModelProperty(hidden = true)
    private Long id;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("验证码 开发环境默认123456")
    private String code;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("角色id")
    private String roleId;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("说明")
    private String remark;

    @ApiModelProperty("类型 1教师 2教练 3家长")
    private Integer type;

    @ApiModelProperty("班级id 多个用，隔开")
    private String classesId;

    @ApiModelProperty("班级名称")
    private String classesName;

    @ApiModelProperty("学生id")
    private String studentId;

    @ApiModelProperty("学生姓名")
    private String studentName;

}
