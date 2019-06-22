package com.teach.wecharprogram.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @Describle This Class Is 微信用户信息
 * @Author ZengMin
 * @Date 2019/5/28 14:53
 */
@ApiModel(value = "微信用户信息")
@NoArgsConstructor
@AllArgsConstructor
public class WxUserInfoVo {

    @ApiModelProperty(value = "头像")
    private String img;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "性别1男 0女")
    private Integer gender;

    @ApiModelProperty(value = "语言")
    private String language;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "jsCode")
    private String code;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getNickName() {
        try {
            return URLDecoder.decode(nickName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return nickName;
    }

    public void setNickName(String nickName) {
        try {
            this.nickName = URLEncoder.encode(nickName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
