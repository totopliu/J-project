package com.crm.platform.enums;

public enum PayStateEnum {

    UNPAY(0), PASS(1), REFUSE(2), PAY(3);

    private int value;

    private PayStateEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
