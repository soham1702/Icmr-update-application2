package com.techvg.covid.care.domain.enumeration;

/**
 * The TransactionStatus enumeration.
 */
public enum TransactionStatus {
    OPEN("Open"),
    TRANSIT("Transit"),
    CANCELLED("Cancelled"),
    RECEIVED("Received");

    private final String value;

    TransactionStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
