package com.student.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lxy
 * @date 2024/5/31 0:50
 */
@Data
public class AuditRequest implements Serializable {

    private int current;

    private String studentId;

    private String studentName;

}
