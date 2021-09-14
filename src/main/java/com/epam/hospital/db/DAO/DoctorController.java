package com.epam.hospital.db.DAO;

import com.epam.hospital.db.entity.Doctor;
import com.epam.hospital.db.entity.DoctorSpecialty;
import com.epam.hospital.db.entity.Staff;
import com.epam.hospital.db.entity.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorController extends AbstractController<Doctor, Integer> {

    private static final Logger logger = Logger.getLogger(DoctorController.class);

    public static final String SELECT_ALL_DOCTOR = "SELECT * FROM doctor";

    public static final String SELECT_DOCTOR_BY_ID = "SELECT * FROM doctor WHERE id=?";

    public static final String INSERT_DOCTOR =
            "INSERT INTO doctor (first_name, last_name, age, gender, staff_id, specialty_id) VALUES (?, ?, ?, ?, ?, ?)";

    public static final String SELECT_ALL_DOCTORS_WITH_COUNT =
            "SELECT d.id, d.first_name, d.last_name, d.age, d.gender, d.staff_id, d.specialty_id," +
                    "(SELECT count(id) FROM patient WHERE patient.doctor_id=d.id) as NumberOfPatients " +
                    "FROM doctor d join doctor_specialty ds on d.specialty_id=ds.id ";

    public static final String SELECT_LAST_ID = "SELECT MAX( id ) FROM doctor";

    public DoctorController() throws SQLException {
    }

    public DoctorController(Connection connection, PoolConnectionBuilder poolConnectionBuilder) {
        super(connection, poolConnectionBuilder);
    }

    @Override
    public List<Doctor> getAll() throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        try (PreparedStatement ps = getPrepareStatement(SELECT_ALL_DOCTOR)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Doctor doctor = new Doctor();
                doctor.setId(rs.getInt(1));
                Integer doctorID = rs.getInt(1);
                doctor.setFirstName(rs.getString(2));
                doctor.setLastName(rs.getString(3));
                doctor.setAge(rs.getDate(4));
                doctor.setGender(rs.getString(5));
                StaffController staffController = new StaffController();
                Integer staffID = rs.getInt(6);
                Staff staff = staffController.getEntityById(staffID);
                doctor.setStaff(staff);
                DoctorSpecialty doctorSpecialty = null;
                if (rs.getInt(7) != 0) {
                    DoctorSpecialtyController dsc = new DoctorSpecialtyController();
                    Integer doctorSpecialtyId = rs.getInt(7);
                    doctorSpecialty = dsc.getEntityById(doctorSpecialtyId);
                }
                doctor.setDoctorSpecialty(doctorSpecialty);
                doctors.add(doctor);
            }
        } catch (SQLException e) {
            logger.error("Can't' execute query", e);
            throw new SQLException("Can't execute query");
        }
        return doctors;
    }

    @Override
    public Doctor update(Doctor entity) throws SQLException {
        logger.info("this empty");
        return null;
    }


    @Override
    public Doctor getEntityById(Integer id) throws SQLException {
        try (PreparedStatement ps = getPrepareStatement(SELECT_DOCTOR_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                Date age = rs.getDate("age");
                String gender = rs.getString("gender");
                Integer staffId = rs.getInt("staff_id");
                Integer doctorSpecialtyId = rs.getInt("specialty_id");
                Doctor doctor = new Doctor();
                doctor.setId(id);
                doctor.setFirstName(firstName);
                doctor.setLastName(lastName);
                doctor.setAge(age);
                doctor.setGender(gender);
                StaffController staffController = new StaffController();
                doctor.setStaff(staffController.getEntityById(staffId));
                DoctorSpecialty doctorSpecialty = null;
                if (doctorSpecialtyId != 0) {
                    DoctorSpecialtyController dsc = new DoctorSpecialtyController();
                    doctorSpecialty = dsc.getEntityById(doctorSpecialtyId);
                }
                doctor.setDoctorSpecialty(doctorSpecialty);
                return doctor;
            }
        } catch (SQLException e) {
            logger.error("Can't' execute query", e);
            throw new SQLException("Can't execute query");
        }
        logger.error("Can't find the doctor");
        throw new SQLException("Can't find the doctor");
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        logger.info("this empty");
        return false;
    }

    @Override
    public boolean insert(Doctor doctor) throws SQLException {
        try (PreparedStatement ps = getPrepareStatement(INSERT_DOCTOR)) {
            ps.setString(1, doctor.getFirstName());
            ps.setString(2, doctor.getLastName());
            ps.setDate(3, doctor.getAge());
            ps.setString(4, doctor.getGender());
            ps.setInt(5, doctor.getStaff().getId());
            ps.setInt(6, doctor.getDoctorSpecialty().getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            logger.error("Can't execute query", e);
            throw new SQLException("Can't execute query", e);
        }
    }

    public List<Doctor> getAllOrderedWithCount(String sort) throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        sort = doctorSort(sort);
        try (PreparedStatement ps = getPrepareStatement(SELECT_ALL_DOCTORS_WITH_COUNT + sort)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Doctor doctor = new Doctor();
                DoctorSpecialtyController doctorSpecialtyController = new DoctorSpecialtyController();
                StaffController staffController = new StaffController();

                Integer id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                Date date = rs.getDate("age");
                String gender = rs.getString("gender");
                Integer staffId = rs.getInt("staff_id");
                Integer specialtyId = rs.getInt("specialty_id");
                Integer count = rs.getInt("NumberOfPatients");

                doctor.setId(id);
                doctor.setFirstName(firstName);
                doctor.setLastName(lastName);
                doctor.setAge(date);
                doctor.setGender(gender);
                doctor.setStaff(staffController.getEntityById(staffId));
                doctor.setDoctorSpecialty(doctorSpecialtyController.getEntityById(specialtyId));
                doctor.setNumberOfPatients(count);
                doctors.add(doctor);
            }
        } catch (SQLException e) {
            logger.error("Can't execute query", e);
            throw new SQLException("Can't execute query");
        }
        return doctors;
    }


    private String doctorSort(String sort) {
        if (sort.equals("alphabet")) {
            return " ORDER BY d.first_name";
        }
        if (sort.equals("specialty")) {
            return " ORDER BY ds.title";
        }
        if (sort.equals("patientCount")) {
            return " ORDER BY NumberOfPatients";
        } else {
            return " ORDER BY d.first_name";
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


    public boolean transactionDoctorCreate(Doctor doctor, User user) throws SQLException {
        try {
            getConnection().setAutoCommit(false);
            UserController userController = new UserController();//connection pool
            if (!userController.insert(user)) {
                throw new SQLException();
            }
            StaffController staffController = new StaffController();
            Staff staff = new Staff();
            Integer userId = getLastInsertId();
            user.setId(userId);
            staff.setUser(user);
            if (!staffController.insert(staff)) {
                throw new SQLException();
            }
            Integer staffId = getLastInsertId();
            staff.setId(staffId);
            DoctorSpecialtyController dsc = new DoctorSpecialtyController();
            DoctorSpecialty doctorSpecialty = dsc.getEntityByTitle(doctor.getDoctorSpecialty().getTitle());
            DoctorController doctorController = new DoctorController();
            doctor.setStaff(staff);
            doctor.setDoctorSpecialty(doctorSpecialty);
            if (!doctorController.insert(doctor)) {
                throw new SQLException();
            }
            getConnection().commit();
            getConnection().setAutoCommit(true);
        } catch (SQLIntegrityConstraintViolationException e) {
            getConnection().rollback();
            getConnection().setAutoCommit(true);
            logger.error("Can't execute transaction, rollback...", e);
            logger.error("Duplicate login");
            throw new SQLIntegrityConstraintViolationException("Duplicate login, please try another one");
        } catch (SQLException e) {
            getConnection().rollback();
            getConnection().setAutoCommit(true);
            logger.error("Can't execute transaction, rollback...", e);
            throw new SQLException("Can't execute transaction");
        }
        return true;
    }
}
