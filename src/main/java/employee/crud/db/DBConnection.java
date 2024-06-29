package employee.crud.db;

import java.sql.Connection;

import java.sql.DriverManager;

public class DBConnection {

    public static final String dbURL = "jdbc:mysql://localhost:3306/employee_db";
    public static final String dbDriver = "com.mysql.cj.jdbc.Driver";
    public static final String dbUserName = "root";
    public static final String dbRoot = "R00t@123";
    public static Connection connection = null;

    public static Connection getConnection() {

        try {
            Class.forName(dbDriver);
            connection =  DriverManager.getConnection(dbURL, dbUserName, dbRoot);
            if (connection != null) {
                return connection;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
