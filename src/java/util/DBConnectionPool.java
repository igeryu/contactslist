//DBConnectionPool.java
//  Adapted from the Module 4 Example
/**
 *
 * @author Alan Johnson
 */
package util;

//JDBC imports
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

//JNDI imports
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.NamingException;

public class DBConnectionPool extends Object {

    private static DBConnectionPool instance;
    private static DataSource ds = null;
    private static final String dbName = "jdbc/PollDatasource";

    //Keep this package private.
    DBConnectionPool() throws SQLException {
        init();
    }

    public static DBConnectionPool getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBConnectionPool();
        }
        return instance;
    }

    public void init() throws SQLException {

        try {
            Context intContext = new InitialContext();
            Context envContext = (Context) intContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup(dbName);
        } catch (NamingException e) {
            System.err.println(
                    "Problem looking up " + dbName + ": " + e);
        }
    }

    public static Connection getPoolConnection() throws SQLException {
        Context context = null;
        DataSource ds = null;

        try {
            context = new InitialContext();
            ds = (DataSource) context.lookup("java:comp/env/jdbc/Contacts");
        } catch (Exception e) {
            System.out.println("\nFailed.");
        }

        Connection conn = ds.getConnection();
        if (conn == null) {
            throw new SQLException(
                    "Maximum number ofconnections in pool exceeded.");
        }
        return conn;
    }
}
