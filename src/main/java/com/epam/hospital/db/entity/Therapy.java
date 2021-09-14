package com.epam.hospital.db.entity;

import org.apache.log4j.Logger;

import java.util.NoSuchElementException;

public class Therapy extends Entity {

    private static final Logger LOG = Logger.getLogger(Therapy.class);

    private String title;

    private String type;

    private String status;

    private MedicalFile medicalFile;

    private Staff staff;


    public Therapy() {
    }

    public Therapy(Integer id, String title, String type, String status, MedicalFile medicalFile, Staff staff) {
        if (!TherapyStatus.contains(status)) {
            LOG.error("Therapy status doesn't exist");
            throw new NoSuchElementException();
        } else if (!TherapyType.contains(type)) {
            LOG.error("Therapy type doesn't exist");
            throw new NoSuchElementException();
        }
        this.id = id;
        this.title = title;
        this.type = type;
        this.status = status;
        this.medicalFile = medicalFile;
        this.staff = staff;
    }

    public Therapy(Integer id, String title, String type, String status, MedicalFile medicalFile) {
        if (!TherapyStatus.contains(status)) {
            LOG.error("Therapy status doesn't exist");
            throw new NoSuchElementException();
        } else if (!TherapyType.contains(type)) {
            LOG.error("Therapy type doesn't exist");
            throw new NoSuchElementException();
        }
        this.id = id;
        this.title = title;
        this.type = type;
        this.status = status;
        this.medicalFile = medicalFile;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (!TherapyType.contains(type)) {
            LOG.error("Therapy type doesn't exist");
            throw new NoSuchElementException();
        }
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (!TherapyStatus.contains(status)) {
            LOG.error("Therapy status doesn't exist");
            throw new NoSuchElementException();
        }
        this.status = status;
    }

    public MedicalFile getMedicalFile() {
        return medicalFile;
    }

    public void setMedicalFile(MedicalFile medicalFile) {
        this.medicalFile = medicalFile;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    @Override
    public String toString() {
        return "Therapy [" + "id=" + id + ", title=" + title + ", type=" + type + ", status=" + status
                + ", medical file=" + medicalFile + ", staff=" + staff + "]";
    }
}
