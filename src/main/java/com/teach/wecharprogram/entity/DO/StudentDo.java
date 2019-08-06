package com.teach.wecharprogram.entity.DO;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/7/14 11:36
 */
@Data
@ApiModel(value = "学生详细", description = "允许导出的字段：no,name,gender,remark,status,birthday,classesName,schoolName,age")
@ExcelTarget("studentMsgEntity")
@JsonInclude(JsonInclude.Include.ALWAYS)
public class StudentDo {

    @ApiModelProperty(value = "数据id", hidden = true)
    private String id;

    @ApiModelProperty(value = "名字")
    @Excel(name = "学号")
    private Integer no;

    @ApiModelProperty(value = "名字")
    @Excel(name = "姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    @Excel(name = "性别", replace = {"男_1", "女_0"})
    private Integer gender;

    @ApiModelProperty(value = "备注", hidden = true)
    @Excel(name = "备注", orderNum = "6")
    private String remark;

    @ApiModelProperty(value = "头像", hidden = true)
    private String imgUrl;

    @ApiModelProperty(value = "状态1正常 2退学")
    @Excel(name = "状态", replace = {"正常_1", "退学_2"})
    private Integer status;

    @ApiModelProperty(value = "出生日期")
    @Excel(name = "出生日期")
    private String birthday;

    @ApiModelProperty(value = "班级Id")
    private Long classesId;

    @ApiModelProperty(value = "班级名称", hidden = true)
    @Excel(name = "所在班级")
    private String classesName;

    @ApiModelProperty(value = "学校Id")
    private Long schoolId;

    @ApiModelProperty(value = "学校名称", hidden = true)
    @Excel(name = "所在学校")
    private String schoolName;

    @ApiModelProperty(value = "年龄", hidden = true)
    @Excel(name = "年龄")
    private Integer age;

}
