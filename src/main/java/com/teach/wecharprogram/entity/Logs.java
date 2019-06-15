package com.teach.wecharprogram.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.teach.wecharprogram.entity.base.EntityModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
* Create by Code Generator
* JPA只用来正向生成数据库表和字段 如果不需要此字段更新 请加上注解@TableField(exist = false)和@Transient
* @Author ZengMin
* @Date 2019-03-19 20:50:11
* https://github.com/zenmin/ProjectTemplate
*/

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="Logs", description="")
@Table(name = "logs")
@Entity
public class Logs extends EntityModel {

    @ApiModelProperty(value = "创建时间 格式：yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    @Transient
    private String createTimeQuery;

    private Date date;

    private String reqIp;

    private String reqParams;

    private String requestURL;

    public Logs(String reqIp, String requestURL, String reqParams) {
        this.date = new Date();
        this.reqIp = reqIp;
        this.reqParams = reqParams;
        this.requestURL = requestURL;
    }

    public Logs() {
    }
}
