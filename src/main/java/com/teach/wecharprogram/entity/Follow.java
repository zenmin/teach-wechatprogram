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
* @Date 2019-06-15 18:37:25
*/

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="特别关注")
@Table(name = "follow")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Follow extends EntityModel {

    @ApiModelProperty(value = "按创建时间查询 格式：yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    @Transient
    private String createTimeQuery;

    @ApiModelProperty(value = "特别关注类型")
    private String type;

    @ApiModelProperty(value = "编码1学生 2年龄")
    private Integer typeCode;

    @ApiModelProperty(value = "老师id")
    private Long teacherId;

    @ApiModelProperty(value = "被关注学生id")
    private Long studentId;

    @ApiModelProperty(value = "关注的年龄，这里放出生日期")
    private String followBirthday;


}
