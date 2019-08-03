package com.teach.wecharprogram.entity;

import com.teach.wecharprogram.entity.base.EntityModel;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.util.Date;

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
 * @Date 2019-07-29 22:14:39
 */

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("up_score")
@ApiModel(value = "分数进步记录表")
@Table(name = "up_score")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UpScore extends EntityModel {

    @ApiModelProperty(value = "评测时间")
    private String date;

    @ApiModelProperty(value = "学生id")
    private Long studentId;

    @ApiModelProperty(value = "班级id")
    private Long classesId;

    @ApiModelProperty(value = "分数和上次的分数差")
    private Double score;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


}
