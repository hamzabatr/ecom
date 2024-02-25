package com.ms.user.controllers.body.enums;

public enum EGrantType {
    PASSWORD("password"),
    REFRESH_TOKEN("refresh-token");

    public final String label;

    EGrantType(String label) {
        this.label = label;
    }
}
