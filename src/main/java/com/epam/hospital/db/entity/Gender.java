package com.epam.hospital.db.entity;

public enum Gender {

    MALE("male"), FEMALE("female");

    private String value;

    Gender(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public boolean equalsTo(String name) {
        return value.equals(name);
    }

    public static boolean contains(String gender) {
        for (Gender c : Gender.values()) {
            if (c.value().equals(gender)) {
                return true;
            }
        }
        return false;
    }
}
