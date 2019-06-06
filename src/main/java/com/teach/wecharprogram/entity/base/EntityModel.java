package com.teach.wecharprogram.entity.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * @Describle This Class Is 实体Model
 * @Author ZengMin
 * @Date 2019/3/14 16:36
 */
@MappedSuperclass
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
@JsonInclude(JsonInclude.Include.ALWAYS)
public abstract class EntityModel implements Serializable {

    @Id
    @Column(length = 32)
    @ApiModelProperty(value = "主键")
    private Long id;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "datetime default now() COMMENT '创建时间' ")
    @ApiModelProperty(hidden = true)
    private Date createTime;

}

