package com.epam.hospital.db.entity;

import com.epam.hospital.db.DAO.config.MedicalFileController;

import java.sql.Date;

public class MedicalFile extends Entity {

    private String diagnosis;

    private Patient patient;

    public MedicalFile() {
    }

    public MedicalFile(Integer id, String diagnosis, Patient patient) {
        this.id = id;
        this.diagnosis = diagnosis;
        this.patient = patient;
    }

    public MedicalFile(Integer id,Patient patient) {
        this.id = id;
        this.patient = patient;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "Medical File [" + "id=" + id + ", diagnosis=" + diagnosis + ", patient=" + patient + "]";
    }
}
