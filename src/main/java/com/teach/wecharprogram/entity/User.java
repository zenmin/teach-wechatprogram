package com.teach.wecharprogram.entity;

import com.teach.wecharprogram.entity.base.EntityModel;

import java.util.Date;

import com.teach.wecharprogram.entity.vo.WxUserInfoVo;
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
 *
 * @Author ZengMin
 * @Date 2019-06-15 18:17:10
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "用户管理")
@Table(name = "user")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User extends EntityModel {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "头像")
    private String img;

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
    private Date lastLoginTime;

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

    @ApiModelProperty(value = "角色名称 冗余")
    private String roleName;

    @ApiModelProperty(value = "状态1启用 0禁用 2角色信息待验证")
    private Integer status;

    @TableField(exist = false)
    @Transient
    private String nowLoginIp;

    @ApiModelProperty(value = "是否重置密码")
    @TableField(exist = false)
    @Transient
    private Boolean resetPassword;

    public User(Long id, Date lastLoginTime, String lastLoginIp) {
        super.setId(id);
        this.lastLoginTime = lastLoginTime;
        this.lastLoginIp = lastLoginIp;
    }

    public User(String username, String password, String nickName, Integer status, Long inviteCode, String lastLoginIp, Date lastLoginTime) {
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.status = status;
        this.lastLoginIp = lastLoginIp;
        this.lastLoginTime = lastLoginTime;
    }

    public User(String username, String password, Integer status, String phone, String nickName, String lastLoginIp, Date lastLoginTime) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.phone = phone;
        this.nickName = nickName;
        this.lastLoginIp = lastLoginIp;
        this.lastLoginTime = lastLoginTime;
    }


    public User(WxUserInfoVo wxUserInfoVo, String lastLoginIp, String initPassword, Integer status, String openid) {
        this.city = wxUserInfoVo.getCity();
        this.country = wxUserInfoVo.getCountry();
        this.gender = wxUserInfoVo.getGender();
        this.language = wxUserInfoVo.getLanguage();
        this.nickName = wxUserInfoVo.getNickName();
        this.province = wxUserInfoVo.getProvince();
        this.phone = wxUserInfoVo.getPhone();
        this.img = wxUserInfoVo.getImg();
        this.openid = openid;
        this.status = status;
        this.lastLoginIp = lastLoginIp;
        this.lastLoginTime = new Date();
        this.password = initPassword;
    }


    public User(Long id,String realName, String img, Integer gender, String nickName, String phone, Integer status) {
        super.setId(id);
        this.realName = realName;
        this.img = img;
        this.gender = gender;
        this.nickName = nickName;
        this.phone = phone;
        this.status = status;
    }
}
