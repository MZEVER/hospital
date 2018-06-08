package servlet;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class DBUtil {
    public static final String TABLE_paccount = "paccount";
    public static final String TABLE_daccount = "daccount";  
  
    // connect to MySql database  
    public static Connection getConnect() {  
        String url = "jdbc:mysql://106.14.219.246:3306/hospital"; // ���ݿ��Url  
        Connection connecter = null;  
        try {  
            Class.forName("com.mysql.jdbc.Driver"); // java���䣬�̶�д��  
            connecter = (Connection) DriverManager.getConnection(url, "root", "339816aA!");  
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
        } catch (SQLException e) {  
            System.out.println("SQLException: " + e.getMessage());  
            System.out.println("SQLState: " + e.getSQLState());  
            System.out.println("VendorError: " + e.getErrorCode());  
        }  
        return connecter;  
    }  

}
