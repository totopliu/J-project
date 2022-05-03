package com.crm.platform.entity.pub;

import java.io.Serializable;

public class RebatePlanDTO implements Serializable {

    private static final long serialVersionUID = 5435844812145024211L;

    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
