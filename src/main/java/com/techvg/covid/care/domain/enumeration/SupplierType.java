package com.techvg.covid.care.domain.enumeration;

/**
 * The SupplierType enumeration.
 */
public enum SupplierType {
    REFILLER("Refiller"),
    MANUFACTURER("Manufacturer"),
    DEALER("Dealer"),
    DISTRIBUTOR("Distributor"),
    INDUSTRY("Industry");

    private final String value;

    SupplierType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
