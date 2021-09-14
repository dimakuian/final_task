package com.epam.hospital.db.entity;

import org.apache.log4j.Logger;

import java.sql.Date;
import java.util.NoSuchElementException;

public class Nurse extends Person {

    private static final Logger LOG = Logger.getLogger(Nurse.class);

    private Staff staff;

    public Nurse() {
    }

    public Nurse(Integer id, String firstName, String lastName, Date age, String gender, Staff staff) {
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
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    @Override
    public String toString() {
        return "Nurse [" + "id=" + id + ", first name=" + firstName + ", last name=" + lastName + ", age=" + age
                + ", gender=" + gender + ", staff=" + staff + "]";
    }
}
