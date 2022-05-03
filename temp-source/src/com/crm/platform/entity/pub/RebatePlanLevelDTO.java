package com.crm.platform.entity.pub;

import java.io.Serializable;
import java.util.List;

public class RebatePlanLevelDTO implements Serializable {

    private static final long serialVersionUID = 7098787492894090187L;

    private String level;
    private List<RebatePlanCurrencyDTO> currencies;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<RebatePlanCurrencyDTO> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<RebatePlanCurrencyDTO> currencies) {
        this.currencies = currencies;
    }

}
