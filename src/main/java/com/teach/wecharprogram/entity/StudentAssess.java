package com.teach.wecharprogram.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.teach.wecharprogram.entity.base.EntityModel;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Create by Code Generator
 * JPA只用来正向生成数据库表和字段 如果不需要此字段更新 请加上注解@TableField(exist = false)和@Transient
 *
 * @Author ZengMin
 * @Date 2019-07-20 12:21:11
 */

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("student_assess")
@ApiModel(value = "每日评议")
@Table(name = "student_assess")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class StudentAssess extends EntityModel {

    @ApiModelProperty(value = "学生id", hidden = true)
    private Long studentId;

    @ApiModelProperty(value = "教师id", hidden = true)
    private Long uid;

    @ApiModelProperty(value = "评价")
    private String text;

    @ApiModelProperty(value = "评价时间", hidden = true)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "datetime default now() COMMENT '评价时间' ")
    private Date updateTime;

    @ApiModelProperty(value = "评价时间", hidden = true)
    private String date;

    @TableField(exist = false)
    @Transient
    @ApiModelProperty(value = "学生姓名", hidden = true)
    private String studentName;

    @ApiModelProperty(value = "学生ids")
    @TableField(exist = false)
    @Transient
    private String studentIds;

    public StudentAssess(Long studentId) {
        this.studentId = studentId;
    }
}
