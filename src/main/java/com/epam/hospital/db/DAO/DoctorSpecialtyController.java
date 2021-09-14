package com.epam.hospital.db.DAO;

import com.epam.hospital.db.entity.DoctorSpecialty;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DoctorSpecialtyController extends AbstractController<DoctorSpecialty, Integer> {

    public static final Logger logger = Logger.getLogger(DoctorSpecialtyController.class);

    public static final String SELECT_SPECIALTY_BY_ID = "SELECT * FROM doctor_specialty WHERE id = ?";

    public static final String SELECT_CATEGORY_BY_TITLE = "SELECT id FROM doctor_specialty WHERE title = ?";

    public DoctorSpecialtyController() throws SQLException {
    }

    public DoctorSpecialtyController(Connection connection, PoolConnectionBuilder poolConnectionBuilder) {
        super(connection, poolConnectionBuilder);
    }

    @Override
    public List<DoctorSpecialty> getAll() throws SQLException {
        logger.info("this empty");
        return null;
    }

    @Override
    public DoctorSpecialty update(DoctorSpecialty entity) throws SQLException {
        logger.info("this empty");
        return null;
    }

    @Override
    public DoctorSpecialty getEntityById(Integer id) throws SQLException {
        try (PreparedStatement ps = getPrepareStatement(SELECT_SPECIALTY_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String title = rs.getString("title");
                DoctorSpecialty doctorSpecialty = new DoctorSpecialty();
                doctorSpecialty.setId(id);
                doctorSpecialty.setTitle(title);
                return doctorSpecialty;
            }
        } catch (SQLException e) {
            logger.error("Can't execute query", e);
            throw new SQLException("Can't execute query");
        }
        logger.error("Can't find the specialty");
        throw new SQLException("Can't find the specialty");
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        logger.info("this empty");
        return false;
    }

    @Override
    public boolean insert(DoctorSpecialty entity) throws SQLException {
        logger.info("this empty");
        return false;
    }

    public DoctorSpecialty getEntityByTitle(String title) throws SQLException {
        try (PreparedStatement ps = getPrepareStatement(SELECT_CATEGORY_BY_TITLE)) {
            ps.setString(1, title);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Integer id = rs.getInt("id");
                DoctorSpecialty doctorSpecialty = new DoctorSpecialty();
                doctorSpecialty.setId(id);
                doctorSpecialty.setTitle(title);
                return doctorSpecialty;
            }
        } catch (SQLException e) {
            logger.error("Can't execute query", e);
            throw new SQLException("Can't execute query");
        }
        logger.error("Can't find the title");
        throw new SQLException("Can't find the title");
    }
}
