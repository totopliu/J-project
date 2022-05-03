package com.crm.platform.enums;

public enum ManagerReviewStateEnum {

    UNAUDITED(0), AUDIT_APPROVAL(1), AUDIT_FAILED(2);

    private int value;

    private ManagerReviewStateEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
