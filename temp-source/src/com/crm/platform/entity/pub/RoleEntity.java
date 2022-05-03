package com.crm.platform.entity.pub;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "t_sys_role")
public class RoleEntity implements java.io.Serializable {
    @Transient
    private static final long serialVersionUID = 5227306709528270839L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String code;
    private String remark;
    private Integer defaultState;

    public RoleEntity() {
    }

    public Integer getDefaultState() {
        return defaultState;
    }

    public void setDefaultState(Integer defaultState) {
        this.defaultState = defaultState;
    }

    public RoleEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}