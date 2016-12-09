package sqlbase;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnect {

    private Connection connection;

    public DBconnect() throws SQLException {
        DriverManager.registerDriver(new FabricMySQLDriver());
    }

    public Connection conn(String url, String user, String pass) throws SQLException {
        if (connection != null)
            return connection;
        connection = DriverManager.getConnection(url, user, pass);
        return connection;
    }
}