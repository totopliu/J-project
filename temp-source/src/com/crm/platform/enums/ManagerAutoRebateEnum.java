package com.crm.platform.enums;

public enum ManagerAutoRebateEnum {

    MANUAL(0), AUTO(1);

    private int value;

    private ManagerAutoRebateEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
