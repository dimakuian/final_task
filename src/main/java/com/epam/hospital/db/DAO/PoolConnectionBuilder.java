package com.epam.hospital.db.DAO;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class PoolConnectionBuilder implements ConnectionBuilder {

    private DataSource dataSource;

    private static PoolConnectionBuilder poolConnectionBuilder;

    private static final Logger logger = Logger.getLogger(PoolConnectionBuilder.class);

    public static synchronized PoolConnectionBuilder getInstance() {
        if (poolConnectionBuilder == null)
            poolConnectionBuilder = new PoolConnectionBuilder();
        return poolConnectionBuilder;
    }

    public PoolConnectionBuilder() {
        try {
            Context initContext = new InitialContext();
            dataSource = (DataSource) initContext.lookup("java:/comp/env/jdbc/hospital");
        } catch (NamingException e) {
            logger.error("Cannot obtain a connection from the pool. " + e.getMessage());
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void returnConnection(Connection con) throws SQLException {
        try {
            con.close();
        } catch (SQLException ex) {
            logger.error("Problem with rollback", ex);
            throw new SQLException("Problem with rollback");
        }
    }
}
