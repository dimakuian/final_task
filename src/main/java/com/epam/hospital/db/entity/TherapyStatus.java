package com.epam.hospital.db.entity;

public enum TherapyStatus {

    DONE("done"), IN_PROGRESS("in progress");

    private String value;

    TherapyStatus(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public boolean equalsTo(String name) {
        return value.equals(name);
    }

    public static boolean contains(String status) {
        for (TherapyStatus c : TherapyStatus.values()) {
            if (c.value().equals(status)) {
                return true;
            }
        }
        return false;
    }
}

