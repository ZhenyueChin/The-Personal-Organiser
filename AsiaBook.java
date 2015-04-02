package comp2100.ass1;

import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * This is the class where people check the person can do the functions
 * This is the main user interface
 * @author Zhenyue Qin, u550595
 *
 */

@SuppressWarnings("serial")
public class AsiaBook extends JFrame {
	/* The ArrayList persons are all the objects stored */
	static ArrayList<Person> persons = new ArrayList<Person>();
	
	/* The HashMap is used to find the corresponding object based on the name String in the JList */
	HashMap<String, Person> nameMatching = new HashMap<String, Person>();
	
	/* The Vector names is to store names in the JList */
	//Idea is from the Internet
	Vector<String> names = new Vector<String>();
	
	/* JList is used to show names */
	public JList nameList = new JList();
	
	/* The JPanel showing the person's information when clicked */
	JPanel personInfo = new JPanel(new GridLayout(0, 2));
	
	JPanel left = new JPanel(new GridLayout(0, 1));
	
	//http://www.trekcore.com/audio/
	java.net.URL information_Not_FoundURL = this.getClass().getResource(
				    "/comp2100/ass1/Information_Not_Found.wav");
	
	static Container container;
	
	public AsiaBook() throws URISyntaxException, IOException{
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
		
		/* Add the new contact */
		JButton addPeople = new JButton("Add New Contact");
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
				AddContacts aPerson = new AddContacts();
				dispose();
			}
		});
		
		/* Delete the contacts */
		JButton deletePeople = new JButton("Delete the Contact");
		deletePeople.setFont(APPLibrary.arialFifteen);
		deletePeople.setForeground(Color.RED);
		operation.add(deletePeople);
		
		deletePeople.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Person toDelete = (Person) nameMatching.get(nameList.getSelectedValue());
				
				if (toDelete != null) {
					java.net.URL deleteURL = this.getClass().getResource(
						    "/comp2100/ass1/DeleteName.wav");
					APPLibrary.playSound(deleteURL);
					
					deleteMethod(toDelete);
					
					nameList.removeAll();
					nameList = new JList(names);
					
					writeDataBase();
					
					try {
						AsiaBook anotherBook = new AsiaBook();
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
		
		/* This is to modify the contact */
		JButton modify = new JButton("Modify the Contacts");
		modify.setFont(APPLibrary.arialFifteen);
		modify.setForeground(Color.GREEN);
		operation.add(modify);
		
		modify.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) { //nameList.getSelectedValue()
				Person toModify = (Person) nameMatching.get(nameList.getSelectedValue());
				
				if (toModify == null) {
					java.net.URL noSelectionURL = this.getClass().getResource(
						    "/comp2100/ass1/NoSelection.wav");
					APPLibrary.playSound(noSelectionURL);
					APPLibrary.noChoice();
				}
				
				else {
					
					java.net.URL addPeopleURL = this.getClass().getResource(
						    "/comp2100/ass1/Add_Person.wav");
					APPLibrary.playSound(addPeopleURL);
					
					String toModifyName = toModify.getNames();
					persons.remove(toModify);
					names.remove(toModifyName);
					nameMatching.remove(toModifyName);
					
					nameList.removeAll();
					nameList = new JList(names);
					
					writeDataBase();
					
					ModifyContacts newModify = new ModifyContacts(toModify);
					dispose();
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
					java.net.URL incomingURL = this.getClass().getResource(
						    "/comp2100/ass1/IncomingTransmission.wav");
					APPLibrary.playSound(incomingURL);
					AsiaBook anotherBook = new AsiaBook();
				} catch (URISyntaxException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
				
			}
			
		});
		
		/* This is to send the email to the contact */
		JButton sendEmail = new JButton("Send Email");
		sendEmail.setFont(APPLibrary.arialFifteen);
		sendEmail.setForeground(Color.BLACK);
		operation.add(sendEmail);
		
		sendEmail.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Person toSend = nameMatching.get(nameList.getSelectedValue());
				
				if (toSend == null) {
					java.net.URL noSelectionURL = this.getClass().getResource(
						    "/comp2100/ass1/NoSelection.wav");
					APPLibrary.playSound(noSelectionURL);
					APPLibrary.noChoice();
				}
				
				else {
					String email = toSend.getEmail();
					if (email == null) {
						java.net.URL sendFailedURL = this.getClass().getResource(
							    "/comp2100/ass1/SendFailed.wav");
						APPLibrary.playSound(sendFailedURL);
						APPLibrary.emailError();
					}
					
					else {
						try {
							Desktop.getDesktop().mail(URI.create("mailto:" + email));
							java.net.URL sendEmail = this.getClass().getResource(
								    "/comp2100/ass1/Sending_Email.wav");
							APPLibrary.playSound(sendEmail);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							java.net.URL sendFailedURL = this.getClass().getResource(
								    "/comp2100/ass1/SendFailed.wav");
							APPLibrary.playSound(sendFailedURL);
							APPLibrary.emailError();
						}
					}
				}
				
			}
			
		});
		
		JButton print = new JButton("Print Address Book");
		print.setFont(APPLibrary.arialFifteen);
		print.setForeground(Color.PINK);
		operation.add(print);
		
		print.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] args = null;
				PrintingSystem.main(args);
			}
			
		});
		
		JMenuBar menuBar = new JMenuBar();
		container.add(menuBar);
		
		JMenu menuOpen = new JMenu("Open");
		menuBar.add(menuOpen);
		
		JMenuItem openJson = new JMenuItem("Open Json");
		menuOpen.add(openJson);
		openJson.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//File Chooser: https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html
				
				try {
					ContactsBookJson json = new ContactsBookJson();
				} catch (URISyntaxException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		});
		
		
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
		
		JMenuItem weternName = new JMenuItem("First name is in the front");
		menuPreferences.add(weternName);
		
		weternName.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ContactsBook westernBook = new ContactsBook();
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
				System.exit(0);
				
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
			FileInputStream fileIn = new FileInputStream(APPLibrary.WHERE_CONTACTS_FILE);
	        ObjectInputStream in = new ObjectInputStream(fileIn);
	        persons = (ArrayList<Person>) in.readObject();
	        in.close();
	        fileIn.close();
	    } catch (Exception ex) {
	    	APPLibrary.playSound(information_Not_FoundURL);
	    	APPLibrary.notFoundFile();
	    }
		
		System.out.println(persons);
		
		for (Person e : persons) {
			nameMatching.put(e.getAsiaNames(), e);
		}
		
		for (String e : nameMatching.keySet()) {
			names.add(e);
		}
		this.nameList = new JList(names);
	}
	
	public void writeDataBase() {
		try{
			FileOutputStream fout = new FileOutputStream(APPLibrary.WHERE_CONTACTS_FILE);
			ObjectOutputStream oos = new ObjectOutputStream(fout); 
			oos.writeObject(persons);
			oos.close();
			System.out.println("Done");
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void openJson(File json) {
		//Refer to Eric McCreath, u4033585
		File f = json;
		persons = new ArrayList<Person>();
		try {
			JSONObject obj = (JSONObject) JSONValue.parse(new FileReader(f));
			
			Person temp = new Person();
			String jFName = null;
			String jLName = null;
			String jNumber = null;
			String jDate = null;
			String jMonth = null;
			String jYear = null;
			String jEmail = null;
			String jAddress = null;
			String jImage = null;
			
			for (int i=0; i<obj.size(); i++) {
				if (i % 9 == 0 && i != 0) {
					temp = new Person(jFName, jLName, jNumber, jDate, jMonth, jYear, jEmail, jAddress, jImage);
					persons.add(temp);
				}
				
				if (i % 9 == 0) {
					if (obj.get("FirstName") != null) {
						jFName = (String) obj.get("FirstName");
					}
					
					else {
						jFName = "#";
					}
				}
				
				if (i % 9 == 1) {
					if (obj.get("LastName") != null) {
						jLName = (String) obj.get("LastName");
					}
					
					else {
						jLName = "#";
					}
				}
				
				if (i % 9 == 2) {
					if (obj.get("Number") != null) {
						jNumber = (String) obj.get("Number");
					}
					
					else {
						jNumber = "#";
					}
				}
				
				if (i % 9 == 3) {
					if (obj.get("Date") != null) {
						jDate = (String) obj.get("Date");
					}
					
					else {
						jDate = "#";
					}
				}
				
				if (i % 9 == 4) {
					if (obj.get("Month") != null) {
						jMonth = (String) obj.get("Month");
					}
					
					else {
						jMonth = "#";
					}
				}
				
				if (i % 9 == 5) {
					if (obj.get("Year") != null) {
						jYear = (String) obj.get("Year");
					}
					
					else {
						jYear = "#";
					}
				}
				
				if (i % 9 == 6) {
					if (obj.get("Email") != null) {
						jEmail = (String) obj.get("Email");
					}
					
					else {
						jEmail = "#";
					}
				}
				
				if (i % 9 == 7) {
					if (obj.get("Address") != null) {
						jAddress = (String) obj.get("Address");
					}
					
					else {
						jAddress = "#";
					}
				}
				
				if (i % 9 == 8) {
					if (obj.get("Image") != null) {
						jImage = (String) obj.get("Image");
					}
					
					else {
						jImage = "#";
					}
				}
			}
			temp = new Person(jFName, jLName, jNumber, jDate, jMonth, jYear, jEmail, jAddress, jImage);
			System.out.println(temp);
			persons.add(temp);
		} catch (FileNotFoundException e) {
			APPLibrary.playSound(information_Not_FoundURL);
			APPLibrary.notFoundFile();
		}
		
		nameMatching = new HashMap<String, Person>();
		for (Person e : persons) {
			nameMatching.put(e.getNames(), e);
		}
		
		System.out.println(nameMatching);
		
		names = new Vector<String>();
		for (String e : nameMatching.keySet()) {
			names.add(e);
		}
		nameList.removeAll();
		this.nameList = new JList(names);
		
		pack();
		SwingUtilities.updateComponentTreeUI(this);
		
	}
	
	public void saveJson() {
		//Refer to Eric McCreath, u4033585
		File f = new File(APPLibrary.WHERE_CONTACTS_JSON_FILE);
		JSONObject obj = new JSONObject();
		
		for (Person element : persons) {
			obj.put("FirstName", element.getFirstName());
			obj.put("LastName", element.getLastName());
			obj.put("Number", element.getNumber());
			obj.put("Date", element.getDate());
			obj.put("Month", element.getMonth());
			obj.put("Year", element.getYear());
			obj.put("Email", element.getEmail());
			obj.put("Address", element.getAddress());
			obj.put("Image", element.getImage());
		}
		
		FileWriter out;
		try {
			out = new FileWriter(f);
			obj.writeJSONString(out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteMethod(Person toDelete) {
		String toDeleteName = toDelete.getNames();
		persons.remove(toDelete);
		names.remove(toDeleteName);
		nameMatching.remove(toDeleteName);
	}
	
	public void showPersonInformation() {
		personInfo.removeAll();
		
		String chosenName = (String) nameList.getSelectedValue();
		Person toShow = nameMatching.get(chosenName);
		
		System.out.println("chosen: " + chosenName);
		String firstName = toShow.getFirstName();
		String lastName = toShow.getLastName();
		String image = toShow.getImage();
		String number = toShow.getNumber();
		String birthday = toShow.getDate() + ", " + toShow.getMonth() + ", " + toShow.getYear();
		String email = toShow.getEmail();
		String address = toShow.getAddress();
		
		JLabel infoFName = new JLabel("First Name: ");
		infoFName.setFont(APPLibrary.arialFifteen);
		personInfo.add(infoFName);
		
		JLabel labelFName = new JLabel(firstName);
		labelFName.setFont(APPLibrary.arialFifteen);
		personInfo.add(labelFName);
		
		JLabel infoLName = new JLabel("Last Name: ");
		infoLName.setFont(APPLibrary.arialFifteen);
		personInfo.add(infoLName);
		
		JLabel labelLName = new JLabel(lastName);
		labelLName.setFont(APPLibrary.arialFifteen);
		personInfo.add(labelLName);
		
		JLabel infoImage = new JLabel("Profile");
		infoImage.setFont(APPLibrary.arialFifteen);
		personInfo.add(infoImage);
		
		ImageIcon profile = new ImageIcon(image);
		JLabel ico = new JLabel(profile);
		
		ico.setPreferredSize(new Dimension(30, 30));
		
		personInfo.add(ico);
		
		
		
		JLabel infoNumber = new JLabel("Phone Number: ");
		infoNumber.setFont(APPLibrary.arialFifteen);
		personInfo.add(infoNumber);
		
		JLabel labelNumber = new JLabel(number);
		labelNumber.setFont(APPLibrary.arialFifteen);
		personInfo.add(labelNumber);
		
		JLabel infoBirthday = new JLabel("Birthday: ");
		infoBirthday.setFont(APPLibrary.arialFifteen);
		personInfo.add(infoBirthday);
		
		JLabel labelBirthday = new JLabel(birthday);
		labelBirthday.setFont(APPLibrary.arialFifteen);
		personInfo.add(labelBirthday);
		
		JLabel infoEmail = new JLabel("Email: ");
		infoEmail.setFont(APPLibrary.arialFifteen);
		personInfo.add(infoEmail);
		
		JLabel labelEmail = new JLabel(email);
		labelEmail.setFont(APPLibrary.arialFifteen);
		personInfo.add(labelEmail);
		
		JLabel infoAddress = new JLabel("Address: ");
		infoAddress.setFont(APPLibrary.arialFifteen);
		personInfo.add(infoAddress);
		
		JLabel labelAddress = new JLabel(address);
		labelAddress.setFont(APPLibrary.arialFifteen);
		personInfo.add(labelAddress);
		
		container.add(personInfo);
		pack();
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public static void main(String[] args) throws URISyntaxException, IOException {
		AsiaBook launch = new AsiaBook();
		launch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
