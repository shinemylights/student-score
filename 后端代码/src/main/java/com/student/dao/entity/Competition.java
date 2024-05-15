package com.student.dao.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author 郑为中
 */
@Data

@TableName("a_competition")
@ApiModel(value = "学科竞赛")
public class Competition {

    private static final long serialVersionUID = 1L;

    private  String id;

    @ApiModelProperty(value = "评分ID")
    private String evaluateId;

    @ApiModelProperty(value = "竞赛级别")
    private String level;

    @ApiModelProperty(value = "竞赛名称")
    private String title;

    @ApiModelProperty(value = "竞赛奖项")
    private String value;
}