package com.epam.hospital.db.DAO;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionBuilder
{
    Connection getConnection()throws SQLException;
}
