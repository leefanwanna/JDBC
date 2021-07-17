package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.SpringLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.SwingConstants;
import java.awt.CardLayout;
import java.awt.Panel;
import java.awt.SystemColor;
import javax.swing.JTabbedPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JLayeredPane;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class LoginPage extends JFrame {
	private boolean pw_style = false;//密码是否可见
	private JPasswordField passwd;
	private Sql sql;
	private Connection con;
	private Statement stmt;
	private JTextField user;
	private MainPage mpg;
	private JLabel register;
	private LoginPage lpg = this;
	private RegisterPage rgpg = new RegisterPage(this);
	private Panel bg;
	private JLabel msg1;
	private JLabel msg2;

	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 */
	private void checkLoad() {
		String u = this.user.getText();
		String p = new String(this.passwd.getPassword());
		
		try {
	        ResultSet rs = stmt.executeQuery("select passwd from userInfo where uid = \'"+u+"\';");
	        bg.remove(msg1);
	        bg.remove(msg2);
	        if(!rs.next()) {
	        	user.setText(null);
	        	bg.add(msg1);
	        	flushBg();
	        } else if(!p.equals(rs.getString(1))) {
	        	passwd.setText(null);
	        	bg.add(msg2);
	        	flushBg();
	        }
        	else {
        		this.dispose();//登录成功显示主界面
        		this.mpg = new MainPage(sql,user.getText());
        		this.mpg.setVisible(true);
	        }
		} catch (Exception e) {
	            e.printStackTrace();
	        }
		return ;
	}
	private void flushBg() {
		bg.validate();
		bg.repaint();
	}
	private void flushPane() {//新增jpanel等必须重新绘制
		getContentPane().validate();//https://stackoverflow.com/questions/14874613/how-to-replace-jpanel-with-another-jpanel
		getContentPane().repaint();
	}
	public Panel getBg() {
		return bg;
	}
	/*public MainPage createMainPage() {//简便方法我不用，欸，就是玩
		this.mpg = new MainPage(sql);//date 2021/6/16 我真蠢
		this.mpg.setVisible(false);
		return this.mpg;
	}*/
	public void loadSql() {//connect database
		try {
			this.sql = new Sql();
			this.con = sql.getCon();
			this.stmt = this.con.createStatement();
		} catch (Exception e) {
	            e.printStackTrace();
	        }
	}
	public  void initPage() {
		MatteBorder border = new MatteBorder(0, 0, 1, 0, new Color(127,255,212));//输入边框格式
		MatteBorder border1 = new MatteBorder(0, 0, 2, 0, new Color(127,255,212));//输入边框格式
		setBackground(Color.DARK_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 250, 375, 250);
		
		bg = new Panel();
		bg.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				register.setForeground(Color.LIGHT_GRAY);
			}
		});
		bg.setFont(new Font("Arial", Font.PLAIN, 18));
		bg.setBackground(Color.BLACK);
		getContentPane().add(bg, BorderLayout.CENTER);
		
		JLabel logo = new JLabel("Hello");
		logo.setBounds(10, 10, 121, 47);
		logo.setForeground(SystemColor.window);
		logo.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		
		JLabel lblNewLabel = new JLabel("\u8D26 \u53F7:");
		lblNewLabel.setBounds(51, 63, 63, 24);
		lblNewLabel.setFont(new Font("楷体", Font.BOLD, 18));
		lblNewLabel.setForeground(Color.WHITE);
		
		user = new JTextField();
		user.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				bg.remove(msg1);
				flushBg();
			}
		});
		
		
		
		user.setBounds(124, 49, 170, 30);
		user.setForeground(Color.GRAY);
		user.setBackground(Color.BLACK);
		
		user.setBorder(border);
		user.setFont(new Font("等线 Light", Font.BOLD, 16));
		user.setHorizontalAlignment(SwingConstants.LEFT);
		user.setColumns(10);
		
		JLabel label = new JLabel("\u5BC6 \u7801\uFF1A");
		label.setBounds(51, 97, 72, 26);
		label.setFont(new Font("楷体", Font.BOLD, 18));
		label.setForeground(Color.WHITE);
		
		passwd = new JPasswordField();
		passwd.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {//聚焦即进行
				bg.remove(msg1);
				flushBg();
			}
		});
		
		passwd.setForeground(Color.GRAY);
		passwd.setFont(new Font("等线 Light", Font.BOLD, 16));
		passwd.setBounds(124, 89, 170, 26);
		passwd.setBackground(Color.BLACK);
		passwd.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(127, 255, 212)));
		passwd.setColumns(10);
		bg.setLayout(null);
		bg.add(logo);
		bg.add(lblNewLabel);
		bg.add(label);
		bg.add(passwd);
		bg.add(user);
		//加载铺满图片
		Image pw_hidden = new ImageIcon(LoginPage.class.getResource("/images/\u5BC6\u7801\u4E0D\u53EF\u89C1.png")).getImage().getScaledInstance(25,25,Image.SCALE_SMOOTH);
		Image pw_ = new ImageIcon(LoginPage.class.getResource("/images/\u5BC6\u7801\u53EF\u89C1.png")).getImage().getScaledInstance(25,25,Image.SCALE_SMOOTH);
		JLabel iconpw = new JLabel("");
		passwd.setEchoChar('*');//密码初始不回显
		
		iconpw.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(pw_style) {
					iconpw.setIcon(new ImageIcon(pw_hidden));
					passwd.setEchoChar('*');
					pw_style = false;
				}else {
					iconpw.setIcon(new ImageIcon(pw_));
					passwd.setEchoChar((char)0);
					pw_style = true;
				}
			}
		});
		iconpw.setIcon(new ImageIcon(pw_hidden));
		iconpw.setBounds(304, 97, 25, 26);
		bg.add(iconpw);
		
		JButton btn_login = new JButton("\u767B \u5F55");
		btn_login.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				checkLoad();
			}
		});
		btn_login.setFocusPainted(false);
		btn_login.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
		MatteBorder btn_border = new MatteBorder(0, 0, 0, 0, Color.BLACK);
		btn_login.setBorder(btn_border);
		btn_login.setBackground(Color.WHITE);
		
		btn_login.setBounds(136, 150, 106, 37);
		bg.add(btn_login);
		
		register = new JLabel("\u6CE8\u518C\u8D26\u53F7");
		register.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				getContentPane().remove(bg);
				getContentPane().add(rgpg);//移除添加jpanel必须调用以下两个方法
				flushPane();
			}
		});
		register.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				register.setForeground(Color.WHITE);
			}
		});
		register.setForeground(Color.LIGHT_GRAY);
		register.setFont(new Font("宋体", Font.ITALIC, 12));
		register.setBounds(29, 159, 55, 24);
		bg.add(register);
		
		msg1 = new JLabel("*\u65E0\u6B64\u8D26\u53F7");
		msg1.setFont(new Font("微軟正黑體 Light", Font.BOLD, 14));
		msg1.setBackground(Color.BLACK);
		msg1.setHorizontalAlignment(SwingConstants.LEFT);
		msg1.setForeground(new Color(255, 51, 0));
		msg1.setBounds(257, 125, 72, 23);
		bg.add(msg1);
		
		msg2 = new JLabel("*\u5BC6\u7801\u9519\u8BEF");
		msg2.setForeground(new Color(204, 51, 51));
		msg2.setFont(new Font("微軟正黑體 Light", Font.BOLD, 14));
		msg2.setBounds(257, 125, 72, 23);
		//bg.add(msg2);
		//getContentPane().add(rgpg);
	}
	public LoginPage() {
		this.loadSql();
		this.initPage();
		this.setVisible(true);
		this.setResizable(false);
	}
}
