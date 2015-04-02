package comp2100.ass1;

import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * This is the class where people check the person can do the functions
 * This is the main user interface
 * @author Zhenyue Qin, u550595
 *
 */

@SuppressWarnings("serial")
public class TodoBook extends JFrame {
	/* The ArrayList events are all the objects stored */
	static ArrayList<Event> events = new ArrayList<Event>();
	
	/* The HashMap is used to find the corresponding object based on the name String in the JList */
	HashMap<String, Event> nameMatching = new HashMap<String, Event>();
	
	/* The Vector names is to store names in the JList */
	//Idea is from the Internet
	Vector<String> names = new Vector<String>();
	
	/* JList is used to show names */
	public JList nameList = null;
	
	/* The JPanel showing the person's information when clicked */
	JPanel personInfo = new JPanel(new GridLayout(0, 2));
	
	JPanel left = new JPanel(new GridLayout(0, 1));
	
	//http://www.trekcore.com/audio/
	java.net.URL information_Not_FoundURL = this.getClass().getResource(
				    "/comp2100/ass1/Information_Not_Found.wav");
	
	static Container container;
	
	public TodoBook() throws URISyntaxException, IOException{
		super("Contacts Book");
		
		/* The container is everything contained in the frame */
		container = getContentPane();
		container.setLayout(new GridLayout(0, 2));
		container.setBackground(Color.WHITE);
		
		/* Load the objects in the file to the persons */
		openFile();
	
		/*  */
		this.nameList.setBorder(BorderFactory.createTitledBorder("Your Contacts: "));
		
		/* These are the buttons */
		JPanel operation = new JPanel();
		operation.setLayout(new GridLayout(0, 1));
		
		/* Add the new event */
		JButton addPeople = new JButton("Add New Todo");
		addPeople.setFont(APPLibrary.arialFifteen);
		addPeople.setForeground(Color.BLUE);
		addPeople.setPreferredSize(new Dimension(40, 50));
		operation.add(addPeople);
		addPeople.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				java.net.URL addPeopleURL = this.getClass().getResource(
					    "/comp2100/ass1/Add_Person.wav");
				APPLibrary.playSound(addPeopleURL);
				AddTodo aTodo = new AddTodo();
				dispose();
			}
		});
		
		/* Delete the contacts */
		JButton deletePeople = new JButton("Delete the Event");
		deletePeople.setFont(APPLibrary.arialFifteen);
		deletePeople.setForeground(Color.RED);
		operation.add(deletePeople);
		
		deletePeople.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Event toDelete = (Event) nameMatching.get(nameList.getSelectedValue());
				
				if (toDelete != null) {
					java.net.URL deleteURL = this.getClass().getResource(
						    "/comp2100/ass1/DeleteName.wav");
					APPLibrary.playSound(deleteURL);
					
					String toDeleteName = toDelete.getEvent();
					events.remove(toDelete);
					names.remove(toDeleteName);
					nameMatching.remove(toDeleteName);
					
					nameList.removeAll();
					nameList = new JList(names);
					
					writeDataBase();
					
					try {
						TodoBook anotherBook = new TodoBook();
					} catch (URISyntaxException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					dispose();
				}
				
				else {
					java.net.URL noSelectionURL = this.getClass().getResource(
						    "/comp2100/ass1/NoSelection.wav");
					APPLibrary.playSound(noSelectionURL);
					APPLibrary.noChoice();
				}
				
			}
			
		});
		
		
		JButton refresh = new JButton("Refresh");
		refresh.setFont(APPLibrary.arialFifteen);
		refresh.setForeground(Color.ORANGE);
		operation.add(refresh);
		
		refresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					TodoBook anotherBook = new TodoBook();
				} catch (URISyntaxException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
				
			}
			
		});
		
		
		JMenuBar menuBar = new JMenuBar();
		container.add(menuBar);
		
		JMenu menuFind = new JMenu("Find");
		menuBar.add(menuFind);
		
		JMenuItem findFirstName = new JMenuItem("Find based on the First Name");
		menuFind.add(findFirstName);
		findFirstName.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FindFirstName firstFind = new FindFirstName();
				
			}
			
		});
		
		JMenuItem findLastName = new JMenuItem("Find based on the Last Name");
		menuFind.add(findLastName);
		findLastName.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FindLastName lastFind = new FindLastName();
				
			}
			
		});
		
		JMenuItem findFullName = new JMenuItem("Find based on the Full Name");
		menuFind.add(findFullName);
		
		findFullName.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FindFullName fullFind = new FindFullName();
				
			}
			
		});
		
		JMenu menuTodo = new JMenu("ToDo");
		menuBar.add(menuTodo);
		
		JMenuItem todo = new JMenuItem("ToDo");
		menuTodo.add(todo);
		
		todo.addActionListener(new ActionListener() {
			

			@Override
			public void actionPerformed(ActionEvent e) {
				AddTodo can = new AddTodo();
				
			}
		});
		
		JMenu menuCalendar = new JMenu("Calendar");
		menuBar.add(menuCalendar);
		
		JMenuItem calendar = new JMenuItem("Calendar");
		menuCalendar.add(calendar);
		
		calendar.addActionListener(new ActionListener() {
			

			@Override
			public void actionPerformed(ActionEvent e) {
				TheCalendar can = new TheCalendar();
				
			}
		});
		
		JMenu menuPreferences = new JMenu("Preferences");
		menuBar.add(menuPreferences);
		
		JMenuItem asiaName = new JMenuItem("Surname is in the front");
		menuPreferences.add(asiaName);
		asiaName.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					AsiaBook asiaBook = new AsiaBook();
				} catch (URISyntaxException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		});
		
		JMenu system = new JMenu("System");
		menuBar.add(system);
		
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
			
		});
		
		system.add(exit);
		
		
		setJMenuBar(menuBar);
		//container.add(menuFind);
		
		
		nameList.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				showPersonInformation();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		//System.out.println(names);
		
		
		left.add(nameList);
		left.add(new JScrollPane(nameList));
		left.add(operation);
		
		
		
		container.add(left);
		container.add(personInfo);
		
		/* When close the window, the application terminates */
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
		
		for (Event e : events) {
			nameMatching.put(e.getEvent(), e);
		}
		
		for (String e : nameMatching.keySet()) {
			names.add(e);
		}
		this.nameList = new JList(names);
		
	}
	
	public void writeDataBase() {
		try{
			FileOutputStream fout = new FileOutputStream(APPLibrary.WHERE_TODO_FILE);
			ObjectOutputStream oos = new ObjectOutputStream(fout); 
			oos.writeObject(events);
			oos.close();
			System.out.println("Done");
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void showPersonInformation() {
		personInfo.removeAll();
		
		String chosenName = (String) nameList.getSelectedValue();
		Event toShow = nameMatching.get(chosenName);
		
		String year = toShow.getYear();
		String month = toShow.getMonth();
		String date = toShow.getDate();
		String thing = toShow.getEvent();
		
		JLabel infoFName = new JLabel("Year: ");
		infoFName.setFont(APPLibrary.arialFifteen);
		personInfo.add(infoFName);
		
		JLabel labelFName = new JLabel(year);
		labelFName.setFont(APPLibrary.arialFifteen);
		personInfo.add(labelFName);
		
		
		JLabel infoLName = new JLabel("Month: ");
		infoLName.setFont(APPLibrary.arialFifteen);
		personInfo.add(infoLName);
		
		JLabel labelLName = new JLabel(month);
		labelLName.setFont(APPLibrary.arialFifteen);
		personInfo.add(labelLName);
		
		JLabel infoNumber = new JLabel("Date: ");
		infoNumber.setFont(APPLibrary.arialFifteen);
		personInfo.add(infoNumber);
		
		JLabel labelNumber = new JLabel(date);
		labelNumber.setFont(APPLibrary.arialFifteen);
		personInfo.add(labelNumber);
		
		JLabel infoBirthday = new JLabel("Event: ");
		infoBirthday.setFont(APPLibrary.arialFifteen);
		personInfo.add(infoBirthday);
		
		JLabel labelBirthday = new JLabel(thing);
		labelBirthday.setFont(APPLibrary.arialFifteen);
		personInfo.add(labelBirthday);
		
		container.add(personInfo);
		pack();
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public static void main(String[] args) throws URISyntaxException, IOException {
		TodoBook launch = new TodoBook();
		launch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}