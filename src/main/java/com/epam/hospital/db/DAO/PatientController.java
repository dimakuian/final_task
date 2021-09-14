package com.epam.hospital.db.DAO;


import com.epam.hospital.db.DAO.config.MedicalFileController;
import com.epam.hospital.db.entity.MedicalFile;
import com.epam.hospital.db.entity.Patient;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientController extends AbstractController<Patient, Integer> {

    private static final org.apache.log4j.Logger logger = Logger.getLogger(PatientController.class);

    public static final String SELECT_ALL_PATIENT = "SELECT * FROM patient ";

    public static final String SELECT_PATIENT_BY_ID = "SELECT * FROM patient WHERE id = ?";

    public static final String INSERT_PATIENT =
            "INSERT INTO patient (first_name, last_name, age, gender, status, doctor_id ) VALUES (?, ?, ?, ?, ?, ?)";

    public static final String SELECT_ALL_DOCTOR_PATIENTS = "SELECT * FROM patient WHERE doctor_id = ?";

    public static final String UPDATE_PATIENT_STATUS = "UPDATE patient SET `status` = ? WHERE (`id` = ?)";

    public static final String UPDATE_PATIENT_DOCTOR_ID =
            "UPDATE `hospital`.`patient` SET `doctor_id` = ? WHERE (`id` = ?)";

    public static final String SELECT_LAST_ID = "SELECT MAX( id ) FROM patient";


    public PatientController() throws SQLException {
    }

    @Override
    public List<Patient> getAll() throws SQLException {
        List<Patient> patientList = new ArrayList<>();
        try (PreparedStatement ps = getPrepareStatement(SELECT_ALL_PATIENT)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Patient patient = new Patient();
                patient.setId(rs.getInt("id"));
                patient.setFirstName(rs.getString("first_name"));
                patient.setLastName(rs.getString("last_name"));
                patient.setAge(rs.getDate("age"));
                patient.setGender(rs.getString("gender"));
                patient.setStatus(rs.getString("status"));
                DoctorController doctorController = new DoctorController();
                if (rs.getInt("doctor_id") != 0) {
                    patient.setDoctor(doctorController.getEntityById(rs.getInt("doctor_id")));
                } else {
                    patient.setDoctor(null);
                }
                patientList.add(patient);
            }
        } catch (SQLException e) {
            logger.error("Can't execute query", e);
            throw new SQLException("Can't execute query");
        }
        return patientList;
    }

    @Override
    public Patient update(Patient entity) throws SQLException {
        logger.info("this empty");
        return null;
    }

    @Override
    public Patient getEntityById(Integer id) throws SQLException {
        try (PreparedStatement ps = getPrepareStatement(SELECT_PATIENT_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Patient patient = new Patient();
                patient.setId(id);
                patient.setFirstName(rs.getString("first_name"));
                patient.setLastName(rs.getString("last_name"));
                patient.setAge(rs.getDate("age"));
                patient.setGender(rs.getString("gender"));
                patient.setStatus(rs.getString("status"));
                DoctorController doctorController = new DoctorController();
                if (rs.getInt("doctor_id") != 0) {
                    patient.setDoctor(doctorController.getEntityById(rs.getInt("doctor_id")));
                } else {
                    patient.setDoctor(null);
                }
                return patient;
            }
        } catch (SQLException e) {
            logger.error("Can't' execute query", e);
            throw new SQLException("Can't execute query");
        }
        logger.error("Can't find the patient");
        throw new SQLException("Can't find the patient");
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        logger.info("this empty");
        return false;
    }

    @Override
    public boolean insert(Patient patient) throws SQLException {
        try (PreparedStatement ps = getPrepareStatement(INSERT_PATIENT)) {
            ps.setString(1, patient.getFirstName());
            ps.setString(2, patient.getLastName());
            ps.setDate(3, patient.getAge());
            ps.setString(4, patient.getGender());
            ps.setString(5, patient.getStatus());
            ps.setInt(6, patient.getDoctor().getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            logger.error("Can't execute query", e);
            throw new SQLException("Can't' execute query", e);
        }
    }

    public List<Patient> getAllDoctorPatients(Integer id) throws SQLException {
        List<Patient> patientList = new ArrayList<>();

        try (PreparedStatement ps = getPrepareStatement(SELECT_ALL_DOCTOR_PATIENTS)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Patient patient = new Patient();
                patient.setId(rs.getInt("id"));
                patient.setFirstName(rs.getString("first_name"));
                patient.setLastName(rs.getString("last_name"));
                patient.setAge(rs.getDate("age"));
                patient.setGender(rs.getString("gender"));
                patient.setStatus(rs.getString("status"));
                DoctorController doctorController = new DoctorController();
                patient.setDoctor(doctorController.getEntityById(rs.getInt("doctor_id")));
                patientList.add(patient);
            }
        } catch (SQLException e) {
            logger.error("Can not execute query", e);
            throw new SQLException("Can not execute query");
        }
        return patientList;
    }


    public Patient updateStatus(Patient entity) throws SQLException {
        try (PreparedStatement ps = getPrepareStatement(UPDATE_PATIENT_STATUS)) {
            ps.setString(1, entity.getStatus());
            ps.setInt(2, entity.getId());
            ps.execute();
            return entity;
        } catch (SQLException e) {
            logger.error("Can not execute query", e);
            throw new SQLException("Can not execute query");
        }
    }


    public Patient updateDoctor(Patient patient) throws SQLException {
        try (PreparedStatement ps = getPrepareStatement(UPDATE_PATIENT_DOCTOR_ID)) {
            ps.setInt(1, patient.getDoctor().getId());
            ps.setInt(2, patient.getId());
            ps.executeUpdate();
            return patient;
        } catch (SQLException e) {
            logger.error("Can not execute query", e);
            throw new SQLException("Can not execute query");
        }
    }


    private Integer getLastInsertId() throws SQLException {
        try (PreparedStatement ps = getPrepareStatement(SELECT_LAST_ID)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Integer result = rs.getInt("MAX( id )");
                return result;
            }
        } catch (SQLException e) {
            logger.error("Can't execute query", e);
            throw new SQLException("Can't execute query");
        }
        logger.error("Can't find the id");
        throw new SQLException("Can't find the id");
    }

    public List<Patient> getAllSortedPatient(String sort) throws SQLException {
        List<Patient> patientList = new ArrayList<>();
        sort = patientSort(sort);
        try (PreparedStatement ps = getPrepareStatement(SELECT_ALL_PATIENT + sort)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Patient patient = new Patient();
                patient.setId(rs.getInt("id"));
                patient.setFirstName(rs.getString("first_name"));
                patient.setLastName(rs.getString("last_name"));
                patient.setAge(rs.getDate("age"));
                patient.setGender(rs.getString("gender"));
                patient.setStatus(rs.getString("status"));
                DoctorController doctorController = new DoctorController();
                if (rs.getInt("doctor_id") != 0) {
                    patient.setDoctor(doctorController.getEntityById(rs.getInt("doctor_id")));
                } else {
                    patient.setDoctor(null);
                }
                patientList.add(patient);
            }
        } catch (SQLException e) {
            logger.error("Can't execute query", e);
            throw new SQLException("Can't execute query");
        }
        return patientList;
    }


    private String patientSort(String sort) {
        if (sort.equals("alphabet")) {
            return "ORDER BY first_name";
        }
        if (sort.equals("date")) {
            return "ORDER BY age";
        } else {
            return "ORDER BY first_name";
        }
    }

    public boolean transactionPatientCreate(Patient patient) throws SQLException {
        try {
            getConnection().setAutoCommit(false);
            if (!insert(patient)) {
                throw new SQLException();
            }
            Integer patientId = getLastInsertId();
            patient.setId(patientId);
            MedicalFileController medicalFileController = new MedicalFileController();
            MedicalFile medicalFile = new MedicalFile();
            medicalFile.setPatient(patient);
            medicalFileController.insert(medicalFile);
            getConnection().commit();
            getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            getConnection().rollback();
            getConnection().setAutoCommit(true);
            logger.error("Can't execute transaction, rollback...", e);
            throw new SQLException("Can't execute transaction");
        }
        return true;
    }
}
