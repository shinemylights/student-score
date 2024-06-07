package com.student.common;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @author lxy
 * @date 2024/6/1 21:00
 */
@Data
public class PracAuditDetailVO implements Serializable {


    private String studentId;

    private String studentName;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private String organization;

    private String description;

    private List<String> images;

}
