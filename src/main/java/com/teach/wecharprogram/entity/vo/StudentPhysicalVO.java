package com.teach.wecharprogram.entity.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/8/3 17:14
 */
@Data
@ApiModel(value = "评测信息 简单")
public class StudentPhysicalVO {

    @ApiModelProperty(value = "班级id", hidden = true)
    private Long classesId;

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

}
