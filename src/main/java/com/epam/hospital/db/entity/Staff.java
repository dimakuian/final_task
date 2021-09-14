package com.epam.hospital.db.entity;

public class Staff extends Entity {


    private User user;

    public Staff() {
    }

    public Staff(Integer id, User user) {
        this.id = id;
        this.user = user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Staff [" + "id=" + id + ", user=" + user + "]";
    }
}
