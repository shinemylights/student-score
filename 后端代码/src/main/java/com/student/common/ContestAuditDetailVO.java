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
public class ContestAuditDetailVO implements Serializable {


    private String studentId;

    private String studentName;

    private String name;

    private String level;

    private String title;

    private LocalDate date;

    private String organization;

    private String description;

    private String category;

    private List<String> images;

}
