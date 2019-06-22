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
 * @Date 2019-06-15 17:59:22
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "班级管理")
@Table(name = "classes")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Classes extends EntityModel {

    @ApiModelProperty(value = "按创建时间查询 格式：yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    @Transient
    private String createTimeQuery;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "学校名称  冗余")
    private String schoolName;

    @ApiModelProperty(value = "所属学校id")
    private Long schoolId;

    @ApiModelProperty(value = "状态1启用 0禁用")
    private Integer status;

}
