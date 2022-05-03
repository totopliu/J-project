package com.crm.platform.enums;

public enum LoginTypeEnum {

    LOGIN(1), REBATELOGIN(2);

    private int value;

    private LoginTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
