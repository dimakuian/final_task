package com.epam.hospital.db.DAO.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Config {

    public static final String DB_URL = "db.url";

    public static final String DB_LOGIN = "db.login";

    public static final String DB_PASSWORD = "db.password";

    private static Properties properties = new Properties();

    public static synchronized String getProperty(String name) {
        if (properties.isEmpty()) {
            try (InputStream in = Files.newInputStream(Paths.get("dao.properties"))) {
                properties.load(in);
            } catch (IOException exception) {
                System.err.println("Cannot find file.");
            }
        }
        return properties.getProperty(name);
    }

}
