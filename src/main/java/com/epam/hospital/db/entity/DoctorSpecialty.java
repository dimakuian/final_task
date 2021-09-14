package com.epam.hospital.db.entity;

public class DoctorSpecialty extends Entity {

    private String title;

    private Language language;

    public DoctorSpecialty() {
    }

    public DoctorSpecialty(Integer id, String name, Language language) {
        this.id = id;
        this.title = name;
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Language getLanguage() {
        return language;
    }

    @Override
    public String toString() {
        return "Specialty [" + "id=" + id + ", title=" + title + "]";
    }
}
