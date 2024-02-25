package com.ms.user.configurations.jwt;

public enum EToken {
    ACCESS_TOKEN("access-token"),
    REFRESH_TOKEN("refresh-token");

    public final String label;

    EToken(String label) {
        this.label = label;
    }
}
