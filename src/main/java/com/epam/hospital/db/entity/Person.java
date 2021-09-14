package com.epam.hospital.db.entity;


import org.apache.log4j.Logger;

import java.sql.Date;
import java.util.NoSuchElementException;

public class Person extends Entity {

    private static final Logger LOG = Logger.getLogger(Person.class);

    protected String firstName;

    protected String lastName;

    protected Date age;

    protected String gender;

    public Person() {
    }

    public Person(Integer id, String firstName, String lastName, Date age, String gender) {
        if (!Gender.contains(gender)) {
            LOG.error("Gender doesn't exist");
            throw new NoSuchElementException();
        }
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getAge() {
        return age;
    }

    public void setAge(Date age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (!Gender.contains(gender)) {
            LOG.error("Gender doesn't exist");
            throw new NoSuchElementException();
        }
        this.gender = gender;
    }



    @Override
    public String toString() {
        return "Person [" + "id=" + id + ", first name=" + firstName +
                ", last name=" + lastName + ", age=" + age + ", gender=" + gender + "]";
    }
}
