package com.teach.wecharprogram.entity;

import com.teach.wecharprogram.entity.base.EntityModel;
import java.time.LocalDateTime;
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
* @Date 2019-06-15 18:17:10
*/

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="用户管理")
@Table(name = "user")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User extends EntityModel {

    @ApiModelProperty(value = "按创建时间查询 格式：yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    @Transient
    private String createTimeQuery;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "性别")
    private Integer gender;

    @ApiModelProperty(value = "语言")
    private String language;

    @ApiModelProperty(value = "最后一次登录IP")
    private String lastLoginIp;

    @ApiModelProperty(value = "最后一次登录时间")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "微信openid")
    private String openid;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "角色码")
    private String roleCode;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "状态1启用 2禁用")
    private Integer status;

    @ApiModelProperty(value = "学校id")
    private Long schoolId;

}
