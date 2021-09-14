package com.epam.hospital.db.DAO;

import com.epam.hospital.db.DAO.config.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager implements ConnectionBuilder {

    public Connection getConnection() throws SQLException {
        return  DriverManager.getConnection(Config.getProperty(Config.DB_URL),
                Config.getProperty(Config.DB_LOGIN), Config.getProperty(Config.DB_PASSWORD));
    }

}
