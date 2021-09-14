package com.epam.hospital.db.DAO;

import com.epam.hospital.db.entity.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserController extends AbstractController<User, Integer> {

    public static final Logger logger = Logger.getLogger(UserController.class);

    public static final String SELECT_ALL_USERS = "SELECT * FROM user";

    public static final String SELECT_USER_BY_ID = "SELECT * FROM user WHERE id = ?";

    public static final String INSERT_USER = "INSERT INTO user (login, password, role) VALUES (?, ?, ?)";

    public static final String SELECT_USER_BY_LOGIN_AND_PASSWORD = "SELECT * FROM user WHERE login=? and password = ?";

    public static final String SELECT_DOCTOR_ID_BY_USER_ID =
            "Select d.id FROM doctor d join staff s on d.staff_id=s.id join `user` u on s.user_id=u.id WHERE u.id = ?";


    private ConnectionBuilder connectionBuilder;

    public void setConnectionBuilder(ConnectionBuilder connectionBuilder) {
        this.connectionBuilder = connectionBuilder;
    }

    public Connection getConnection() throws SQLException {
        return connectionBuilder.getConnection();
    }

    public UserController() throws SQLException {
        super();
    }


    public UserController(Connection connection, PoolConnectionBuilder poolConnectionBuilder) {
        super(connection, poolConnectionBuilder);
    }

    @Override
    public List<User> getAll() throws SQLException {
        List<User> userList = new ArrayList<>();
        try (PreparedStatement ps = getPrepareStatement(SELECT_ALL_USERS)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setLogin(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setRole(rs.getString(4));
                userList.add(user);
            }
        } catch (SQLException e) {
            logger.error("Can't' execute query", e);
            throw new SQLException("Can't execute query");
        }
        return userList;
    }


    @Override
    public User update(User entity) throws SQLException {
        logger.info("this empty");
        return null;
    }


    @Override
    public User getEntityById(Integer id) throws SQLException {
        try (PreparedStatement ps = getPrepareStatement(SELECT_USER_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                return user;
            }
        } catch (SQLException e) {
            logger.error("Can't' execute query", e);
            throw new SQLException("Can't execute query");
        }
        logger.error("Can't find the user");
        throw new SQLException("Can't find the user");
    }


    @Override
    public boolean delete(Integer id) throws SQLException {
        logger.info("this empty");
        return false;
    }


    @Override
    public boolean insert(User user) throws SQLException {
        try (PreparedStatement ps = getPrepareStatement(INSERT_USER)) {
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.execute();
            return true;
        } catch (SQLException e) {
            logger.error("Can't execute query", e);
            throw new SQLException("Can't' execute query", e);
        }
    }


    public User getUserByLoginAndPassword(String login, String password) throws SQLException {
        try (PreparedStatement ps = getPrepareStatement(SELECT_USER_BY_LOGIN_AND_PASSWORD)) {
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                return user;
            }

        } catch (SQLException e) {
            logger.error("Can not execute query", e);
            throw new SQLException("Can not execute query");
        }
        logger.error("Can't find the user");
        throw new SQLException("Can't find the user");
    }

    public Integer getDoctorByUserId(Integer id) throws SQLException {
        try (PreparedStatement ps = getPrepareStatement(SELECT_DOCTOR_ID_BY_USER_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Integer doctorId = rs.getInt("id");
                return doctorId;
            }
        } catch (SQLException e) {
            logger.error("Can not execute query", e);
            throw new SQLException("Can not execute query");
        }
        logger.error("Can't find the user");
        throw new SQLException("Can't find the user");
    }
}
