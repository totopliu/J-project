package com.crm.platform.enums;

public enum InGoldAutoReviewEnum {

    MANUAL(0), AUTO(1);

    private int value;

    private InGoldAutoReviewEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
