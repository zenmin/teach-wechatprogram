package com.teach.wecharprogram.entity.DO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/7/20 11:30
 */
@Data
public class CoursePlanClassesDo {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "班级id")
    private String classesId;

    @ApiModelProperty(value = "课程id")
    private String courseId;

    @ApiModelProperty(value = "班级名称")
    private String classesName;

    @ApiModelProperty(value = "学校id")
    private String schoolId;

    @ApiModelProperty(value = "学校名称")
    private String schoolName;

    @ApiModelProperty(value = "星期")
    private Integer day;


}
