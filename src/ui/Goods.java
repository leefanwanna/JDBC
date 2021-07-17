package ui;

import java.sql.Connection;
import java.sql.Statement;

public class Goods {
	private Sql sql;
	private Connection con;
	private Statement stmt = null;
	private int gid;
	private String uid;
	private String name;
	private String text;
	private String type;
	private String image;
	Goods(Sql sql,int gid,String uid,String name,String type,String text,String image) {
		this.setSql(sql);
		this.con = sql.getCon();
		this.gid = gid;
		this.uid = uid;
		this.name = name;
		this.type = type;
		this.text =  text;
		if(image != null)
			this.image = image.replaceAll("\\\\", "\\\\\\\\");//ø”À¿µ˘¡À
		else {
			this.image = null;
		}
		//System.out.println("insert into goods(gid,uid,name,text,type,image) values (\'"+gid+"\',\'"+uid+"\',\'"+name+"\',\'"+text+"\',\'"+type+"\',\'"+this.image+"\');");
	}
	public void add() {
		try {
			this.stmt = con.createStatement();
			stmt.executeUpdate("insert into goods(gid,uid,name,text,type,image) values (\'"+gid+"\',\'"+uid+"\',\'"+name+"\',\'"+text+"\',\'"+type+"\',\'"+image+"\');");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void update(String ggid) {
		try {
			this.stmt = con.createStatement();
			stmt.executeUpdate("update goods set name = \'" + name + "\' , type = " + "\'" + type +"\' , text = \'" + text +"\' , image = \'" + image +"\' where gid = \'" + ggid +"\';");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public Sql getSql() {
		return sql;
	}
	public void setSql(Sql sql) {
		this.sql = sql;
	}
}
