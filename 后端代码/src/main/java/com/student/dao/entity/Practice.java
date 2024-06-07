package com.student.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * 
 * @TableName practice
 */
@TableName(value ="practice")
@Data
public class Practice implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private LocalDate startDate;

    /**
     * 
     */
    private LocalDate endDate;

    /**
     * 
     */
    private String organization;

    /**
     * 
     */
    private String level;

    /**
     * 
     */
    private String description;

    /**
     * 
     */
    private String proofImageUrl;

    /**
     * 
     */
    private String studentId;

    /**
     * 
     */
    private String studentName;

    /**
     * 
     */
    private Date submissionDate;

    /**
     * 
     */
    private String approvalStatus;

    /**
     * 
     */
    private Integer approverId;

    /**
     * 
     */
    private Date approvalDate;

    /**
     * 
     */
    private String remarks;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Practice other = (Practice) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getStartDate() == null ? other.getStartDate() == null : this.getStartDate().equals(other.getStartDate()))
            && (this.getEndDate() == null ? other.getEndDate() == null : this.getEndDate().equals(other.getEndDate()))
            && (this.getOrganization() == null ? other.getOrganization() == null : this.getOrganization().equals(other.getOrganization()))
            && (this.getLevel() == null ? other.getLevel() == null : this.getLevel().equals(other.getLevel()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getProofImageUrl() == null ? other.getProofImageUrl() == null : this.getProofImageUrl().equals(other.getProofImageUrl()))
            && (this.getStudentId() == null ? other.getStudentId() == null : this.getStudentId().equals(other.getStudentId()))
            && (this.getStudentName() == null ? other.getStudentName() == null : this.getStudentName().equals(other.getStudentName()))
            && (this.getSubmissionDate() == null ? other.getSubmissionDate() == null : this.getSubmissionDate().equals(other.getSubmissionDate()))
            && (this.getApprovalStatus() == null ? other.getApprovalStatus() == null : this.getApprovalStatus().equals(other.getApprovalStatus()))
            && (this.getApproverId() == null ? other.getApproverId() == null : this.getApproverId().equals(other.getApproverId()))
            && (this.getApprovalDate() == null ? other.getApprovalDate() == null : this.getApprovalDate().equals(other.getApprovalDate()))
            && (this.getRemarks() == null ? other.getRemarks() == null : this.getRemarks().equals(other.getRemarks()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getStartDate() == null) ? 0 : getStartDate().hashCode());
        result = prime * result + ((getEndDate() == null) ? 0 : getEndDate().hashCode());
        result = prime * result + ((getOrganization() == null) ? 0 : getOrganization().hashCode());
        result = prime * result + ((getLevel() == null) ? 0 : getLevel().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getProofImageUrl() == null) ? 0 : getProofImageUrl().hashCode());
        result = prime * result + ((getStudentId() == null) ? 0 : getStudentId().hashCode());
        result = prime * result + ((getStudentName() == null) ? 0 : getStudentName().hashCode());
        result = prime * result + ((getSubmissionDate() == null) ? 0 : getSubmissionDate().hashCode());
        result = prime * result + ((getApprovalStatus() == null) ? 0 : getApprovalStatus().hashCode());
        result = prime * result + ((getApproverId() == null) ? 0 : getApproverId().hashCode());
        result = prime * result + ((getApprovalDate() == null) ? 0 : getApprovalDate().hashCode());
        result = prime * result + ((getRemarks() == null) ? 0 : getRemarks().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", organization=").append(organization);
        sb.append(", level=").append(level);
        sb.append(", description=").append(description);
        sb.append(", proofImageUrl=").append(proofImageUrl);
        sb.append(", studentId=").append(studentId);
        sb.append(", studentName=").append(studentName);
        sb.append(", submissionDate=").append(submissionDate);
        sb.append(", approvalStatus=").append(approvalStatus);
        sb.append(", approverId=").append(approverId);
        sb.append(", approvalDate=").append(approvalDate);
        sb.append(", remarks=").append(remarks);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}