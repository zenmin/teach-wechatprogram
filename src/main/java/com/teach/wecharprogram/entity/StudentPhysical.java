package com.teach.wecharprogram.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.teach.wecharprogram.entity.base.EntityModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/7/15 20:03
 */
@Data
@Entity
@Table(name = "student_physical")
@EqualsAndHashCode(callSuper = true)
@TableName("student_physical")
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("体适能详情")
public class StudentPhysical extends EntityModel {

    @ApiModelProperty(value = "学生id")
    private Long studentId;

    @ApiModelProperty(value = "身高")
    private Double height;

    @ApiModelProperty(value = "体重")
    private Double weight;

    @ApiModelProperty(value = "BMI指数")
    private Double bmi;

    @ApiModelProperty(value = "足内外翻、扁平足")
    private String downValgus;

    @ApiModelProperty(value = "八字脚")
    private String downEightFoot;

    @ApiModelProperty(value = "X型、O型腿")
    private String downXo;

    @ApiModelProperty(value = "膝关节超伸")
    private String downOverextension;

    @ApiModelProperty(value = "长短腿")
    private String downLongLegs;

    @ApiModelProperty(value = "骨盆回旋")
    private String downPelvicGyration;

    @ApiModelProperty(value = "骨盆前后倾")
    private String downPelvicAnter;

    @ApiModelProperty(value = "骨盆左右高低")
    private String downLeftPelvic;

    @ApiModelProperty(value = "脊柱侧弯")
    private String upScoliosis;

    @ApiModelProperty(value = "胸椎、腰椎侧曲")
    private String upThoracic;

    @ApiModelProperty(value = "翼状肩胛")
    private String upWingedScapula;

    @ApiModelProperty(value = "高低肩、耸肩、塌肩")
    private String upHighLowShoulders;

    @ApiModelProperty(value = "圆肩、含胸")
    private String upRoundShoulders;

    @ApiModelProperty(value = "颈椎前引、后缩")
    private String upAnteriorCervical;

    @ApiModelProperty(value = "嘴角高低是否一致")
    private String upIsCorners;

    @ApiModelProperty(value = "颈椎侧曲、侧旋")
    private String upLateralCurvature;

    @ApiModelProperty(value = "评测时间", hidden = true)
    private String date;

    @ApiModelProperty(value = "评测人id", hidden = true)
    private Long createUid;

    @ApiModelProperty(value = "教师姓名", hidden = true)
    private String createUserName;

    @ApiModelProperty(value = "更新人id", hidden = true)
    private Long updateUid;

    @ApiModelProperty(value = "教师姓名", hidden = true)
    private String updateUserName;

    @ApiModelProperty(value = "更新时间", hidden = true)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
