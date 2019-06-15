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
* @Date 2019-06-15 18:37:02
*/

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="")
@Table(name = "course")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Course extends EntityModel {

    @ApiModelProperty(value = "按创建时间查询 格式：yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    @Transient
    private String createTimeQuery;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "课程类型")
    private String type;

    @ApiModelProperty(value = "课程类型编码")
    private Integer typeCode;

    @ApiModelProperty(value = "老师id")
    private Long teacherId;

    @ApiModelProperty(value = "老师名称")
    private String teacherName;

    @ApiModelProperty(value = "班级id")
    private Long classesId;


}
