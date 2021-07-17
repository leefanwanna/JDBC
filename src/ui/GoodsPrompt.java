package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.border.MatteBorder;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.BoxLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GoodsPrompt extends JFrame {
	private boolean isClose = false;

	public boolean isClose() {
		return isClose;
	}

	/**
	 * Create the frame.
	 */
	public GoodsPrompt() {
		setBounds(600, 400, 200, 100);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("\u6DFB\u52A0\u6210\u529F");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				isClose = true;
				dispose();
			}
		});
		
		btnNewButton.setBounds(44, 10, 90, 40);
		btnNewButton.setForeground(Color.DARK_GRAY);
		btnNewButton.setBackground(SystemColor.controlHighlight);
		btnNewButton.setFocusable(false);
		btnNewButton.setFont(new Font("Î¢Ü›ÕýºÚów Light", Font.BOLD, 16));
		btnNewButton.setBorder(new MatteBorder(1,1,1,1,Color.DARK_GRAY));
		getContentPane().add(btnNewButton);
		this.setVisible(true);
	}

}
