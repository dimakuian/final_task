package com.epam.hospital.db.DAO.config;

import com.epam.hospital.db.DAO.AbstractController;
import com.epam.hospital.db.DAO.PatientController;
import com.epam.hospital.db.DAO.PoolConnectionBuilder;
import com.epam.hospital.db.entity.MedicalFile;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicalFileController extends AbstractController<MedicalFile, Integer> {

    public static final Logger logger = Logger.getLogger(MedicalFileController.class);

    public static final String SELECT_ALL_MEDICAL_FILE = "SELECT * FROM medical_file";

    public static final String SELECT_MEDICAL_FILE_BY_PATIENT_ID =
            "SELECT mf.id, mf.diagnosis, mf.patient_id FROM `medical_file` mf join patient p on mf.patient_id=p.id WHERE p.id=?";

    public static final String SELECT_MEDICAL_FILE_BY_ID = "SELECT * FROM medical_file WHERE id = ?";

    public static final String INSERT_MEDICAL_FILE = "INSERT INTO medical_file (diagnosis, patient_id) VALUES (?, ?)";

    public static final String SELECT_ALL_MEDICAL_FILE_FOR_DOCTOR =
            "SELECT mf.id, mf.diagnosis, mf.patient_id FROM `medical_file` mf " +
                    "JOIN patient p on mf.patient_id=p.id join doctor d on p.doctor_id=d.id " +
                    "WHERE d.id=? and p.status!='Discharged from the hospital'";

    public static final String SELECT_ALL_PATIENT_MEDICAL_FILE_WITHOUT_DOCTOR =
            "SELECT mf.id, mf.diagnosis, mf.patient_id FROM medical_file mf " +
                    "JOIN patient p on mf.patient_id=p.id " +
                    "WHERE p.doctor_id is null and p.status!='Discharged from the hospital'";

    public static final String UPDATE_MEDICAL_FILE =
            "UPDATE `medical_file` SET `diagnosis` = ? WHERE (`patient_id` = ?)";

    public MedicalFileController() throws SQLException {
    }

    public MedicalFileController(Connection connection, PoolConnectionBuilder poolConnectionBuilder) {
        super(connection, poolConnectionBuilder);
    }

    @Override
    public List<MedicalFile> getAll() throws SQLException {
        List<MedicalFile> medicalFiles = new ArrayList<>();
        try (PreparedStatement ps = getPrepareStatement(SELECT_ALL_MEDICAL_FILE)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MedicalFile medicalFile = new MedicalFile();
                medicalFile.setId(rs.getInt("id"));
                medicalFile.setDiagnosis(rs.getString("diagnosis"));
                PatientController patientController = new PatientController();
                medicalFile.setPatient(patientController.getEntityById(rs.getInt("patient_id")));
                medicalFiles.add(medicalFile);
            }
        } catch (SQLException e) {
            logger.error("Can't' execute query", e);
            throw new SQLException("Can't execute query");
        }
        return medicalFiles;
    }

    @Override
    public MedicalFile update(MedicalFile medicalFile) throws SQLException {
        try (PreparedStatement ps = getPrepareStatement(UPDATE_MEDICAL_FILE)) {
            ps.setString(1, medicalFile.getDiagnosis());
            ps.setInt(2, medicalFile.getPatient().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can not execute query", e);
            throw new SQLException("Can not execute query");
        }
        return medicalFile;
    }

    @Override
    public MedicalFile getEntityById(Integer id) throws SQLException {
        try (PreparedStatement ps = getPrepareStatement(SELECT_MEDICAL_FILE_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                MedicalFile medicalFile = new MedicalFile();
                medicalFile.setId(id);
                medicalFile.setDiagnosis(rs.getString("diagnosis"));
                PatientController patientController = new PatientController();
                medicalFile.setPatient(patientController.getEntityById(rs.getInt("patient_id")));
                return medicalFile;
            }
        } catch (SQLException e) {
            logger.error("Can't' execute query", e);
            throw new SQLException("Can't execute query");
        }
        logger.error("Can't find the medical file");
        throw new SQLException("Can't find the medical file");
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        logger.info("this empty");
        return false;
    }

    @Override
    public boolean insert(MedicalFile medicalFile) throws SQLException {
        try (PreparedStatement ps = getPrepareStatement(INSERT_MEDICAL_FILE)) {
            ps.setString(1, medicalFile.getDiagnosis());
            ps.setInt(2, medicalFile.getPatient().getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            logger.error("Can't execute query", e);
            throw new SQLException("Can't' execute query", e);
        }
    }

    public MedicalFile getEntityByPatientId(Integer id) throws SQLException {
        try (PreparedStatement ps = getPrepareStatement(SELECT_MEDICAL_FILE_BY_PATIENT_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                MedicalFile medicalFile = new MedicalFile();
                medicalFile.setId(rs.getInt("id"));
                medicalFile.setDiagnosis(rs.getString("diagnosis"));
                PatientController patientController = new PatientController();
                medicalFile.setPatient(patientController.getEntityById(id));
                return medicalFile;
            }
        } catch (SQLException e) {
            logger.error("Can not execute query", e);
            throw new SQLException("Can not execute query");
        }
        logger.error("Can't find the medical file");
        throw new SQLException("Can't find the medical file");
    }


    public List<MedicalFile> getAllMedicalFileForDoctor(Integer doctorId) throws SQLException {
        List<MedicalFile> medicalFiles = new ArrayList<>();
        try (PreparedStatement ps = getPrepareStatement(SELECT_ALL_MEDICAL_FILE_FOR_DOCTOR)) {
            ps.setInt(1, doctorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MedicalFile medicalFile = new MedicalFile();
                medicalFile.setId(rs.getInt("id"));
                medicalFile.setDiagnosis(rs.getString("diagnosis"));
                PatientController patientController = new PatientController();
                medicalFile.setPatient(patientController.getEntityById(rs.getInt("patient_id")));
                medicalFiles.add(medicalFile);
            }
        } catch (SQLException e) {
            logger.error("Can't' execute query", e);
            throw new SQLException("Can't execute query");
        }
        return medicalFiles;
    }


    private String patientSort(String sort) {
        if (sort.equals("alphabet")) {
            return "ORDER BY p.first_name";
        }
        if (sort.equals("date")) {
            return "ORDER BY p.age";
        } else {
            return "ORDER BY p.first_name";
        }
    }


    public List<MedicalFile> getAllMedicalFileForDoctorSort(Integer doctorId, String sort) throws SQLException {
        List<MedicalFile> medicalFiles = new ArrayList<>();
        sort = patientSort(sort);
        try (PreparedStatement ps = getPrepareStatement(SELECT_ALL_MEDICAL_FILE_FOR_DOCTOR + sort)) {
            ps.setInt(1, doctorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MedicalFile medicalFile = new MedicalFile();
                medicalFile.setId(rs.getInt("id"));
                medicalFile.setDiagnosis(rs.getString("diagnosis"));
                PatientController patientController = new PatientController();
                medicalFile.setPatient(patientController.getEntityById(rs.getInt("patient_id")));
                medicalFiles.add(medicalFile);
            }
        } catch (SQLException e) {
            logger.error("Can't' execute query", e);
            throw new SQLException("Can't execute query");
        }
        return medicalFiles;
    }

    public List<MedicalFile> getAllMedicalFileWithoutDoctorSort(String sort) throws SQLException {
        List<MedicalFile> medicalFiles = new ArrayList<>();
        sort = patientSort(sort);
        try (PreparedStatement ps = getPrepareStatement(SELECT_ALL_PATIENT_MEDICAL_FILE_WITHOUT_DOCTOR + sort)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MedicalFile medicalFile = new MedicalFile();
                medicalFile.setId(rs.getInt("id"));
                medicalFile.setDiagnosis(rs.getString("diagnosis"));
                PatientController patientController = new PatientController();
                medicalFile.setPatient(patientController.getEntityById(rs.getInt("patient_id")));
                medicalFiles.add(medicalFile);
            }
        } catch (SQLException e) {
            logger.error("Can't' execute query", e);
            throw new SQLException("Can't execute query");
        }
        return medicalFiles;
    }
}
