package comp2100.ass1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

/**
 * This class is when the user log in to the application
 * @author Zhenyue Qin, u5505995
 *
 */

/* 
 * References:
 * Some of the password code: http://docs.oracle.com/javase/tutorial/uiswing/components/passwordfield.html
 * */

@SuppressWarnings("serial")
public class Main extends JFrame{
	
	/* Reference: http://docs.oracle.com/javase/tutorial/uiswing/components/passwordfield.html */
	private static String OK = "ok";

	public Main() throws IOException {
		super("Desktop");
		
		/* The container is everything contained in the frame */
		Container container = getContentPane();
		container.setBackground(Color.WHITE);
		container.setLayout(new GridLayout(0,1));
		 
		//www.softicons.com
		java.net.URL imageURL = this.getClass().getResource(
			    "/comp2100/ass1/Contacts.png");
		
		//http://www.trekcore.com/audio/
		final java.net.URL inputOKURL = this.getClass().getResource(
			    "/comp2100/ass1/Input_OK.wav");
		
		final java.net.URL inputFailedURL = this.getClass().getResource(
			    "/comp2100/ass1/Input_Failed.wav");
		
		/* The image at the top */
		ImageIcon imageIcon = new ImageIcon(imageURL);
		JLabel ico = new JLabel(imageIcon);
		container.add(ico);
		
		/* The JPanel below includes the instructions and the password field */
		JPanel below = new JPanel();
		below.setLayout(new BorderLayout());
		below.setBackground(Color.WHITE);
		
		/* The JPanel words include the heading and the instruction */
		JPanel words = new JPanel();
		words.setLayout(new GridLayout(0, 1));
		words.setBackground(Color.WHITE);
		
		/* The heading */
		JLabel welcome = new JLabel();
		welcome.setText("Welcome to the Personal Organiser");
		welcome.setFont(APPLibrary.arialRoundedThirty);
		welcome.setFocusable(false);
		welcome.setHorizontalAlignment(JLabel.CENTER);
		welcome.setVerticalAlignment(JLabel.CENTER);
		words.add(welcome);
		
		/* The password field */
		//http://docs.oracle.com/javase/tutorial/uiswing/components/passwordfield.html
		final JPasswordField passwordField;
		passwordField = new JPasswordField(10);
		passwordField.setActionCommand(OK);
		passwordField.setPreferredSize(new Dimension(70, 30));
		passwordField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String input = passwordField.getText();
				/* Unfortunately, the password is "0000" and cannot be changed */
				if (input.equals("0000")) {
					try {
						APPLibrary.playSound(inputOKURL);
						ContactsBook book = new ContactsBook();
					} catch (URISyntaxException | IOException e1) {
						e1.printStackTrace();
					}
				}
				
				/* The password is not correct */
				else {
					APPLibrary.playSound(inputFailedURL);
				}
				
			}
			
		});
		
		/* The instruction */
		JLabel instruction = new JLabel();
		instruction.setText("Please input your password (Original password: 0000)");
		instruction.setForeground(Color.GRAY);
		instruction.setHorizontalAlignment(JLabel.CENTER);
		instruction.setVerticalAlignment(JLabel.CENTER);
		words.add(instruction);
		
		below.add(passwordField, BorderLayout.PAGE_END);
		below.add(words, BorderLayout.CENTER);
		container.add(below);
		
		/* When close the window, the application terminates */
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pack();
		setVisible(true);
	}
	
	public static void main(String[] args) throws IOException {
		Main launch = new Main();
		launch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}