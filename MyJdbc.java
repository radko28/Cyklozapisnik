/*
MyJdbc.java
*/
import java.sql.*;


public class MyJdbc  {



  public MyJdbc()  {
  	try {
  		Class.forName("com.mysql.jdbc.Driver").newInstance();
	} catch(Exception e) {
		System.out.println(e.getMessage());
      		e.printStackTrace();
	}
  }
  public Connection getConnection() {

  	try {
		return DriverManager.getConnection(ConfClass.DBSERVER+"?user="+ConfClass.USERID+"&password="+ConfClass.PASSWORD);
//		return DriverManager.getConnection("jdbc:mysql://db.host.sk/cvicenie?user="+userid+"&password="+password);
	} catch(SQLException e) {
//                System.out.println("jdbc:mysql://db.host.sk/cvicenie?user="+userid+"&password="+password);
		System.out.println(e.getMessage());
      		e.printStackTrace();
	}
	return null;

  }
}