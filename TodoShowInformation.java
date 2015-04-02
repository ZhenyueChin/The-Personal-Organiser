package comp2100.ass1;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class TodoShowInformation extends JFrame {
	public static Font fontHead = new Font("Arial",Font.BOLD ,25);
	public static Font fontMany = new Font("Arial",Font.BOLD ,12);
	
	public TodoShowInformation(Event toShow) {
		super("Todo Information");
		
		Container container = getContentPane();
		container.setLayout(new FlowLayout());
		container.setBackground(Color.WHITE);
		
		container.setLayout(new GridLayout(0, 2));
		String year = toShow.getYear();
		String month = toShow.getMonth();
		String date = toShow.getDate();
		String thing = toShow.getEvent();
		
		JLabel infoFName = new JLabel("Year: ");
		infoFName.setFont(fontHead);
		container.add(infoFName);
		
		JLabel labelFName = new JLabel(year);
		labelFName.setFont(fontHead);
		container.add(labelFName);
		
		
		JLabel infoLName = new JLabel("Month: ");
		infoLName.setFont(fontHead);
		container.add(infoLName);
		
		JLabel labelLName = new JLabel(month);
		labelLName.setFont(fontHead);
		container.add(labelLName);
		
		JLabel infoNumber = new JLabel("Date: ");
		infoNumber.setFont(fontHead);
		container.add(infoNumber);
		
		JLabel labelNumber = new JLabel(date);
		labelNumber.setFont(fontHead);
		container.add(labelNumber);
		
		JLabel infoBirthday = new JLabel("Event: ");
		infoBirthday.setFont(fontHead);
		container.add(infoBirthday);
		
		JLabel labelBirthday = new JLabel(thing);
		labelBirthday.setFont(fontHead);
		container.add(labelBirthday);
		
		
		
		pack();
		setVisible(true);
	}
	

	public static void main(String[] args) {
		Event test = new Event("Zhenyue", "2015", "2", "2");
		TodoShowInformation launch = new TodoShowInformation(test);
		launch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
