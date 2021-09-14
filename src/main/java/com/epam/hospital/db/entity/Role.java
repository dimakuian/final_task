package com.epam.hospital.db.entity;

public enum Role {

    ADMINISTRATOR("administrator"), DOCTOR("doctor"), NURSE("nurse");

    private String value;

    Role(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public boolean equalsTo(String name) {
        return value.equals(name);
    }
}
