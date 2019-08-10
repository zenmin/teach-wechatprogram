package com.teach.wecharprogram.entity;

import com.teach.wecharprogram.entity.base.EntityModel;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Create by Code Generator
 * JPA只用来正向生成数据库表和字段 如果不需要此字段更新 请加上注解@TableField(exist = false)和@Transient
 *
 * @Author ZengMin
 * @Date 2019-07-15 20:58:15
 */

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("course_plan")
@ApiModel(value = "课程安排")
@Table(name = "course_plan")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CoursePlan extends EntityModel {

    @ApiModelProperty(value = "用户id", hidden = true)
    private Long uid;

    @ApiModelProperty(value = "班级id")
    private Long classesId;

    @ApiModelProperty(value = "课程id")
    private Long courseId;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "星期几")
    private Integer day;

}
