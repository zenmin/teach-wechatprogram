package com.teach.wecharprogram.entity.DO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/7/20 11:30
 */
@Data
public class CoursePlanCourseDo {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "课程id")
    private String courseId;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

}
