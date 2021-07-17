package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JLayeredPane;
import javax.swing.JToolBar;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JRadioButtonMenuItem;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Font;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JMenu;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextPane;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class MainPage extends JFrame {

	private JPanel contentPane;
	private enum Select{
		first,
		second,
		third;
	};
	private Select cur = Select.first;
	private Select curr = Select.first;
	private JPanel menu1;
	private JPanel menu2;
	private JPanel menu3;
	private JPanel face3;
	private JPanel face2;
	private JPanel face1;
	private JPanel panel;
	private JPanel image1;
	private JTextField textField;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JTextPane textPane;
	private JScrollPane scrollPane;
	private JLabel count;
	private Sql sql;
	private Connection con;
	private String uid;
	private JComboBox comboBox;
	private GoodsPrompt promt = null;
	private JLabel lbl1;
	private JLabel lbl2;
	private JLabel lbl3;
	JFileChooser jf;
	private JLabel addimage;
	JComboBox comboBox_1;
	private String filepath = null;
	private JTextField find;
	private JComboBox comboBox_2;
	private JPanel bar;
	private Panel showMsg;
	private JTable table;
	private String ggid;//
	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 */
	public MainPage(Sql sql,String uid) {
		this.sql = sql;
		con = sql.getCon();
		this.uid = uid;
		this.initPage();
		this.setVisible(true);
	}
	private void changeMenu() {
		switch(this.cur) {
		case first:
			menu1.setBackground(Color.GRAY);
			menu2.setBackground(Color.lightGray);
			menu3.setBackground(Color.lightGray);
			break;
		case second:
			menu2.setBackground(Color.GRAY);
			menu1.setBackground(Color.lightGray);
			menu3.setBackground(Color.lightGray);
			break;
		case third:
			menu3.setBackground(Color.GRAY);
			menu1.setBackground(Color.lightGray);
			menu2.setBackground(Color.lightGray);
			break;
		}
	}
	private void flushLabel() {
		panel.validate();
		panel.repaint();
	}
	public void updataShowMsg() {
		
		showMsg = new Panel();
		face1.add(showMsg);
		showMsg.setBackground(Color.WHITE);
		showMsg.setBounds(0, 35, 630, 391);
		
		Icon Icon = null;

        String[] columnNames = {"图片","uid", "名称","种类","描述"};
        String[] columnNames1 = {"图片","gid", "名称","种类","描述"};
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        try {
        	Statement stmt = con.createStatement();
        	String s1 = "select image,type,name,text,uid,gid from goods";
        	if(curr == Select.third) {
        		s1 += " WHERE uid = \'" + uid +"\' ";
        	}
        	boolean flag = false;
        	if(comboBox_2.getSelectedItem().toString().length() > 0) {
        		if(curr == Select.third) {
        			s1=s1 + " and type = \'" + comboBox_2.getSelectedItem().toString() + "\'";
            	}
        		else {
        			s1=s1 + " where type = \'" + comboBox_2.getSelectedItem().toString() + "\'";
        		}
        		flag = true;
        	} 
        	if(find.getText().length() > 0) {
        		if(flag) 
        			s1 = s1 + " and name like";
        		else  {
        			if(curr == Select.third) {
        				s1 += " and name like ";
                	}
            		else {
            			s1 += " where name like ";
            		}
        		}
        			
        		s1 = s1 + " \'%"+find.getText() + "%\'";
        	}
        	System.out.println(s1);
        	ResultSet rs = stmt.executeQuery(s1);
        	while(rs.next()) {
        		ImageIcon icon = null;
        		Image img = null;
        		Vector<Object> ele = new Vector<Object>();
        		String path = rs.getString("image");
        		if(path.equals("null")) {
        			ele.add(null);
        		} else {
        			img = ImageIO.read(new File(path)).getScaledInstance(100,100,Image.SCALE_SMOOTH);
                	icon = new ImageIcon(img);
        		}
            	String name = rs.getString("name");
            	String text = rs.getString("text");
            	String type = rs.getString("type");
            	int gid = rs.getInt("gid");
            	String uid = rs.getString("uid");
            	if(!path.equals("null"))
            		ele.add(icon);
            	if(curr == Select.third)
            		ele.add(gid);
            	else {
            		ele.add(uid);
            	}
        		ele.add(name);
        		ele.add(type);
        		ele.add(text);
        		data.add(ele);
        	}
        } catch (Exception e) {
        	e.printStackTrace();
        }
        DefaultTableModel model = new DefaultTableModel(){
        	
        	public boolean isCellEditable(int row, int column) {
        		return false;
        		/*if(curr == Select.first) {
        			return false;
        		}//face1不可编辑
        		     switch (column) {
        		         case 0:
        		         case 1:
        		             return false;
        		         default:
        		             return true;
        		      }//第一列不可编辑*/
        	}
        	public Class getColumnClass(int column) {
        		switch (column) {
        		case 0: 
        			return ImageIcon.class;
        		default: 
        			return String.class;
        		}
        	}
        };
        
        if(curr == Select.third) {
        	for(int i = 0; i < columnNames1.length; i++) {
            	model.addColumn(columnNames1[i]);
            }
        }
        else {
        	for(int i = 0; i < columnNames.length; i++) {
            	model.addColumn(columnNames[i]);
            }
        }
        for(int i = 0; i < data.size(); i++) {
        		model.addRow(data.get(i));
        }
        class WordWrapCellRenderer extends JTextArea implements TableCellRenderer {
            WordWrapCellRenderer() {
                setLineWrap(true);
                setWrapStyleWord(true);
            }

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                setText(value.toString());
                setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
                if (table.getRowHeight(row) <= getPreferredSize().height) {
                    table.setRowHeight(row, getPreferredSize().height);
                }
                return this;
            }//https://stackoverflow.com/questions/37768335
        }//单元格文字换行
        table = new JTable(model);
       // table.setMinimumSize(getMinimumSize());
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        if(curr == Select.third) {
        	table.addMouseListener(new MouseAdapter() {
        	    @Override
        	    public void mouseClicked(final MouseEvent e) {
        	            final JTable target = (JTable)e.getSource();
        	            final int row = target.getSelectedRow();
        	            final int column = target.getSelectedColumn();
        	            String[] op = {"删除","修改"};
        	            String gid = model.getValueAt(row, 1).toString();
        	            int ret = JOptionPane.showOptionDialog(null,"你对"+gid+"号的操作是：","选择",JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE,null,op,op[0]);
        	            switch(ret) {
        	            case 0:
        	            	deleteMsg(gid);
        	            	break;
        	            case 1:
        	            	updateMsg(gid);
        	            	break;
        	            }
        	    }
        	});
        }
        if(curr == Select.third) {
        	 table.getColumn(columnNames1[1]).setPreferredWidth(30);
        }
        else {
        	table.getColumn(columnNames[1]).setPreferredWidth(30);
       	 	table.getColumn(columnNames[1]).setCellRenderer(new WordWrapCellRenderer());
        }
        table.getColumn(columnNames[3]).setPreferredWidth(27);
        table.getColumn(columnNames[4]).setPreferredWidth(300);
        table.getColumn(columnNames[2]).setPreferredWidth(120);
        table.getColumn(columnNames[0]).setPreferredWidth(140);
        table.setPreferredScrollableViewportSize(new Dimension(450,103));
        table.setFillsViewportHeight(true);
        table.setRowHeight(100);
        table.getColumn(columnNames[4]).setCellRenderer(new WordWrapCellRenderer());
        table.getColumn(columnNames[3]).setCellRenderer(new WordWrapCellRenderer());
        table.getColumn(columnNames[2]).setCellRenderer(new WordWrapCellRenderer());
        JScrollPane js=new JScrollPane(table);
        js.setVisible(true);
        showMsg.setLayout(new BorderLayout(0, 0));
        showMsg.add(js);
	}
	public void deleteMsg(String gid) {
		 try {
	        	Statement stmt = con.createStatement();
	        	stmt.executeUpdate("delete  from goods where gid = \'" + gid + "\'");
	        	} catch(Exception e) {
	        		e.printStackTrace();
	        	}
		 face3.remove(showMsg);
		 updataShowMsg();
		 face3.add(showMsg);
		 face3.validate();
		 face3.repaint();
		
	}
	public void updateMsg(String gid) {
		panel.remove(face3);
		ggid = gid;
		initFace2();
		panel.add(face2);
		panel.validate();
		panel.repaint();
	}
	public void initFace1() {
		face1 = new JPanel();
		face1.setBackground(Color.WHITE);                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
		face1.setBounds(162, 48, 646, 436);
		//panel.add(face1);
		face1.setLayout(null);
		
		bar = new JPanel();
		bar.setBounds(0, 0, 800, 35);
		face1.add(bar);
		bar.setLayout(null);
		
		find = new JTextField();
		find.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		find.setBounds(155, 6, 215, 21);
		bar.add(find);
		find.setColumns(10);
		
		comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"", "\u4E66", "\u96F6\u98DF", "\u6587\u5177", "\u751F\u6D3B\u7528\u54C1"}));
		comboBox_2.setBounds(483, 4, 72, 23);
		comboBox_2.setLightWeightPopupEnabled(false);//https://stackoverflow.com/questions/43276431
		bar.add(comboBox_2);//我直接被干废了，下拉框被遮挡的问题,fuck the code
		comboBox_2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				face1.remove(showMsg);
				updataShowMsg();
				face1.add(showMsg);
				face1.validate();
				face1.repaint();
			}
		});
		
		JButton search = new JButton("搜索");
		search.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				face1.remove(showMsg);
				bar.remove(comboBox_2);
				bar.add(comboBox_2);
				bar.validate();
				bar.repaint();
				updataShowMsg();
				face1.add(showMsg);
				face1.validate();
				face1.repaint();
			}
		});
		search.setBackground(Color.LIGHT_GRAY);
		search.setFocusable(false);
		search.setFont(new Font("微軟正黑體 Light", Font.PLAIN, 14));
		search.setBounds(380, 6, 72, 23);
		bar.add(search);
		updataShowMsg();
	}
	public void initFace2() {
		face2 = new JPanel();
		face2.setBackground(new Color(	245, 245, 245));
		face2.setBounds(162, 48, 646, 436);
		//panel.remove(face1);
		//panel.add(face2);
		face2.setLayout(null);
		
		image1 = new JPanel();
		image1.setBounds(178, 20, 189, 116);
		face2.add(image1);
		image1.setLayout(new BorderLayout(0, 0));
		
		addimage = new JLabel("\u70B9\u51FB\u6DFB\u52A0\u56FE\u7247");
		addimage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				fileChose();
			}
		});
		addimage.setHorizontalAlignment(SwingConstants.CENTER);
		image1.add(addimage);
		
		textField = new JTextField();
		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(textField.getText().length() >= 20) {
					textField.setText(textField.getText().substring(0,20));
				}
			}
		});
		textField.addInputMethodListener(new InputMethodListener() {
			public void caretPositionChanged(InputMethodEvent event) {
			}
			public void inputMethodTextChanged(InputMethodEvent event) {
				if(textField.getText().length() >= 30) {
					textField.setText(textField.getText().substring(0,20));
				}
			}
		});
		textField.setBounds(207, 150, 125, 21);
		face2.add(textField);
		textField.setColumns(10);
		
		lblNewLabel = new JLabel("\u7F16\u8F91\u540D\u79F0\uFF1A");
		lblNewLabel.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		lblNewLabel.setBounds(112, 150, 80, 21);
		face2.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("\u9009\u62E9\u7C7B\u522B\uFF1A");
		lblNewLabel_1.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(112, 180, 80, 21);
		face2.add(lblNewLabel_1);
		
		comboBox = new JComboBox();
		comboBox.setForeground(Color.BLACK);
		comboBox.setBackground(Color.WHITE);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"\u4E66", "\u6587\u5177", "\u96F6\u98DF", "\u751F\u6D3B\u7528\u54C1"}));
		comboBox.setSelectedIndex(0);
		comboBox.setFont(new Font("微軟正黑體 Light", Font.BOLD, 16));
		comboBox.setBounds(208, 180, 75, 21);
		face2.add(comboBox);
		
		textPane = new JTextPane();
		textPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				textPane.setEditable(true);
				if(textPane.getText().length() >= 200) 
					textPane.setText(textPane.getText().substring(0,199));
				count.setText(200-textPane.getText().length()+"/200");
			}//鼠标点击选择输入位置
		});
		textPane.addInputMethodListener(new InputMethodListener() {
			public void caretPositionChanged(InputMethodEvent event) {
			}
			public void inputMethodTextChanged(InputMethodEvent event) {
				if(textPane.getText().length() >= 210) {
					textPane.setText(textPane.getText().substring(0,199));
					textPane.setEditable(false);
				} 
				count.setText(""+(200-textPane.getText().length())+"/200");
			}
		});
		textPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(textPane.getText().length() >= 200) {
					textPane.setText(textPane.getText().substring(0,199));
					textPane.setEditable(false);
				} //粘贴动作需要按键检测，input检测不出来
				count.setText(""+(200-textPane.getText().length())+"/200");
				if(e.getID() == e.KEY_PRESSED && e.getKeyCode() == 8 ) {
					textPane.setEditable(true);
				}
			}
		});
		textPane.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(textPane.getText().equals("介绍一下"))
					textPane.setText(null);
				count.setText(""+(200-textPane.getText().length())+"/200");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(textPane.getText().length() == 0)
					textPane.setText("介绍一下");
			}
		});
		textPane.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
		textPane.setBackground(Color.WHITE);
		textPane.setText("\u4ECB\u7ECD\u4E00\u4E0B");
		textPane.setBounds(139, 210, 233, 98);
		textPane.setBorder( new MatteBorder(1, 1, 1, 1, Color.BLACK));
		//textPane.requestFocus();
		//face2.add(textPane);
		
		scrollPane = new JScrollPane(textPane);
		scrollPane.setBorder( new MatteBorder(0, 0, 0, 0,Color.white));
		scrollPane.setBounds(139, 210, 233, 98);
		face2.add(scrollPane);
		
		count = new JLabel("200/200");
		count.setFont(new Font("宋体", Font.PLAIN, 14));
		count.setBounds(380, 290, 58, 15);
		face2.add(count);//scrollPane.add(count);
		
		JButton submit = new JButton("\u53D1\u5E03");
		submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int gid = 1;
				if(promt != null && promt.isClose() == false) {
					Toolkit.getDefaultToolkit().beep();
					promt.dispose();
					promt = new GoodsPrompt();
					return ;
				}
				try {
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery("select max(gid) from goods;");
					while(rs.next()) {
						gid = rs.getInt(1);
					}
				} catch(Exception e1) {
					e1.printStackTrace();
				}
				Goods g = new Goods(sql,gid+1,uid,textField.getText(),comboBox.getSelectedItem().toString(),textPane.getText(),filepath);
				if(curr == Select.second)
					g.add();
				if(curr == Select.third) 
					g.update(ggid);
				promt = new GoodsPrompt();
			}
		});
		submit.setFocusPainted(false);
		submit.setBackground(SystemColor.controlHighlight);
		submit.setFont(new Font("微軟正黑體 Light", Font.BOLD, 20));
		submit.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
		submit.setBounds(218, 338, 97, 40);
		face2.add(submit);
		if(curr == Select.third) {
			try {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("select * from goods where gid = \'" + ggid +"\';");
				while(rs.next()) {
					textField.setText(rs.getString("name"));
					textPane.setText(rs.getString("text"));
					int idx = 0;
					String tmp = rs.getString("type");
					if(tmp.equals("书")) {
						idx = 0;
					}else if(tmp.equals("文具")) {
						idx = 1;
					}else if(tmp.equals("零食")) {
						idx = 2;
					}else if(tmp.equals("生活用品")) {
						idx = 3;
					}
					comboBox.setSelectedIndex(idx);
					String file = rs.getString("image");
					if(!file.equals("null")) {
						Image im = ImageIO.read(new File(file )).getScaledInstance(image1.getWidth(),image1.getHeight(),Image.SCALE_SMOOTH);
						addimage.setIcon(new ImageIcon(im));
					}
					
				}
			} catch(Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	public void initFace3() {
		face3 = new JPanel();
		face3.setBackground(new Color(245, 245, 245));
		face3.setBounds(162, 48, 646, 436);                                                                                                                                                                                                                                                                                                                                                                                                                                                                
		face3.setLayout(null);
		
		bar = new JPanel();
		bar.setBounds(0, 0, 800, 35);
		face3.add(bar);
		bar.setLayout(null);
		
		find = new JTextField();
		find.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		find.setBounds(155, 6, 215, 21);
		bar.add(find);
		find.setColumns(10);
		
		comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"", "\u4E66", "\u96F6\u98DF", "\u6587\u5177", "\u751F\u6D3B\u7528\u54C1"}));
		comboBox_2.setBounds(483, 4, 72, 23);
		comboBox_2.setLightWeightPopupEnabled(false);//https://stackoverflow.com/questions/43276431
		bar.add(comboBox_2);//我直接被干废了，下拉框被遮挡的问题,fuck the code
		comboBox_2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				face3.remove(showMsg);
				updataShowMsg();
				face3.add(showMsg);
				face3.validate();
				face3.repaint();
			}
		});
		
		JButton search = new JButton("搜索");
		search.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				face3.remove(showMsg);
				bar.remove(comboBox_2);
				bar.add(comboBox_2);
				bar.validate();
				bar.repaint();
				updataShowMsg();
				face3.add(showMsg);
				face3.validate();
				face3.repaint();
			}
		});
		search.setBackground(Color.LIGHT_GRAY);
		search.setFocusable(false);
		search.setFont(new Font("微軟正黑體 Light", Font.PLAIN, 14));
		search.setBounds(380, 6, 72, 23);
		bar.add(search);
		updataShowMsg();
	}
	public void initMenu() {
		menu1 = new JPanel();
		menu1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lbl1.setFont(new Font("微軟正黑體", Font.BOLD, 17));
				lbl2.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
				lbl3.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
				panel.remove(face1);
				curr = Select.first;
				initFace1();
				panel.remove(face2);
				panel.remove(face3);
				panel.add(face1);
				panel.validate();
				panel.repaint();
			}
		});
		menu1.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				cur = Select.first;
				changeMenu();
			}
		});
		menu1.setBackground(Color.GRAY);
		menu1.setBounds(0, 54, 158, 50);
		panel.add(menu1);
		menu1.setLayout(new BorderLayout(0, 0));
		
		lbl1 = new JLabel("\u6211\u8981\u8D2D\u7269");
		lbl1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl1.setFont(new Font("微軟正黑體", Font.BOLD, 17));
		menu1.add(lbl1);
		
		menu2 = new JPanel();
		menu2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lbl2.setFont(new Font("微軟正黑體", Font.BOLD, 17));
				lbl1.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
				lbl3.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
				panel.remove(face1);
				panel.remove(face3);
				panel.add(face2);
				panel.validate();
				panel.repaint();
				curr = Select.second;
			}
		});
		menu2.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				cur = Select.second;
				changeMenu();
			}
		});
		menu2.setBackground(Color.LIGHT_GRAY);
		menu2.setBounds(0, 110, 158, 50);
		panel.add(menu2);
		menu2.setLayout(new BorderLayout(0, 0));
		
		lbl2 = new JLabel("\u95F2\u7F6E\u6362\u94B1");
		lbl2.setHorizontalAlignment(SwingConstants.CENTER);
		lbl2.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		menu2.add(lbl2);
		
		menu3 = new JPanel();
		menu3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lbl3.setFont(new Font("微軟正黑體", Font.BOLD, 17));
				lbl2.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
				lbl1.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
				panel.remove(face3);
				panel.remove(face1);
				panel.remove(face2);
				initFace3();
				panel.add(face3);
				panel.validate();
				panel.repaint();
				curr = Select.third;
				updataShowMsg();
				face3.add(showMsg);
			}
		});
		menu3.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				cur = Select.third;
				changeMenu();
			}
		});
		menu3.setBackground(Color.LIGHT_GRAY);
		menu3.setBounds(0, 165, 158, 50);
		panel.add(menu3);
		menu3.setLayout(new BorderLayout(0, 0));
		
		lbl3 = new JLabel("\u6211\u7684\u53D1\u5E03");
		lbl3.setHorizontalAlignment(SwingConstants.CENTER);
		lbl3.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		menu3.add(lbl3);
	}
	public void initPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(134, 158, 162));
		panel_2.setBounds(0, 0, 786, 48);
		panel.add(panel_2);
		
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(new Color(	134, 158, 162));
		panel_6.setBounds(160, 48, 2, 415);
		panel.add(panel_6);
		
		
		
		JLabel avatar = new JLabel("");
		avatar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int ret = JOptionPane.showConfirmDialog(null,"确认登出吗？","leefanwanna",JOptionPane.WARNING_MESSAGE);
				if(ret == 0) {
					dispose();
					new LoginPage();
				}
			}
		});
		avatar.setHorizontalAlignment(SwingConstants.CENTER);
		avatar.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
		avatar.setBounds(20, 257, 120, 120);
		try {
			avatar.setIcon(new ImageIcon(new CircleImage("src/images/avatar/meigumi.jpg",avatar).getMasked()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		panel.add(avatar);
		
		initFace1();
		initFace2();
		initFace3();
		initMenu();
		panel.add(face1);
		
		
	}

	public void fileChose() {
		jf = new JFileChooser("./src/images");             //设置选择器
		jf.showOpenDialog(null);        //是否打开文件选择框
		jf.setFileSelectionMode(jf.FILES_ONLY);
		jf.addChoosableFileFilter(new FileFilter() {
	        public boolean accept(File file) {
	            //筛选器
	            if (file.getName().endsWith(".jpg")||file.getName().endsWith(".png")) {
	                return true;
	            }
	            return false;
	        }
			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return null;
			}
	    });
		Image pic = null;
		try {
			 if( jf.getSelectedFile() != null) {
				 filepath = jf.getSelectedFile().getAbsolutePath();
				 pic = ImageIO.read(new File(filepath)).getScaledInstance(addimage.getWidth(),addimage.getHeight(),Image.SCALE_SMOOTH);
				 addimage.setIcon(new ImageIcon(pic));
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
	}
}
