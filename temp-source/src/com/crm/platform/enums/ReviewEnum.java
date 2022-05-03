package com.crm.platform.enums;

public enum ReviewEnum {

    PASS(1), UNPASS(2);

    private int value;

    private ReviewEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
