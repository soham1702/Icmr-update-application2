package com.techvg.covid.care.domain.enumeration;

/**
 * The StatusInd enumeration.
 */
public enum StatusInd {
    A("Active"),
    I("Inactive"),
    D("Deleted");

    private final String value;

    StatusInd(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
