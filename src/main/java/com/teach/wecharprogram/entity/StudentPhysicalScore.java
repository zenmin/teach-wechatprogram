package com.teach.wecharprogram.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.teach.wecharprogram.entity.base.EntityModel;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Create by Code Generator
 * JPA只用来正向生成数据库表和字段 如果不需要此字段更新 请加上注解@TableField(exist = false)和@Transient
 *
 * @Author ZengMin
 * @Date 2019-07-15 20:38:53
 */

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("student_physical_score")
@ApiModel(value = "体适能评分")
@Table(name = "student_physical_score")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class StudentPhysicalScore extends EntityModel {

    @ApiModelProperty(value = "评测id")
    private Long physicalId;

    @ApiModelProperty(value = "灵敏")
    private Double sensitiveScore;

    @ApiModelProperty(value = "速度")
    private Double speedScore;

    @ApiModelProperty(value = "协调")
    private Double concertScore;

    @ApiModelProperty(value = "柔韧")
    private Double pliableScore;

    @ApiModelProperty(value = "平衡")
    private Double balanceScore;

    @ApiModelProperty(value = "力量")
    private Double powerScore;

    @ApiModelProperty(value = "总分")
    private Double allScore;

    @ApiModelProperty(value = "次数")
    private Integer timesNum;

    @ApiModelProperty(value = "评估指示")
    @Column(columnDefinition = "varchar(1000) COMMENT '评估指示'")
    private String remark;

    @ApiModelProperty(value = "更新时间", hidden = true)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
