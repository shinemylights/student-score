package com.student.common;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lxy
 * @date 2024/6/2 15:21
 */
@Data
public class WenjuanScoresVO implements Serializable {

    private String studentId;

    private String studentName;

    private Integer xyScore;

    private Integer sxScore;

    private Integer pdScore;

    private BigDecimal average;

}
