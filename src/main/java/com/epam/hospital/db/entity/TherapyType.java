package com.epam.hospital.db.entity;

public enum TherapyType {

    DRUG("drug"), PROCEDURE("procedure"), OPERATION("operation");

    private String value;

    TherapyType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public boolean equalsTo(String name) {
        return value.equals(name);
    }

    public static boolean contains(String type) {
        for (TherapyType c : TherapyType.values()) {
            if (c.value().equals(type)) {
                return true;
            }
        }
        return false;
    }
}
