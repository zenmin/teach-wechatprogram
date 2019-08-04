package com.teach.wecharprogram.entity.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/8/3 17:14
 */
@Data
@ApiModel(value = "评测信息 建议")
public class StudentPhysicalTextVO {

    @ApiModelProperty(value = "学生姓名", hidden = true)
    @Excel(name = "姓名")
    private String studentName;

    @ApiModelProperty(value = "BMI指数")
    @Excel(name = "BMI指数")
    private Double bmi;

    @ApiModelProperty(value = "总分")
    @Excel(name = "总分")
    private Double allScore;

    @ApiModelProperty(value = "评测时间", hidden = true)
    @Excel(name = "评测时间")
    private String date;

    @ApiModelProperty(value = "总体评估建议")
    @Column(columnDefinition = "varchar(1000) COMMENT '总体评估建议'")
    @Excel(name = "总体评估建议", width = 16)
    private String allRemark;

    @ApiModelProperty(value = "评测人姓名", hidden = true)
    @Excel(name = "评测人姓名")
    private String createUserName;

}
