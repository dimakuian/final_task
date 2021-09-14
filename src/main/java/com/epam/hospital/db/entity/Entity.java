package com.epam.hospital.db.entity;

import java.io.Serializable;

public class Entity implements Serializable {
    protected Integer id;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Entity [id=" + id + "]";
    }
}
