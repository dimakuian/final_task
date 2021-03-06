package com.epam.hospital.db.entity;

import org.apache.log4j.Logger;

import java.util.NoSuchElementException;


public class User extends Entity {

    private static final Logger LOG = Logger.getLogger(User.class);

    private String login;

    private String password;

    private String role;

    public User() {
    }

    public User(Integer id, String login, String password, String role) {
        if (!contains(role)) {
            LOG.error("Role doesn't exist");
            throw new NoSuchElementException();
        }
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        if (!contains(role)) {
            LOG.error("Role doesn't exist");
            throw new NoSuchElementException();
        }
        this.role = role;
    }

    private static boolean contains(String role) {
        for (Role c : Role.values()) {
            if (c.value().equals(role)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "User [" + "id=" + id + ", login=" + login + ", password=" + password + ", role=" + role + "]";
    }
}
