package com.teach.wecharprogram.entity;

import com.teach.wecharprogram.entity.base.EntityModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Create by Code Generator
 * JPA只用来正向生成数据库表和字段 如果不需要此字段更新 请加上注解@TableField(exist = false)和@Transient
 *
 * @Author ZengMin
 * @Date 2019-06-22 16:36:34
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "角色审批")
@Table(name = "approved")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Approved extends EntityModel {

    @ApiModelProperty(value = "审批名称")
    private String name;

    @ApiModelProperty(value = "创建人名称")
    private String startUserName;

    @ApiModelProperty(value = "创建人id")
    private Long startUserId;

    @ApiModelProperty(value = "结果")
    private String result;

    @ApiModelProperty(value = "1通过 0不通过 2审批中")
    private Integer resultCode;

    @ApiModelProperty(value = "审批人名称")
    private String approvedUserName;

    @ApiModelProperty(value = "审批人id")
    private Long approvedUserId;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "摘要")
    private String remark;

    @ApiModelProperty(value = "审批意见")
    private String opinion;

    @ApiModelProperty("类型 1教师 2教练 3家长")
    private Integer type;

    @ApiModelProperty("班级id 多个用,隔开")
    private String classesId;

    @ApiModelProperty("学生id")
    private String studentId;

    @ApiModelProperty("结束时间")
    private Date endTime;

    public Approved(String name, String startUserName, Long startUserId, String roleName, String roleId, String remark, String opinion, Integer type, String classesId, String studentId, String result, Integer resultCode) {
        this.name = name;
        this.startUserName = startUserName;
        this.startUserId = startUserId;
        this.roleName = roleName;
        this.roleId = roleId;
        this.remark = remark;
        this.opinion = opinion;
        this.type = type;
        this.classesId = classesId;
        this.studentId = studentId;
        this.result = result;
        this.resultCode = resultCode;
    }
}
