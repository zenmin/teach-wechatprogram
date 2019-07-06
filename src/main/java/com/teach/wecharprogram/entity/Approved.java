package com.teach.wecharprogram.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @ApiModelProperty(value = "申请人名称",hidden = true)
    private String startUserName;

    @ApiModelProperty(value = "申请人id",hidden = true)
    private Long startUserId;

    @ApiModelProperty(value = "结果")
    private String result;

    @ApiModelProperty(value = "1通过 0不通过 2审批中",hidden = true)
    private Integer resultCode;

    @ApiModelProperty(value = "审批人名称",hidden = true)
    private String approvedUserName;

    @ApiModelProperty(value = "审批人id",hidden = true)
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

    @ApiModelProperty("班级名称 多个用,隔开")
    private String classesName;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("学生id")
    private String studentId;

    @ApiModelProperty("学生姓名")
    private String studentName;

    @ApiModelProperty("结束时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @ApiModelProperty("学校id")
    private String schoolId;

    @ApiModelProperty("学校名称")
    private String schoolName;

    @ApiModelProperty("用户详情")
    @Column(columnDefinition = "text COMMENT '用户详情'")
    private String userText;

    public Approved(String name, String startUserName, Long startUserId, String roleName, String roleId, String remark,
                    String opinion, Integer type, String classesId, String studentId, String result, Integer resultCode,
                    String phone, String realName,String classesName,String studentName) {
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
        this.phone = phone;
        this.realName = realName;
        this.classesName = classesName;
        this.studentName = studentName;
    }

    public Approved(Long startUserId, Integer resultCode) {
        this.startUserId = startUserId;
        this.resultCode = resultCode;
    }
}
