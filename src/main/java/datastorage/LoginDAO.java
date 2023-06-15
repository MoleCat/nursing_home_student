package datastorage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Implements the Interface <code>DAOImp</code>. Overrides methods to generate specific UserAuthorise-SQL-queries.
 */
public class LoginDAO{

    protected Connection conn;

    /**
     * constructs Onbject. Calls the Constructor from <code>DAOImp</code> to store the connection.
     * @param conn
     */
    public LoginDAO(Connection conn) {
        this.conn = conn;
    }
/**
 * Takes the password from the Login_Data table, using the loginName
 * @param loginName
 */
    public String getPasswordFromTable(String loginName) throws SQLException{
        Statement st = conn.createStatement();

        ResultSet result = st.executeQuery(String.format("SELECT password FROM login_Data WHERE loginname = '%s'", loginName));
        String s = "";
        while(result.next()){
            s = result.getString(1);
        }
        return s;
    }

}
