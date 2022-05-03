package com.crm.platform.enums;

public enum TransactionTypeEnum {

    ABOOK(1), BBOOK(2);

    private int value;

    private TransactionTypeEnum(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    public int getValue() {
        return value;
    }

}
