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
* @Date 2019-06-15 18:17:01
*/

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="学生管理")
@Table(name = "student")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Student extends EntityModel {

    @ApiModelProperty(value = "按创建时间查询 格式：yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    @Transient
    private String createTimeQuery;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "性别")
    private Integer gender;

    @ApiModelProperty(value = "班级Id")
    private String classesId;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "头像")
    private String imgUrl;

    @ApiModelProperty(value = "状态1正常 2退学")
    private Integer status;

    @ApiModelProperty(value = "出生日期")
    private String birthday;


}
