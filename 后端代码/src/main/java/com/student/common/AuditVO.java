package com.student.common;

import lombok.Data;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author lxy
 * @date 2024/5/31 1:06
 */
@Data
public class AuditVO implements Serializable {

    private Integer id;

    private String studentId;

    private String studentName;

    private String approvalStatus;

    private Date submissionDate;

    private String name;

}
