package ui;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.MatteBorder;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class RegisterPage extends JPanel {
	private LoginPage lpg;
	private Sql sql;
	private Connection con;
	private JTextField id = new JTextField();
	private JPasswordField pw1 =  new JPasswordField();
	private JPasswordField pw2 = new JPasswordField();
	private JLabel msg1 = new JLabel("*\u8D26\u53F7\u5DF2\u88AB\u6CE8\u518C");
	private JLabel msg2 = new JLabel("*\u5BC6\u7801\u4E3A\u7A7A");
	private JLabel msg3 = new JLabel("*\u5BC6\u7801\u4E0D\u4E00\u81F4");
	private JLabel registerOK = new JLabel("\u6CE8\u518C\u6210\u529F");
	private JLabel lb1 = new JLabel("\u65B0 \u8D26 \u53F7:");
	private JLabel lb2 = new JLabel("\u5BC6    \u7801\uFF1A");
	private JLabel lb3 = new JLabel("\u786E\u8BA4\u5BC6\u7801\uFF1A");
	private final JLabel returnOK = new JLabel("\u70B9\u51FB\u8FD4\u56DE\u767B\u9646");
	private final JLabel exitPG = new JLabel("\u8FD4\u56DE\u767B\u5F55");
	private JButton btn_register  = new JButton("\u6CE8  \u518C");

	/**
	 * Create the panel.
	 */
	public RegisterPage(LoginPage lpg) {
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				returnOK.setForeground(Color.LIGHT_GRAY);
				exitPG.setFont(new Font("宋体", Font.ITALIC, 12));
				exitPG.setForeground(Color.LIGHT_GRAY);
			}
		});
		this.lpg = lpg;
		initPage();
		this.sql = new Sql();
		this.con = sql.getCon();
		exitPG.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lpg.getContentPane().add(lpg.getBg());
			}
		});
		exitPG.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				exitPG.setForeground(Color.WHITE);
				exitPG.setFont(new Font("宋体", Font.BOLD, 12));
			}
		});
		exitPG.setFont(new Font("宋体", Font.ITALIC, 12));
		exitPG.setForeground(Color.LIGHT_GRAY);
		exitPG.setBounds(25, 163, 55, 15);
		
	}
	private void flushLabel() {
		validate();
		repaint();//flush label
	}
	private void isOkPage() {
		registerOK.setBackground(Color.BLACK);
		registerOK.setForeground(Color.LIGHT_GRAY);
		registerOK.setHorizontalAlignment(SwingConstants.CENTER);
		registerOK.setFont(new Font("微軟正黑體 Light", Font.BOLD, 20));
		registerOK.setBounds(105, 62, 131, 41);
		returnOK.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lpg.getContentPane().add(lpg.getBg());
				removeAll();
				add(lb1);
				add(id);
				add(pw1);
				add(pw2);
				add(lb2);
				add(lb3);
				add(btn_register);
				add(exitPG);
			}
		});
		returnOK.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				returnOK.setForeground(Color.pink);
			}
		});
		
		returnOK.setForeground(Color.LIGHT_GRAY);
		returnOK.setHorizontalAlignment(SwingConstants.CENTER);
		returnOK.setFont(new Font("微軟正黑體 Light", Font.BOLD, 18));
		returnOK.setBounds(95, 99, 140, 41);
		removeAll();
		add(this.registerOK);
		add(this.returnOK);
		flushLabel();
	}
	public void initPage() {
		
		MatteBorder border = new MatteBorder(1, 1, 1, 1, Color.black);
		setBackground(Color.BLACK);
		//lb1 = new JLabel("\u65B0 \u8D26 \u53F7:");
		lb1.setBounds(37, 20, 74, 32);
		lb1.setForeground(Color.WHITE);
		lb1.setFont(new Font("楷体", Font.BOLD, 14));
		id.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				
			}
		});
		
		//id = new JTextField();
		id.setBounds(110, 25, 162, 24);
		id.setFont(new Font("等线 Light", Font.BOLD, 16));
		id.setColumns(10);
		id.setBorder(border);
		pw1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				String uid = id.getText();
				remove(msg1);
				flushLabel();
				try {
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery("select uid from userInfo;");
					while(rs.next()) {
						if(rs.getString(1).equals(uid)) {
							add(msg1);
							flushLabel();
						}
					}
				} catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		//pw1 = new JPasswordField();
		pw1.setBounds(110, 65, 162, 24);
		pw1.setFont(new Font("等线 Light", Font.BOLD, 16));
		pw1.setBorder(border);
		pw2.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				String uid = id.getText();
				remove(msg1);
				flushLabel();
				try {
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery("select uid from userInfo;");
					while(rs.next()) {
						if(rs.getString(1).equals(uid)) {
							add(msg1);
							flushLabel();
						}
					}
				} catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		//pw2 = new JPasswordField();
		pw2.setBounds(110, 105, 162, 24);
		pw2.setFont(new Font("等线 Light", Font.BOLD, 16));
		pw2.setBorder(border);
		
		//lb2 = new JLabel("\u5BC6    \u7801\uFF1A");
		lb2.setBounds(37, 65, 74, 25);
		lb2.setFont(new Font("楷体", Font.BOLD, 14));
		lb2.setForeground(Color.WHITE);
		
		//lb3 = new JLabel("\u786E\u8BA4\u5BC6\u7801\uFF1A");
		lb3.setBounds(37, 100, 74, 34);
		lb3.setForeground(Color.WHITE);
		lb3.setFont(new Font("楷体", Font.BOLD, 14));
		
		//btn_register = new JButton("\u6CE8  \u518C");
		btn_register.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String uid = id.getText();
				String pwd1 = new String(pw1.getPassword());
				String pwd2 = new String(pw2.getPassword());
				remove(msg1);
				remove(msg2);
				remove(msg3);
				try {
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery("select uid from userInfo;");
					while(rs.next()) {
						if(rs.getString(1).equals(uid)) {
							add(msg1);
							flushLabel();
							return ;
						}
					}
					if(pwd1.length() <= 0) {
						add(msg2);
						flushLabel();
						return ;
					}
					if(!pwd1.equals(pwd2)) {
						add(msg3);
						flushLabel();
						return ;
					}
					
					stmt.executeUpdate("insert into userInfo(uid,passwd) values (\'"+uid+"\',\'"+pwd1+"\');");
					isOkPage();
				} catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_register.setBackground(Color.WHITE);
		btn_register.setBounds(127, 149, 100, 34);
		btn_register.setFont(new Font("微軟正黑體 Light", Font.BOLD, 18));
		btn_register.setFocusPainted(false);
		btn_register.setBorder(border);
		
		//msg1 = new JLabel("\u8D26\u53F7\u5DF2\u88AB\u6CE8\u518C");
		msg1.setBounds(282, 20, 86, 32);
		msg1.setForeground(new Color(204, 51, 51));
		msg1.setFont(new Font("微軟正黑體", Font.BOLD, 10));
		
		
		//msg3 = new JLabel("\u5BC6\u7801\u4E0D\u4E00\u81F4");
		msg3.setBounds(282, 110, 83, 15);
		msg3.setForeground(new Color(204, 51, 51));
		msg3.setFont(new Font("微軟正黑體", Font.BOLD, 10));
		
		//msg2 = new JLabel("\u5BC6\u7801\u4E3A\u7A7A");
		msg2.setBounds(282, 70, 58, 15);
		msg2.setForeground(new Color(204, 51, 51));
		msg2.setFont(new Font("微軟正黑體", Font.BOLD, 10));
		setLayout(null);
		add(lb1);
		add(id);
		add(pw1);
		add(pw2);
		add(lb2);
		add(lb3);
		add(btn_register);
		add(exitPG);
		//add(msg1);
		//add(msg2);
		//add(msg3);
	}
}
