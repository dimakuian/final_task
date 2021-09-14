package com.epam.hospital.db.entity;

import org.apache.log4j.Logger;

import java.sql.Date;
import java.util.NoSuchElementException;

public class Doctor extends Person {

    private static final Logger LOG = Logger.getLogger(Doctor.class);

    private Staff staff;

    private DoctorSpecialty doctorSpecialty;

    private Integer numberOfPatients;

    public Doctor() {
    }

    public Doctor(Integer id, String firstName, String lastName, Date age, String gender,
                  Staff staff, DoctorSpecialty ds) {
        if (!Gender.contains(gender)) {
            LOG.error("Gender doesn't exist");
            throw new NoSuchElementException();
        }
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.staff = staff;
        this.doctorSpecialty = ds;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public DoctorSpecialty getDoctorSpecialty() {
        return doctorSpecialty;
    }

    public void setDoctorSpecialty(DoctorSpecialty doctorSpecialty) {
        this.doctorSpecialty = doctorSpecialty;
    }

    public Integer getNumberOfPatients() {
        return numberOfPatients;
    }

    public void setNumberOfPatients(Integer numberOfPatients) {
        this.numberOfPatients = numberOfPatients;
    }

    @Override
    public String toString() {
        return "Doctor [" + "id=" + id + ", first name=" + firstName + ", last name=" + lastName +
                ", age=" + age + ", gender=" + gender + ", staff=" + staff + ", specialty=" + doctorSpecialty +
                ", number of patients=" + numberOfPatients + "]";
    }
}
