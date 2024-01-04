package vn.aptech.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDb {
	
	public static Connection getConnectMySQL(){	
		String url = "jdbc:mysql://localhost:3306/ProductDb"; //jdbc:mysql://127.0.0.1:3306/...
		String username = "root";
		String password = "1235";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		    return DriverManager.getConnection(url, username, password);
		}catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
		return null;
	}
}
