package com.student.common;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lxy
 * @date 2024/5/31 1:06
 */
@Data
public class AuditVO implements Serializable {

    private String studentId;

    private String studentName;

    private String approvalStatus;

    private Date submissionDate;
}
