package com.epam.hospital.db.DAO;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractController<E, K> {

    private static final Logger logger = Logger.getLogger(AbstractController.class);

    private Connection connection;

    private PoolConnectionBuilder poolConnectionBuilder;

    private ConnectionBuilder connectionBuilder;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public AbstractController() throws SQLException {
        DBManager dbManager = new DBManager();
        connection = dbManager.getConnection();
//          poolConnectionBuilder = PoolConnectionBuilder.getInstance();
//        try {
//            connection = poolConnectionBuilder.getConnection();
//        } catch (SQLException e) {
//            logger.error("Something wrong with Connection", e);
//            throw new SQLException();
//        }
    }

    public AbstractController(Connection connection, PoolConnectionBuilder poolConnectionBuilder) {
        this.connection = connection;
        this.poolConnectionBuilder = poolConnectionBuilder;
    }

    public Connection getConnection() throws SQLException {
        return connection;
    }

    public PoolConnectionBuilder getPoolConnectionBuilder() {
        return poolConnectionBuilder;
    }


    public abstract List<E> getAll() throws SQLException;

    public abstract E update(E entity) throws SQLException;

    public abstract E getEntityById(K id) throws SQLException;

    public abstract boolean delete(K id) throws SQLException;

    public abstract boolean insert(E entity) throws SQLException;

    public void returnConnectionInPool() throws SQLException {
        poolConnectionBuilder.returnConnection(connection);
    }

    public PreparedStatement getPrepareStatement(String sql) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
        } catch (SQLException e) {
            logger.error("Can not prepapre statement", e);
            throw new SQLException();
        }

        return ps;
    }

    public void closePrepareStatement(PreparedStatement ps) throws SQLException {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                logger.error("Can not close prepapre statement", e);
                throw new SQLException();
            }
        }
    }
}
