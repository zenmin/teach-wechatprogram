package com.teach.wecharprogram.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.teach.wecharprogram.entity.base.EntityModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Create by Code Generator
 * JPA只用来正向生成数据库表和字段 如果不需要此字段更新 请加上注解@TableField(exist = false)和@Transient
 *
 * @Author ZengMin
 * @Date 2019-06-10 10:24:28
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "角色管理")
@Table(name = "role")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Role extends EntityModel {

    @ApiModelProperty(value = "按创建时间查询 格式：yyyy-MM-dd HH:mm:ss")
    @TableField(exist = false)
    @Transient
    private String createTimeQuery;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    @ApiModelProperty(value = "角色描述")
    private String description;

    @ApiModelProperty(value = "是否为管理员")
    private Boolean isAdmin;

    public Role(String roleName, String roleCode, String description, Boolean isAdmin) {
        this.roleName = roleName;
        this.roleCode = roleCode;
        this.description = description;
        this.isAdmin = isAdmin;
    }
}
