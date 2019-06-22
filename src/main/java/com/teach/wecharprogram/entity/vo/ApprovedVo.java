package com.teach.wecharprogram.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/6/22 17:37
 */
@Data
@ApiModel(value = "提交审批")
public class ApprovedVo {

    @ApiModelProperty(value = "审批id")
    private Long id;

    @ApiModelProperty(value = "结果 1通过 0不通过")
    private String resultCode;

    @ApiModelProperty(value = "审批意见")
    private String opinion;

    @ApiModelProperty(hidden = true)
    private Long approvedUserId;

    @ApiModelProperty(hidden = true)
    private String approvedUserName;

}
