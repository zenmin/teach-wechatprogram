package com.teach.wecharprogram.entity;

import com.teach.wecharprogram.entity.base.EntityModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
* Create by Code Generator
* JPA只用来正向生成数据库表和字段 如果不需要此字段更新 请加上注解@TableField(exist = false)和@Transient
* @Author ZengMin
* @Date 2019-06-15 17:59:30
*/

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="学校管理")
@Table(name = "school")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class School extends EntityModel {

    @ApiModelProperty(value = "按创建时间查询 格式：yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    @Transient
    private String createTimeQuery;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "状态0禁用 1启用")
    private Integer status;


}
