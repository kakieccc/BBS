package com.kakie.bbs_backend.common;

/**
 * 分区状态枚举
 */
public enum BarStatusEnum {
    PUBLIC(0,"公开"),
    PRIVATE(1,"私有"),
    SECRET(2,"加密");

    private int value;
    private String text;

    public static BarStatusEnum getEnumByValue(Integer value){
        if (value == null){
            return null;
        }
        BarStatusEnum[] values = BarStatusEnum.values();
        for (BarStatusEnum barStatusEnum: values){
            if (barStatusEnum.getValue()==value){
                return barStatusEnum;
            }
        }
        return null;
    }

    BarStatusEnum(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}