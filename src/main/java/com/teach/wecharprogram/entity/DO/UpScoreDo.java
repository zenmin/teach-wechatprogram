package com.teach.wecharprogram.entity.DO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/7/29 22:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpScoreDo {

    @ApiModelProperty(value = "主键")
    private Long id;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "datetime default now() COMMENT '创建时间' ")
    @ApiModelProperty(hidden = true)
    private Date createTime;

    @ApiModelProperty(value = "学生id")
    private Long studentId;

    @ApiModelProperty(value = "学生姓名")
    private String studentName;

    @ApiModelProperty(value = "班级id")
    private Long classesId;

    @ApiModelProperty(value = "班级姓名")
    private String classesName;

    @ApiModelProperty(value = "分数和上次的分数差")
    private Double score;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}
