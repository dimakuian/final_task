package com.epam.hospital.db.DAO;

import com.epam.hospital.db.entity.Staff;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StaffController extends AbstractController < Staff, Integer > {

    public static final Logger logger = Logger.getLogger(StaffController.class);

    public static final String SELECT_STAFF_BY_ID = "SELECT * FROM staff WHERE id = ?";

    public static final String INSERT_STAFF = "INSERT INTO staff (user_id) VALUE (?)";

    public static final String SELECT_STAFF_BY_USER_ID =
            "SELECT s.id, s.user_id FROM staff s join `user` u on s.user_id=u.id WHERE u.id = ?";

    public StaffController() throws SQLException {}

    public StaffController(Connection connection, PoolConnectionBuilder poolConnectionBuilder) {
        super(connection, poolConnectionBuilder);
    }


    @Override
    public List < Staff > getAll() throws SQLException {
        logger.info("this empty");
        return null;
    }


    @Override
    public Staff update(Staff entity) throws SQLException {
        logger.info("this empty");
        return null;
    }


    @Override
    public Staff getEntityById(Integer id) throws SQLException {
        try (PreparedStatement ps = getPrepareStatement(SELECT_STAFF_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Staff staff = new Staff();
                UserController uc = new UserController();
                staff.setId(id);
                staff.setUser(uc.getEntityById(rs.getInt("user_id")));
                return staff;
            }
        } catch (SQLException e) {
            logger.error("Can't execute query", e);
            throw new SQLException("Can't execute query");
        }
        logger.error("Can't find the staff");
        throw new SQLException("Can't find the staff");
    }


    @Override
    public boolean delete(Integer id) throws SQLException {
        logger.info("this empty");
        return false;
    }


    @Override
    public boolean insert(Staff entity) throws SQLException {
        try (PreparedStatement ps = getPrepareStatement(INSERT_STAFF)) {
            ps.setInt(1, entity.getUser().getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            logger.error("Can not execute query", e);
            throw new SQLException("Can not execute query", e);
        }
    }


    public Staff getEntityByUserId(Integer id) throws SQLException {
        try (PreparedStatement ps = getPrepareStatement(SELECT_STAFF_BY_USER_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Staff staff = new Staff();
                UserController userController = new UserController();
                staff.setId(rs.getInt("id"));
                staff.setUser(userController.getEntityById(id));
                return staff;
            }
        } catch (SQLException e) {
            logger.error("Can't execute query", e);
            throw new SQLException("Can't execute query");
        }
        logger.error("Can't find the staff");
        throw new SQLException("Can't find the staff");
    }
}