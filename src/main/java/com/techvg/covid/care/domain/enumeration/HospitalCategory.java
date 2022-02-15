package com.techvg.covid.care.domain.enumeration;

/**
 * The HospitalCategory enumeration.
 */
public enum HospitalCategory {
    CENTRAL("CentralMinistry"),
    GOVT("Govt"),
    PRIVATE("Private");

    private final String value;

    HospitalCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
