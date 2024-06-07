package com.student.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName contest
 */
@TableName(value ="contest")
@Data
public class Contest implements Serializable {
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
    private String level;

    /**
     * 
     */
    private String title;

    /**
     * 
     */
    private Date date;

    /**
     * 
     */
    private String organization;

    /**
     * 
     */
    private String description;

    /**
     * 
     */
    private String category;

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
        Contest other = (Contest) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getLevel() == null ? other.getLevel() == null : this.getLevel().equals(other.getLevel()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getDate() == null ? other.getDate() == null : this.getDate().equals(other.getDate()))
            && (this.getOrganization() == null ? other.getOrganization() == null : this.getOrganization().equals(other.getOrganization()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getCategory() == null ? other.getCategory() == null : this.getCategory().equals(other.getCategory()))
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
        result = prime * result + ((getLevel() == null) ? 0 : getLevel().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getDate() == null) ? 0 : getDate().hashCode());
        result = prime * result + ((getOrganization() == null) ? 0 : getOrganization().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getCategory() == null) ? 0 : getCategory().hashCode());
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
        sb.append(", level=").append(level);
        sb.append(", title=").append(title);
        sb.append(", date=").append(date);
        sb.append(", organization=").append(organization);
        sb.append(", description=").append(description);
        sb.append(", category=").append(category);
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