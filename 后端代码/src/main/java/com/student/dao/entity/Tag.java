package com.student.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName tag
 */
@TableName(value ="tag")
@Data
public class Tag implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String category;

    /**
     * 
     */
    private String basic;

    /**
     * 
     */
    private String middle;

    /**
     * 
     */
    private String advanced;

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
        Tag other = (Tag) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCategory() == null ? other.getCategory() == null : this.getCategory().equals(other.getCategory()))
            && (this.getBasic() == null ? other.getBasic() == null : this.getBasic().equals(other.getBasic()))
            && (this.getMiddle() == null ? other.getMiddle() == null : this.getMiddle().equals(other.getMiddle()))
            && (this.getAdvanced() == null ? other.getAdvanced() == null : this.getAdvanced().equals(other.getAdvanced()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCategory() == null) ? 0 : getCategory().hashCode());
        result = prime * result + ((getBasic() == null) ? 0 : getBasic().hashCode());
        result = prime * result + ((getMiddle() == null) ? 0 : getMiddle().hashCode());
        result = prime * result + ((getAdvanced() == null) ? 0 : getAdvanced().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", category=").append(category);
        sb.append(", basic=").append(basic);
        sb.append(", middle=").append(middle);
        sb.append(", advanced=").append(advanced);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}