package com.techvg.covid.care.domain.enumeration;

/**
 * The HospitalSubCategory enumeration.
 */
public enum HospitalSubCategory {
    FREE("Free"),
    MPJAY("Mpjay"),
    CHARGEABLE("Chargeable"),
    TRUST("Trust");

    private final String value;

    HospitalSubCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
