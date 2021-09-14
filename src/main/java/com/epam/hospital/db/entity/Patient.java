package com.epam.hospital.db.entity;

import org.apache.log4j.Logger;

import java.sql.Date;
import java.util.NoSuchElementException;

public class Patient extends Person {

    private static final Logger LOG = Logger.getLogger(Patient.class);

    private String status;

    private Doctor doctor;

    public Patient() {
    }

    public Patient(Integer id, String firstName, String lastName, Date age, String gender, String status, Doctor doctor) {
        if (!Gender.contains(gender)) {
            LOG.error("Gender doesn't exist");
            throw new NoSuchElementException();
        }
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.status = status;
        this.doctor = doctor;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Patient [" + "id=" + id + ", first name=" + firstName + ", last name=" + lastName + ", age=" + age
                + ", gender=" + gender + ",  doctor=" + doctor + "]";
    }


}
