package com.techvg.covid.care.domain.enumeration;

/**
 * The AccessLevel enumeration.
 */
public enum AccessLevel {
    HOSPITAL("Hospital"),
    SUPPLIER("Supplier"),
    TALUKA("Taluka"),
    DISTRICT("District"),
    STATE("State"),
    MUNCIPALCORP("MunicipalCorp");

    private final String value;

    AccessLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
