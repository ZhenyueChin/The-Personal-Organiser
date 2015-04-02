package comp2100.ass1;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The Calendar can be used to show which day there is an event
 * @author Zhenyue Qin, u5505995
 *
 */

@SuppressWarnings("serial")
public class TheCalendar extends JFrame implements Serializable{
	/* The ArrayList events is used to store all the events and output them */
	private ArrayList<Event> events = new ArrayList<Event>();
	
	/* The heading and the instruction on the top */
	JPanel instructions = new JPanel(new GridLayout(0, 1));
	
	/* Every month is a month panel */
	private JPanel[] monthPanels = new JPanel[12];
	
	/* The days for every month */
	private JButton[] days = new JButton[32];
	
	/* The calendar is used to calculate what day is the first day of the month */
	Calendar calendar = new GregorianCalendar();
	
	/* The container is everything contained in the frame */
	private Container container = getContentPane();
	
	/* The JPanel allMonths is used put all the month panels together */
	private JPanel allMonths = new JPanel(new GridLayout(0, 4)); 

	public TheCalendar() {
		super("The Calendar");
		
		container.setLayout(new BorderLayout());
		container.setBackground(Color.WHITE);
		
		/* These are the words in the top */
		JLabel topWords = new JLabel();
		topWords.setText("The days associalated with the events are marked as red and bigger. After adding a new event, please refresh!");
		topWords.setFont(APPLibrary.arialFifteen);
		topWords.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel heading = new JLabel();
		heading.setText("New Contact");
		heading.setFont(APPLibrary.arielTwentyFive);
		heading.setHorizontalAlignment(JLabel.CENTER);
		topWords.setForeground(Color.GRAY);
		
		JPanel buttons = new JPanel(new GridLayout(0, 4));
		
		instructions.add(heading);
		instructions.add(topWords);
		
		container.add(instructions, BorderLayout.PAGE_START);
		
		/* These are the buttons */
		JButton addEvents = new JButton("Add New Event");
		addEvents.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				java.net.URL addPeopleURL = this.getClass().getResource(
					    "/comp2100/ass1/Add_Person.wav");
				APPLibrary.playSound(addPeopleURL);
				
				AddTodo newTodo = new AddTodo();
			}
			
		});
		
		JButton allEvents = new JButton("Show all TODO");
		allEvents.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				java.net.URL addPeopleURL = this.getClass().getResource(
					    "/comp2100/ass1/Add_Person.wav");
				APPLibrary.playSound(addPeopleURL);
				
				try {
					TodoBook todobook = new TodoBook();
				} catch (URISyntaxException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
		JButton close = new JButton("Cancel");
		close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				dispose();
			}
			
		});
		
		JButton refresh = new JButton("Refresh");
		refresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				java.net.URL incomingURL = this.getClass().getResource(
					    "/comp2100/ass1/IncomingTransmission.wav");
				APPLibrary.playSound(incomingURL);
				
				TheCalendar newWindow = new TheCalendar();
				
				dispose();
			}
			
		});
		
		buttons.add(addEvents);
		buttons.add(allEvents);
		buttons.add(close);
		buttons.add(refresh);
		
		instructions.add(buttons);
		
		/* Load the objects in the file */
		readTheFile();
		
		/* Create the panels for every month */
		createPanel("January");
		createPanel("Feburary");
		createPanel("March");
		createPanel("April");
		createPanel("May");
		createPanel("June");
		createPanel("July");
		createPanel("August");
		createPanel("September");
		createPanel("October");
		createPanel("November");
		createPanel("December");
		
		/* When close the window, the application terminates */
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pack();
		setVisible(true);
	}
	
	/* Load the events in the file */
	private void readTheFile() {
		try {
			FileInputStream fileIn = new FileInputStream(APPLibrary.WHERE_TODO_FILE);
	        ObjectInputStream in = new ObjectInputStream(fileIn);
	        events = (ArrayList<Event>) in.readObject(); 
	        in.close();
	        fileIn.close();
	    } catch(Exception exc) {
	    	java.net.URL information_Not_FoundURL = this.getClass().getResource(
				    "/comp2100/ass1/Information_Not_Found.wav");
	    	APPLibrary.playSound(information_Not_FoundURL);
	    	APPLibrary.notFoundFile();
	    }
	}
	
	/* Create panels for the month */
	public void createPanel(String whichMonth) {
		/* Know which month is dealing with */
		/* START FROM 0! */
		int whichPanel = 0;
		
		/* The heading for the month panel */
		JPanel heading = new JPanel();
		
		/* The name of the month */
		JLabel theMonthNow = new JLabel(whichMonth);
		theMonthNow.setFont(APPLibrary.arialFifteen);
		theMonthNow.setHorizontalAlignment(JLabel.CENTER);
		theMonthNow.setHorizontalAlignment(JLabel.CENTER);
		heading.add(theMonthNow);
		
		/* The days */
		JPanel below = new JPanel();
		below.setBackground(Color.WHITE);
		below.setLayout(new GridLayout(0, 7));
		
		/* The days' names */
		JLabel[] whatDay = new JLabel[7];
		whatDay[0] = new JLabel(" Sun");
		whatDay[1] = new JLabel(" Mon");
		whatDay[2] = new JLabel(" Tue");
		whatDay[3] = new JLabel(" Wed");
		whatDay[4] = new JLabel(" Thu");
		whatDay[5] = new JLabel(" Fri");
		whatDay[6] = new JLabel(" Sat");
		
		for (int i=0; i<7; i++) {
			below.add(whatDay[i]);
		}
		
		/* Calculate what is the first day */
		calendar = Calendar.getInstance();
		int monYear = calendar.get(Calendar.YEAR);
		
		if (whichMonth.equals("January")) {
			calendar.set(monYear, 0, 1);
			whichPanel = 0;
		}
		
		else if (whichMonth.equals("Feburary")) {
			calendar.set(monYear, 1, 1);
			whichPanel = 1;
		}
		
		else if (whichMonth.equals("March")) {
			calendar.set(monYear, 2, 1);
			whichPanel = 2;
		}
		
		else if (whichMonth.equals("April")) {
			calendar.set(monYear, 3, 1);
			whichPanel = 3;
		}
		
		else if (whichMonth.equals("May")) {
			calendar.set(monYear, 4, 1);
			whichPanel = 4;
		}
		
		else if (whichMonth.equals("June")) {
			calendar.set(monYear, 5, 1);
			whichPanel = 5;
		}
		
		else if (whichMonth.equals("July")) {
			calendar.set(monYear, 6, 1);
			whichPanel = 6;
		}
		
		else if (whichMonth.equals("August")) {
			calendar.set(monYear, 7, 1);
			whichPanel = 7;
		}
		
		else if (whichMonth.equals("September")) {
			calendar.set(monYear, 8, 1);
			whichPanel = 8;
		}
		
		else if (whichMonth.equals("October")) {
			calendar.set(monYear, 9, 1);
			whichPanel = 9;
		}
		
		else if (whichMonth.equals("November")) {
			calendar.set(monYear, 10, 1);
			whichPanel = 10;
		}
		
		else if (whichMonth.equals("December")) {
			calendar.set(monYear, 11, 1);
			whichPanel = 11;
		}
		
		int firstDay = calendar.get(Calendar.DAY_OF_WEEK);
		
		for (int i=0; i<firstDay; i++) {
			JLabel empty = new JLabel("");
			below.add(empty);
		}
		
		/* Get the event */
		for (int i=1; i<=calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
			days[i] = new JButton(Integer.toString(i));
			below.add(days[i]);
			Calendar tempCalendar = new GregorianCalendar();
			tempCalendar.set(monYear, whichPanel, i);
			for (final Event element : events) {
				
				if (element.getCalendar().get(Calendar.DATE) == tempCalendar.get(Calendar.DATE) 
						&& element.getCalendar().get(Calendar.MONTH) == tempCalendar.get(Calendar.MONTH)
						&& element.getCalendar().get(Calendar.YEAR) == tempCalendar.get(Calendar.YEAR)) {
					
					/* Found it! Set as red */
					days[i].setFont(APPLibrary.arielTwentyFive);
					days[i].setForeground(Color.RED);
					days[i].addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							TodoShowInformation info = new TodoShowInformation(element);
							
						}
						
					});
				}
			}
		}
		
		/* Set the layout */
		monthPanels[whichPanel] = new JPanel();
		monthPanels[whichPanel].setLayout(new BorderLayout());
		monthPanels[whichPanel].add(heading, BorderLayout.PAGE_START);
		monthPanels[whichPanel].add(below, BorderLayout.CENTER);
		allMonths.add(monthPanels[whichPanel]);
		container.add(allMonths);
	}
	
	public static void main(String[] args) {
		TheCalendar launch = new TheCalendar();
		launch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}