package com.teach.wecharprogram.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.teach.wecharprogram.entity.base.EntityModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
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
@ApiModel(value = "体适能详情/评测详情", description = "允许导出的字段：studentName,height,weight,bmi,downValgus,downEightFoot,downXo,downOverextension,downLongLegs,downPelvicGyration,downPelvicAnter,downLeftPelvic,upScoliosis,upThoracic,upWingedScapula,upHighLowShoulders,upRoundShoulders,upAnteriorCervical,upIsCorners,upLateralCurvature,sensitiveScore,sensitiveRemark,speedScore,speedRemark,concertScore,concertRemark,pliableRemark,balanceScore,balanceRemark,powerScore,powerRemark,allRemark,allScore,date,createUserName,updateTime")
@ExcelTarget("studentPhysicalEntity")
public class StudentPhysical extends EntityModel {

    @ApiModelProperty(value = "班级id", hidden = true)
    private Long classesId;

    @ApiModelProperty(value = "班级名称", hidden = true)
    private String classesName;

    @ApiModelProperty(value = "学生姓名", hidden = true)
    @Excel(name = "姓名")
    private String studentName;

    @ApiModelProperty(value = "学生id")
    private Long studentId;

    @ApiModelProperty(value = "身高")
    @Excel(name = "身高")
    private Double height;

    @ApiModelProperty(value = "体重")
    @Excel(name = "体重")
    private Double weight;

    @ApiModelProperty(value = "BMI指数")
    @Excel(name = "BMI指数")
    private Double bmi;

    @ApiModelProperty(value = "足内外翻、扁平足")
    @Excel(name = "足内外翻、扁平足", width = 16)
    private String downValgus;

    @ApiModelProperty(value = "八字脚")
    @Excel(name = "八字脚")
    private String downEightFoot;

    @ApiModelProperty(value = "X型、O型腿")
    @Excel(name = "X型、O型腿", width = 15)
    private String downXo;

    @ApiModelProperty(value = "膝关节超伸")
    @Excel(name = "膝关节超伸")
    private String downOverextension;

    @ApiModelProperty(value = "长短腿")
    @Excel(name = "长短腿")
    private String downLongLegs;

    @ApiModelProperty(value = "骨盆回旋")
    @Excel(name = "骨盆回旋")
    private String downPelvicGyration;

    @ApiModelProperty(value = "骨盆前后倾")
    @Excel(name = "骨盆前后倾")
    private String downPelvicAnter;

    @ApiModelProperty(value = "骨盆左右高低")
    @Excel(name = "骨盆左右高低", width = 12)
    private String downLeftPelvic;

    @ApiModelProperty(value = "脊柱侧弯")
    @Excel(name = "脊柱侧弯")
    private String upScoliosis;

    @ApiModelProperty(value = "胸椎、腰椎侧曲")
    @Excel(name = "胸椎、腰椎侧曲", width = 15)
    private String upThoracic;

    @ApiModelProperty(value = "翼状肩胛")
    @Excel(name = "翼状肩胛")
    private String upWingedScapula;

    @ApiModelProperty(value = "高低肩、耸肩、塌肩")
    @Excel(name = "高低肩、耸肩、塌肩", width = 18)
    private String upHighLowShoulders;

    @ApiModelProperty(value = "圆肩、含胸")
    @Excel(name = "圆肩、含胸")
    private String upRoundShoulders;

    @ApiModelProperty(value = "颈椎前引、后缩")
    @Excel(name = "颈椎前引、后缩", width = 15)
    private String upAnteriorCervical;

    @ApiModelProperty(value = "嘴角高低是否一致")
    @Excel(name = "嘴角高低是否一致", width = 16)
    private String upIsCorners;

    @ApiModelProperty(value = "颈椎侧曲、侧旋")
    @Excel(name = "颈椎侧曲、侧旋", width = 15)
    private String upLateralCurvature;

    @ApiModelProperty(value = "灵敏分数")
    @Excel(name = "灵敏分数")
    private Double sensitiveScore;

    @ApiModelProperty(value = "灵敏评估指示")
    @Column(columnDefinition = "varchar(1000) COMMENT '灵敏评估指示'")
    @Excel(name = "灵敏评估指示", width = 16)
    private String sensitiveRemark;

    @ApiModelProperty(value = "速度分数")
    @Excel(name = "速度分数")
    private Double speedScore;

    @ApiModelProperty(value = "速度评估指示")
    @Column(columnDefinition = "varchar(1000) COMMENT '速度评估指示'")
    @Excel(name = "速度评估指示", width = 16)
    private String speedRemark;

    @ApiModelProperty(value = "协调分数")
    @Excel(name = "协调分数")
    private Double concertScore;

    @ApiModelProperty(value = "协调评估指示")
    @Column(columnDefinition = "varchar(1000) COMMENT '协调评估指示'")
    @Excel(name = "协调评估指示", width = 16)
    private String concertRemark;

    @ApiModelProperty(value = "柔韧分数")
    private Double pliableScore;

    @ApiModelProperty(value = "柔韧评估指示")
    @Column(columnDefinition = "varchar(1000) COMMENT '柔韧评估指示'")
    @Excel(name = "柔韧评估指示", width = 16)
    private String pliableRemark;

    @ApiModelProperty(value = "平衡分数")
    @Excel(name = "平衡分数")
    private Double balanceScore;

    @ApiModelProperty(value = "平衡评估指示")
    @Column(columnDefinition = "varchar(1000) COMMENT '平衡评估指示'")
    @Excel(name = "平衡评估指示", width = 16)
    private String balanceRemark;

    @ApiModelProperty(value = "力量分数")
    @Excel(name = "力量分数")
    private Double powerScore;

    @ApiModelProperty(value = "力量评估指示")
    @Column(columnDefinition = "varchar(1000) COMMENT '力量评估指示'")
    @Excel(name = "力量评估指示")
    private String powerRemark;

    @ApiModelProperty(value = "总体评估建议")
    @Column(columnDefinition = "varchar(1000) COMMENT '总体评估建议'")
    @Excel(name = "总体评估建议", width = 16)
    private String allRemark;

    @ApiModelProperty(value = "总分")
    @Excel(name = "总分")
    private Double allScore;

    @ApiModelProperty(value = "评测时间", hidden = true)
    @Excel(name = "评测时间")
    private String date;

    @ApiModelProperty(value = "评测人id", hidden = true)
    private Long createUid;

    @ApiModelProperty(value = "评测人姓名", hidden = true)
    @Excel(name = "评测人姓名")
    private String createUserName;

    @ApiModelProperty(value = "更新时间", hidden = true)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "评测更新时间", format = "yyyy-MM-dd HH:mm:ss", width = 20)
    private Date updateTime;

    @ApiModelProperty(value = "学校id", hidden = true)
    @TableField(exist = false)
    @Transient
    private Long schoolId;

    public StudentPhysical(Long classesId, Long studentId, Double allScore, String date, Long createUid, String createUserName, Date updateTime) {
        this.classesId = classesId;
        this.studentId = studentId;
        this.allScore = allScore;
        this.date = date;
        this.createUid = createUid;
        this.createUserName = createUserName;
        this.updateTime = updateTime;
    }

}
