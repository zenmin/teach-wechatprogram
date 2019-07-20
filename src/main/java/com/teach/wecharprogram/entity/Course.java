package com.teach.wecharprogram.entity;

import com.teach.wecharprogram.entity.base.EntityModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Create by Code Generator
 * JPA只用来正向生成数据库表和字段 如果不需要此字段更新 请加上注解@TableField(exist = false)和@Transient
 *
 * @Author ZengMin
 * @Date 2019-07-20 11:50:30
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "课程管理")
@Table(name = "course")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Course extends EntityModel {

    @ApiModelProperty(value = "课程名称")
    private String name;

    @ApiModelProperty(value = "运动对象")
    private String suitObject;

    @ApiModelProperty(value = "学生人数")
    private Integer sportNum;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "运动单元")
    private String sportUnit;

    @ApiModelProperty(value = "使用器械")
    private String useTool;

    @ApiModelProperty(value = "辅助器材")
    private String assistTool;

    @ApiModelProperty(value = "体能因素培养")
    private String skillFactor;

    @ApiModelProperty(value = "心理培养目标")
    private String psychicFactor;

    @ApiModelProperty(value = "准备部分")
    private String contentReady;

    @ApiModelProperty(value = "基本部分")
    private String contentBasic;

    @ApiModelProperty(value = "结束部分")
    private String contentEnd;

    @ApiModelProperty(value = "评价")
    private String evaluate;

    @ApiModelProperty(value = "用户id")
    private Long uid;

}
