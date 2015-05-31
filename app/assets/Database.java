package assets;

import models.UserLoginData;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	public String connectToDatabase(String username, String password){
		//return oracle.jdbc.OracleDriver.getDriverVersion();
		try{
			/*try{
				Class.forName("oracle.jdbc.driver.OracleDriver");
			}
			catch (Exception e){
				return oracle.jdbc.OracleDriver.getDriverVersion();
				//return oracle.jdbc.driver.OracleDriver.getDriverVersion();
			}*/
			Driver myDriver = new oracle.jdbc.driver.OracleDriver();
			DriverManager.registerDriver(myDriver);
			
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", username, password);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("Select * from emp");
			StringBuilder msg = new StringBuilder();
			
			while (rs.next()){
				Integer id = rs.getInt("empno");
				msg.append(id.toString());
			}
			return msg.toString();
		}
		catch (SQLException e){
			return e.toString();
		}
	}
}
