package com.crm.platform.entity.pub;

import java.io.Serializable;
import java.util.List;

public class GroupRebateDTO implements Serializable {

    private static final long serialVersionUID = -1580753032324254479L;

    private Integer level;
    private List<GroupRebateCurrencyDTO> groupRebateCurrencyDTO;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<GroupRebateCurrencyDTO> getGroupRebateCurrencyDTO() {
        return groupRebateCurrencyDTO;
    }

    public void setGroupRebateCurrencyDTO(List<GroupRebateCurrencyDTO> groupRebateCurrencyDTO) {
        this.groupRebateCurrencyDTO = groupRebateCurrencyDTO;
    }

}
