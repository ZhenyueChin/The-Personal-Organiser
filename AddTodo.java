package comp2100.ass1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class is used to add TODO events
 * @author Zhenyue Qin, u5505995
 *
 */

public class AddTodo extends JFrame implements Serializable{
	/* The ArrayList events is used to store all the events and output them */
	ArrayList<Event> events = new ArrayList<Event>();
	
	//http://www.trekcore.com/audio/
		java.net.URL information_Not_FoundURL = this.getClass().getResource(
					    "/comp2100/ass1/Information_Not_Found.wav");
	
	/* These objects are used for ComboBox */
	static Object selectedDate;
	static Object selectedMonth;
	
	public AddTodo() {
		super("Add Todo Event");
		Container container = getContentPane();
		container.setBackground(Color.WHITE);
		container.setLayout(new BorderLayout());
		
		openFile();
		
		/* The main panel */
		JPanel below = new JPanel();
		below.setLayout(new GridLayout(0, 2));
		
		JPanel buttons = new JPanel(new GridLayout(0, 2));
		
		JLabel time = new JLabel("Please set the time");
		below.add(time);
		
		/* Define the dates */
		String[] dates = new String[32];
		for (int i=1; i<=31; i++) {
			dates[i] = Integer.toString(i);
		}
		
		JPanel theDate = new JPanel();
		
		final JComboBox date = new JComboBox();
		for (int i=1; i<=31; i++) {
			date.addItem(dates[i]);
		}
		
		date.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectedDate = date.getSelectedItem();
			}
			
		}
		);
		theDate.add(date);
		
		/* Define the months */
		String[] months = new String[13];
		for (int i=1; i<=12; i++) {
			months[i] = Integer.toString(i);
		}
		
		final JComboBox month = new JComboBox();
		for (int i=1; i<=12; i++) {
			month.addItem(dates[i]);
		}
		
		month.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectedMonth = month.getSelectedItem();
			}
		}
		);
		theDate.add(month);
		
		below.add(theDate);
		
		JLabel infoEvent = new JLabel("Please input the event: (DD/MM)");
		below.add(infoEvent);
		
		final JTextField event = new JTextField("e.g. Go to the movie with Mary");
		below.add(event);
		
		JButton done = new JButton("Done");
		
		done.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				/* No choice */
				if (selectedDate == null) {
					selectedDate = "1";
				} 
				
				if (selectedMonth == null) {
					selectedMonth = "1";
				}
				
				/* Unfortunately, only the year of 2015 can be used */
				Event toAdd = new Event(event.getText(), (String) selectedDate, (String) selectedMonth, "2015");
				events.add(toAdd);
				writeToFile();
				
				try {
					TodoBook theTodoBook = new TodoBook();
				} catch (URISyntaxException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
			}
			
		});
		
		/* The close button */
		JButton close = new JButton("Cancel");
		close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
			
		});
		
		buttons.add(done);
		buttons.add(close);
		
		/* When close the window, the application terminates */
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		container.add(below, BorderLayout.CENTER);
		container.add(buttons, BorderLayout.PAGE_END);
		pack();
		setVisible(true);
		
	}
	
	/* Load the objects in the file to the persons */
	// The original code is from the Internet
	public void openFile() {
		try {
			FileInputStream fileIn = new FileInputStream(APPLibrary.WHERE_TODO_FILE);
	        ObjectInputStream in = new ObjectInputStream(fileIn);
	        events = (ArrayList<Event>) in.readObject();
	        in.close();
	        fileIn.close();
	    } catch (Exception ex) {
	    	APPLibrary.playSound(information_Not_FoundURL);
	    	APPLibrary.notFoundFile();
	    }
	}
	
	/* Write the objects to the file */
	public void writeToFile() {
		try{
			FileOutputStream fout = new FileOutputStream(APPLibrary.WHERE_TODO_FILE);
			ObjectOutputStream oos = new ObjectOutputStream(fout); 
			oos.writeObject(events);
			oos.close();
			
			java.net.URL completeURL = this.getClass().getResource(
				    "/comp2100/ass1/Transfer_Complete.wav");
			APPLibrary.playSound(completeURL);
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}