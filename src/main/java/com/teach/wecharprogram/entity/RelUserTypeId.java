package com.teach.wecharprogram.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
 * @Date 2019-06-22 17:57:58
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "用户关联的学校班级学生")
@Table(name = "rel_user_typeid")
@TableName("rel_user_typeid")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RelUserTypeId extends EntityModel {

    @ApiModelProperty(value = "按创建时间查询 格式：yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    @Transient
    private String createTimeQuery;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "学校 班级 学生id")
    private Long otherId;

    @ApiModelProperty(value = "1学校 2班级 3学生")
    private Integer type;

    public RelUserTypeId(Long userId, Long otherId, Integer type) {
        this.userId = userId;
        this.otherId = otherId;
        this.type = type;
    }
}
