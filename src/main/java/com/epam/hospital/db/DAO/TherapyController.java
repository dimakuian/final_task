package com.epam.hospital.db.DAO;

import com.epam.hospital.db.DAO.config.MedicalFileController;
import com.epam.hospital.db.entity.Staff;
import com.epam.hospital.db.entity.Therapy;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TherapyController extends AbstractController<Therapy, Integer> {

    private static final Logger logger = Logger.getLogger(TherapyController.class);

    public static final String SELECT_ALL_THERAPY = "SELECT * FROM therapy";

    public static final String SELECT_THERAPY_BY_ID = "SELECT * FROM therapy WHERE id= ?";

    public static final String INSERT_THERAPY = "INSERT INTO therapy (title, type, status, medical_file_id, staff_id) "
            + "VALUES (?,?,?,?,?)";

    public static final String DONE_THERAPY = "UPDATE therapy SET status = 'done', staff_id = ? WHERE (id = ?)";

    public static final String SELECT_ALL_THERAPY_NO_OPERATION =
            "SELECT * FROM therapy WHERE status='in progress' and type!='operation'";

    public static final String SELECT_COUNT_OF_OPERATIONS =
            "SELECT COUNT(id) as count FROM therapy WHERE `type` = 'operation' and `status` = 'in progress'";


    public TherapyController() throws SQLException {
    }


    public TherapyController(Connection connection, PoolConnectionBuilder poolConnectionBuilder) {
        super(connection, poolConnectionBuilder);
    }


    @Override
    public List<Therapy> getAll() throws SQLException {
        List<Therapy> therapyList = new ArrayList<>();
        try (PreparedStatement ps = getPrepareStatement(SELECT_ALL_THERAPY)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Therapy therapy = new Therapy();
                therapy.setId(rs.getInt("id"));
                therapy.setTitle(rs.getString("title"));
                therapy.setType(rs.getString("type"));
                therapy.setStatus(rs.getString("status"));
                MedicalFileController mfc = new MedicalFileController();
                therapy.setMedicalFile(mfc.getEntityById(rs.getInt("medical_file_id")));
                StaffController staffController = new StaffController();
                therapy.setStaff(staffController.getEntityById(rs.getInt("staff_id")));
                therapyList.add(therapy);
            }
        } catch (SQLException e) {
            logger.error("Can't execute query", e);
            throw new SQLException("Can't execute query");
        }
        return therapyList;
    }


    @Override
    public Therapy update(Therapy entity) throws SQLException {
        logger.info("this empty");
        return null;
    }


    @Override
    public Therapy getEntityById(Integer id) throws SQLException {
        try (PreparedStatement ps = getPrepareStatement(SELECT_THERAPY_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Therapy therapy = new Therapy();
                therapy.setId(rs.getInt("id"));
                therapy.setTitle(rs.getString("title"));
                therapy.setType(rs.getString("type"));
                therapy.setStatus(rs.getString("status"));
                MedicalFileController mfc = new MedicalFileController();
                therapy.setMedicalFile(mfc.getEntityById(rs.getInt("medical_file_id")));
                StaffController staffController = new StaffController();
                therapy.setStaff(staffController.getEntityById(rs.getInt("staff_id")));
                return therapy;
            }
        } catch (SQLException e) {
            logger.error("Can't' execute query", e);
            throw new SQLException("Can't execute query");
        }
        logger.error("Can't find the therapy");
        throw new SQLException("Can't find the therapy");
    }


    @Override
    public boolean delete(Integer id) throws SQLException {
        logger.info("this empty");
        return false;
    }


    @Override
    public boolean insert(Therapy therapy) throws SQLException {
        try (PreparedStatement ps = getPrepareStatement(INSERT_THERAPY)) {
            ps.setString(1, therapy.getTitle());
            ps.setString(2, therapy.getType());
            ps.setString(3, therapy.getStatus());
            ps.setInt(4, therapy.getMedicalFile().getId());
            ps.setInt(5, therapy.getStaff().getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            logger.error("Can't' execute query", e);
            throw new SQLException("Can't execute query");
        }
    }


    public void doneTherapy(Therapy therapy, Staff staff) throws SQLException {
        try (PreparedStatement ps = getPrepareStatement(DONE_THERAPY)) {
            ps.setInt(1, staff.getId());
            ps.setInt(2, therapy.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't' execute query", e);
            throw new SQLException("Can't execute query");
        }
    }


    public List<Therapy> getAllNoOperation() throws SQLException {
        List<Therapy> therapyList = new ArrayList<>();
        try (PreparedStatement ps = getPrepareStatement(SELECT_ALL_THERAPY_NO_OPERATION)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Therapy therapy = new Therapy();
                therapy.setId(rs.getInt("id"));
                therapy.setTitle(rs.getString("title"));
                therapy.setType(rs.getString("type"));
                therapy.setStatus(rs.getString("status"));
                MedicalFileController mfc = new MedicalFileController();
                therapy.setMedicalFile(mfc.getEntityById(rs.getInt("medical_file_id")));
                StaffController staffController = new StaffController();
                therapy.setStaff(staffController.getEntityById(rs.getInt("staff_id")));
                therapyList.add(therapy);
            }
        } catch (SQLException e) {
            logger.error("Can't' execute query", e);
            throw new SQLException("Can't execute query");
        }
        return therapyList;
    }

    public Integer getCountOfOperations() throws SQLException {
        try (PreparedStatement ps = getPrepareStatement(SELECT_COUNT_OF_OPERATIONS)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Integer count = rs.getInt("count");
                return count;
            }
        } catch (SQLException e) {
            logger.error("Can't' execute query", e);
            throw new SQLException("Can't execute query");
        }
        logger.error("Can't find any operation");
        throw new SQLException("Can't find any operation");
    }
}
