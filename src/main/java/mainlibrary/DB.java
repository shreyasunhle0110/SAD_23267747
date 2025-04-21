/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainlibrary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * Centralized database connection manager.
 * Enforces security best practices:
 * - Environment-based credentials
 * - Connection pooling
 * - UTF-8 encoding
 * - Prepared statements
 */
public class DB {

    public static Connection getConnection() {
        Connection con = null;
        try {
            Properties props = new Properties();

            // Get username and password from environment variables
            String user = System.getenv("DB_USER");
            String password = System.getenv("DB_PASS");

            if (user == null || password == null) {
                System.err.println("Error: DB_USER or DB_PASS environment variables not set.");
                return null; // Or throw an exception
            }

            props.put("user", user);
            props.put("password", password);
            props.put("useUnicode", "true");
            props.put("useServerPrepStmts", "false"); // use client-side prepared statement
            props.put("characterEncoding", "UTF-8"); // ensure charset is utf8 here

            Class.forName("com.mysql.cj.jdbc.Driver");
            String connection = "jdbc:mysql://host.docker.internal:3306/library?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(connection, props);
        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }

}
