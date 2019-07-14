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
* @Author ZengMin
* @Date 2019-07-13 20:43:30
*/

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="特别关注")
@Table(name = "follow")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Follow extends EntityModel {

    @ApiModelProperty(value = "老师id",hidden = true)
    private Long uid;

    @ApiModelProperty(value = "被关注学生id")
    private Long studentId;

    @ApiModelProperty(value = "学生名称")
    @TableField(exist = false)
    @Transient
    private String studentName;

}
