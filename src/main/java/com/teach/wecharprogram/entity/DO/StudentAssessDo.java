package com.teach.wecharprogram.entity.DO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/7/14 11:36
 */
@Data
public class StudentAssessDo {

    @ApiModelProperty(value = "学生id")
    private String studentId;

    @ApiModelProperty(value = "评议id")
    private String assessId;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "性别")
    private Integer gender;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "头像")
    private String imgUrl;

    @ApiModelProperty(value = "状态1正常 2退学")
    private Integer status;

    @ApiModelProperty(value = "出生日期")
    private String birthday;

    @ApiModelProperty(value = "班级Id")
    private Long classesId;

    @ApiModelProperty(value = "班级名称")
    private String classesName;

    @ApiModelProperty(value = "学校Id")
    private Long schoolId;

    @ApiModelProperty(value = "学校名称")
    private String schoolName;

    @ApiModelProperty(value = "更新时间")
    @Column(columnDefinition = "datetime default now() COMMENT '创建时间' ")
    private Date updateTime;

    @ApiModelProperty(value = "评价")
    private String text;
}


