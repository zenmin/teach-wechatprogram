package com.teach.wecharprogram.entity.DO;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.teach.wecharprogram.entity.base.EntityModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/7/15 20:03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "体适能详情/评测详情", description = "允许导出的字段：classesName,no,studentName,height,weight,bmi,downValgus,downEightFoot,downXo,downOverextension,downLongLegs,downPelvicGyration,downPelvicAnter,downLeftPelvic,upScoliosis,upThoracic,upWingedScapula,upHighLowShoulders,upRoundShoulders,upAnteriorCervical,upIsCorners,upLateralCurvature,sensitiveScore,sensitiveRemark,speedScore,speedRemark,concertScore,concertRemark,pliableRemark,balanceScore,balanceRemark,powerScore,powerRemark,allRemark,allScore,date,createUserName,updateTime")
@ExcelTarget("studentPhysicalEntity")
public class StudentPhysicalDo extends EntityModel {

    @ApiModelProperty(value = "班级id")
    private Long classesId;

    @ApiModelProperty(value = "学校id", hidden = true)
    private Long schoolId;

    @Excel(name = "班级")
    @ApiModelProperty(value = "班级名称", hidden = true)
    private String classesName;

    @ApiModelProperty(value = "学号")
    @Excel(name = "学号")
    private Integer no;

    @ApiModelProperty(value = "学生姓名")
    @Excel(name = "姓名")
    private String studentName;

    @ApiModelProperty(value = "年龄", hidden = true)
    @Excel(name = "年龄")
    private Integer age;

    @ApiModelProperty(value = "性别")
    @Excel(name = "性别")
    private Integer gender;

    @ApiModelProperty(value = "学生id")
    private Long studentId;

    @ApiModelProperty(value = "身高", hidden = true)
    @Excel(name = "身高")
    private Double height;

    @ApiModelProperty(value = "体重", hidden = true)
    @Excel(name = "体重")
    private Double weight;

    @ApiModelProperty(value = "BMI指数", hidden = true)
    @Excel(name = "BMI指数")
    private Double bmi;

    @ApiModelProperty(value = "足内外翻、扁平足", hidden = true)
    @Excel(name = "足内外翻、扁平足", width = 16)
    private String downValgus;

    @ApiModelProperty(value = "八字脚", hidden = true)
    @Excel(name = "八字脚")
    private String downEightFoot;

    @ApiModelProperty(value = "X型、O型腿", hidden = true)
    @Excel(name = "X型、O型腿", width = 15)
    private String downXo;

    @ApiModelProperty(value = "膝关节超伸", hidden = true)
    @Excel(name = "膝关节超伸")
    private String downOverextension;

    @ApiModelProperty(value = "长短腿", hidden = true)
    @Excel(name = "长短腿")
    private String downLongLegs;

    @ApiModelProperty(value = "骨盆回旋", hidden = true)
    @Excel(name = "骨盆回旋")
    private String downPelvicGyration;

    @ApiModelProperty(value = "骨盆前后倾", hidden = true)
    @Excel(name = "骨盆前后倾")
    private String downPelvicAnter;

    @ApiModelProperty(value = "骨盆左右高低", hidden = true)
    @Excel(name = "骨盆左右高低", width = 12)
    private String downLeftPelvic;

    @ApiModelProperty(value = "脊柱侧弯", hidden = true)
    @Excel(name = "脊柱侧弯")
    private String upScoliosis;

    @ApiModelProperty(value = "胸椎、腰椎侧曲", hidden = true)
    @Excel(name = "胸椎、腰椎侧曲", width = 15)
    private String upThoracic;

    @ApiModelProperty(value = "翼状肩胛", hidden = true)
    @Excel(name = "翼状肩胛")
    private String upWingedScapula;

    @ApiModelProperty(value = "高低肩、耸肩、塌肩", hidden = true)
    @Excel(name = "高低肩、耸肩、塌肩", width = 18)
    private String upHighLowShoulders;

    @ApiModelProperty(value = "圆肩、含胸", hidden = true)
    @Excel(name = "圆肩、含胸")
    private String upRoundShoulders;

    @ApiModelProperty(value = "颈椎前引、后缩", hidden = true)
    @Excel(name = "颈椎前引、后缩", width = 15)
    private String upAnteriorCervical;

    @ApiModelProperty(value = "嘴角高低是否一致", hidden = true)
    @Excel(name = "嘴角高低是否一致", width = 16)
    private String upIsCorners;

    @ApiModelProperty(value = "颈椎侧曲、侧旋", hidden = true)
    @Excel(name = "颈椎侧曲、侧旋", width = 15)
    private String upLateralCurvature;

    @ApiModelProperty(value = "灵敏分数", hidden = true)
    @Excel(name = "灵敏分数")
    private Double sensitiveScore;

    @ApiModelProperty(value = "灵敏评估指示", hidden = true)
    @Excel(name = "灵敏评估指示", width = 16)
    private String sensitiveRemark;

    @ApiModelProperty(value = "速度分数", hidden = true)
    @Excel(name = "速度分数")
    private Double speedScore;

    @ApiModelProperty(value = "速度评估指示", hidden = true)
    @Excel(name = "速度评估指示", width = 16)
    private String speedRemark;

    @ApiModelProperty(value = "协调分数", hidden = true)
    @Excel(name = "协调分数")
    private Double concertScore;

    @ApiModelProperty(value = "协调评估指示", hidden = true)
    @Excel(name = "协调评估指示", width = 16)
    private String concertRemark;

    @ApiModelProperty(value = "柔韧分数", hidden = true)
    private Double pliableScore;

    @ApiModelProperty(value = "柔韧评估指示", hidden = true)
    @Excel(name = "柔韧评估指示", width = 16)
    private String pliableRemark;

    @ApiModelProperty(value = "平衡分数", hidden = true)
    @Excel(name = "平衡分数")
    private Double balanceScore;

    @ApiModelProperty(value = "平衡评估指示", hidden = true)
    @Excel(name = "平衡评估指示", width = 16)
    private String balanceRemark;

    @ApiModelProperty(value = "力量分数", hidden = true)
    @Excel(name = "力量分数")
    private Double powerScore;

    @ApiModelProperty(value = "力量评估指示", hidden = true)
    @Excel(name = "力量评估指示")
    private String powerRemark;

    @ApiModelProperty(value = "总体评估建议", hidden = true)
    @Excel(name = "总体评估建议", width = 16)
    private String allRemark;

    @ApiModelProperty(value = "总分", hidden = true)
    @Excel(name = "总分")
    private Double allScore;

    @ApiModelProperty(value = "评测时间")
    @Excel(name = "评测时间")
    private String date;

    @ApiModelProperty(value = "评测人id", hidden = true)
    private Long createUid;

    @ApiModelProperty(value = "评测人姓名")
    @Excel(name = "评测人姓名")
    private String createUserName;

    @ApiModelProperty(value = "按什么字段排序")
    private String sortFiled;

    @ApiModelProperty(value = "排序方式 1顺序 0逆序")
    private Integer sortType;

}
