package com.teach.wecharprogram.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
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
 * @Date 2019-06-15 18:17:01
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "学生管理")
@Table(name = "student")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ExcelTarget("studentEntity")
public class Student extends EntityModel {

    @ApiModelProperty(value = "名字")
    @Excel(name = "姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    @Excel(name = "性别", replace = {"男_1", "女_0"})
    private Integer gender;

    @ApiModelProperty(value = "备注")
    @Excel(name = "备注", orderNum = "6")
    private String remark;

    @ApiModelProperty(value = "头像")
    private String imgUrl;

    @ApiModelProperty(value = "状态1正常 2退学")
    @Excel(name = "状态", replace = {"正常_1", "退学_2"})
    private Integer status;

    @ApiModelProperty(value = "出生日期")
    @Excel(name = "出生日期")
    private String birthday;

    @ApiModelProperty(value = "班级Id")
    private Long classesId;

    @ApiModelProperty(value = "班级名称")
    @Excel(name = "所在班级")
    private String classesName;

    @ApiModelProperty(value = "排序")
    private Integer sort;

}
