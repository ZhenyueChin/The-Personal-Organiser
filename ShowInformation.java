package comp2100.ass1;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class ShowInformation extends JFrame {
	public static Font fontHead = new Font("Arial",Font.BOLD ,25);
	public static Font fontMany = new Font("Arial",Font.BOLD ,12);
	
	public ShowInformation(Person toShow) {
		super("Information");
		
		Container container = getContentPane();
		container.setLayout(new FlowLayout());
		container.setBackground(Color.WHITE);
		
		container.setLayout(new GridLayout(0, 2));
		String firstName = toShow.getFirstName();
		String lastName = toShow.getLastName();
		String number = toShow.getNumber();
		String birthday = toShow.getDate() + ", " + toShow.getMonth() + ", " + toShow.getYear();
		String email = toShow.getEmail();
		String address = toShow.getAddress();
		
		JLabel infoFName = new JLabel("First Name: ");
		infoFName.setFont(fontHead);
		container.add(infoFName);
		
		JLabel labelFName = new JLabel(firstName);
		labelFName.setFont(fontHead);
		container.add(labelFName);
		
		
		JLabel infoLName = new JLabel("Last Name: ");
		infoLName.setFont(fontHead);
		container.add(infoLName);
		
		JLabel labelLName = new JLabel(lastName);
		labelLName.setFont(fontHead);
		container.add(labelLName);
		
		JLabel infoNumber = new JLabel("Phone Number: ");
		infoNumber.setFont(fontHead);
		container.add(infoNumber);
		
		JLabel labelNumber = new JLabel(number);
		labelNumber.setFont(fontHead);
		container.add(labelNumber);
		
		JLabel infoBirthday = new JLabel("Birthday: ");
		infoBirthday.setFont(fontHead);
		container.add(infoBirthday);
		
		JLabel labelBirthday = new JLabel(birthday);
		labelBirthday.setFont(fontHead);
		container.add(labelBirthday);
		
		JLabel infoEmail = new JLabel("Email: ");
		infoEmail.setFont(fontHead);
		container.add(infoEmail);
		
		JLabel labelEmail = new JLabel(email);
		labelEmail.setFont(fontMany);
		container.add(labelEmail);
		
		JLabel infoAddress = new JLabel("Address: ");
		infoAddress.setFont(fontHead);
		container.add(infoAddress);
		
		JLabel labelAddress = new JLabel(address);
		labelAddress.setFont(fontMany);
		container.add(labelAddress);
		
		pack();
		setVisible(true);
	}
	

	public static void main(String[] args) {
		Person test = new Person("Zhenyue", "Chin", "123", "15", "09", "1994", "qwe", "fgh");
		ShowInformation launch = new ShowInformation(test);
		launch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
