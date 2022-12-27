package action;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/*
The purpose of this class is to connect to the database.

*/
public class PartConnection {
    public PartConnection() {
    }
    public Connection connect() {

        String connectionUrl = "jdbc:sqlserver://10.11.12.120\\MSSQLSERVER2016;" +
                "databaseName=devcsep;" +
                "user=CseppUser;" +
                "password=Cj9V?BcEh@vu4Dk;" +
                "encrypt=false;" +
                "trustServerCertificate=true;" +
                "loginTimeout=45;";

        //OutMsg.disp(connectionUrl);

        Connection connection;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e1) {
            throw new RuntimeException(e1);
        }
        try {
            connection = DriverManager.getConnection(connectionUrl);
        } catch (SQLException e2) {
            throw new RuntimeException(e2);
        }
        try {
            OutMsg.disp("Successfully connected to " + connection.getCatalog());
        } catch (SQLException e3) {
            throw new RuntimeException(e3);
        }
        return connection;
    }
}