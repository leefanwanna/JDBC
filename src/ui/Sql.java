package ui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Sql {
	private static final String url = "jdbc:mysql://localhost:3306/pinkPig?user=root";
	private Connection con = null;
	private Statement stmt = null;
	public Connection getCon() {
		return con;
	}
	Sql() {
		try {
            //加载驱动程序
            con=DriverManager.getConnection(url);
            stmt = con.createStatement();
            this.createUserInfo();
            this.createGoods();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	private void createGoods() {
		try {
			stmt.execute("create table IF NOT EXISTS goods ("
					+ "	gid int primary key,"
					+ "	uid nchar(10)not null,"
					+ "	name nvarchar(20) not null,"
					+ "	text nvarchar(210),"
					+ "	type nchar(10),"
					+ "	image nvarchar(100),"
					+ "	foreign key(uid) references userInfo(uid),"
					+ "	check(type = '书' or type = '生活用品'or type = '文具'or type = '零食')"
					+ "	)");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void createUserInfo() {
		try {
			stmt.execute("create table IF NOT EXISTS userInfo (uid nchar(10) not null primary key,passwd nvarchar(15) not null);");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/*void test()  {
		try {
			Statement stmt = this.con.createStatement();
	        ResultSet rs = stmt.executeQuery("select id,name from tt");//选择import java.sql.ResultSet;
	        while(rs.next()) {
	        	System.out.println(rs.getInt("id")+","+rs.getString("name"));
	        }
		} catch (Exception e) {
	            e.printStackTrace();
	        }
	}*/
}
