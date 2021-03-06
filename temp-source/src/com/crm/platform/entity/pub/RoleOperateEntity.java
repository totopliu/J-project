package com.crm.platform.entity.pub;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "t_sys_role_operate")
public class RoleOperateEntity implements java.io.Serializable {
    @Transient
    private static final long serialVersionUID = 7794389182171635877L;

    private Integer role;

    @Column(name = "op_id")
    private Integer opId;

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getOpId() {
        return opId;
    }

    public void setOpId(Integer opId) {
        this.opId = opId;
    }

}