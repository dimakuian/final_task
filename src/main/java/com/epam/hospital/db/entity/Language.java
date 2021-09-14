package com.epam.hospital.db.entity;

public class Language extends Entity {

    private String short_name;

    private String full_name;

    public Language() {
    }

    public Language(Integer id, String short_name, String full_name) {
        this.id = id;
        this.short_name = short_name;
        this.full_name = full_name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    @Override
    public String toString() {
        return "Language [" + "id=" + id + ", short name=" + short_name + ", full name=" + full_name + "]";
    }
}
