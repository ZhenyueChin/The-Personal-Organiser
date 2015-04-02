package comp2100.ass1;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * This class is for user to modify new person
 * This is based on the class "AddContacts"
 * @author Zhenyue Qin, u5505995
 *
 */

public class ModifyContacts extends JFrame implements Serializable{
	/* The ArrayList persons are all the objects stored */
	static ArrayList<Person> persons = new ArrayList<Person>();
	
	/* These object are for JComboBox */
	static Object selectedDate;
	static Object selectedMonth;
	static Object selectedYear;
	
	static String imageName;
	
	public ModifyContacts(Person toModify) {
		super("Modify the Contacts");
		
		String mFName = toModify.getFirstName();
		String mLName = toModify.getLastName();
		final String mImage = toModify.getImage();
		String mNumber = toModify.getNumber();
		String mYear = toModify.getYear();
		String mAddress = toModify.getAddress();
		String mEmail = toModify.getEmail();
		
		/* The container is everything contained in the frame */
		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		container.setBackground(Color.WHITE);
	
		/* These are the words in the top */
		JLabel topWords = new JLabel();
		topWords.setText("Please input the information");
		topWords.setFont(APPLibrary.arialFifteen);
		topWords.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel heading = new JLabel();
		heading.setText("Modify Contact");
		heading.setFont(APPLibrary.arielTwentyFive);
		heading.setHorizontalAlignment(JLabel.CENTER);
		topWords.setForeground(Color.GRAY);
		
		/* Put the words into one panel */
		JPanel headings = new JPanel();
		headings.setLayout(new GridLayout(0, 1));
		headings.add(heading);
		headings.add(topWords);
		
		/* These are the buttons */
		JPanel inputs = new JPanel();
		inputs.setLayout(new GridLayout(0, 2));
		inputs.setBackground(Color.WHITE);
		
		/* These are the input fields */
		JLabel infoFirstName = new JLabel();
		infoFirstName.setText("First Name: ");
		inputs.add(infoFirstName);
		
		final JTextField firstName = new JTextField(mFName);
		firstName.setPreferredSize(new Dimension(140, 40));
		inputs.add(firstName);
		
		JLabel infoLastName = new JLabel();
		infoLastName.setText("Last Name: ");
		
		final JTextField lastName = new JTextField(mLName);
		lastName.setPreferredSize(new Dimension(140, 40));
		inputs.add(infoLastName);
		inputs.add(lastName);
		
		/* This is to add image */
		/* The idea is to copy the image to the current directory and save the path */
		/* TRICKY PART */
		JButton addImage = new JButton("Add the Profile");
		addImage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//File Chooser: https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html
				
				/* Get the image from the file chooser */
				JFileChooser fc = new JFileChooser();
				fc.showSaveDialog(ModifyContacts.this);
				
				/* The image */
				File chosenfile = fc.getSelectedFile();
				
				String newName = chosenfile.getName();
				
				imageName = APPLibrary.getPath + newName;
				
				/* New Image */
				File newFile = new File(APPLibrary.getPath + newName); 
				newFile.getParentFile().mkdirs(); 
				try {
					newFile.createNewFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					APPLibrary.copyFile(chosenfile, newFile);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
		JLabel infoNumber = new JLabel();
		infoNumber.setText("Phone number: ");
		
		final JTextField number = new JTextField(mNumber);
		number.setPreferredSize(new Dimension(140, 40));
		inputs.add(infoNumber);
		inputs.add(number);
		
		JLabel infoBirthday = new JLabel();
		infoBirthday.setText("Birthday (DD/MM/YYYY)");
		final JTextField theBirthYear = new JTextField(mYear);
		theBirthYear.setPreferredSize(new Dimension(100,40));
		inputs.add(infoBirthday);

		/* This is to generate elements for JComboBox */
		String[] dates = new String[32];
		for (int i=1; i<=31; i++) {
			dates[i] = Integer.toString(i);
		}
		
		/* Put the elements inside */
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
		
		/* This is to generate elements for JComboBox */
		String[] months = new String[13];
		for (int i=1; i<=12; i++) {
			months[i] = Integer.toString(i);
		}
		
		/* Put the elements inside */
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
		
		/* The zone for the birthday */
		JPanel infoBirth = new JPanel();
		infoBirth.add(date);
		infoBirth.add(month);
		infoBirth.add(theBirthYear);
		inputs.add(infoBirth);
		
		JLabel infoEmail = new JLabel();
		infoEmail.setText("Email Address: ");
		
		final JTextField email = new JTextField(mEmail);
		email.setPreferredSize(new Dimension(250,40));
		inputs.add(infoEmail);
		inputs.add(email);
		
		JLabel infoAddress = new JLabel();
		infoAddress.setText("Address: ");

		final JTextField address = new JTextField(mAddress);
		address.setPreferredSize(new Dimension(300,40));
		inputs.add(infoAddress);
		inputs.add(address);
		
		/* When the input has been finished, save it! */
		JButton done = new JButton();
		done.setText("Done");
		done.setPreferredSize(new Dimension(30, 40));
		
		done.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				/* Get all the input information */
				String fName = firstName.getText();
				String lName = lastName.getText();
				String numberString = number.getText();
				String tBirthday = theBirthYear.getText();
				String tEmail = email.getText();
				String tAddress = address.getText(); 
				String tImage = imageName;
				
				/* Avoid the empty input */
				if (fName.equals("")) {
					fName = "#";
				}
				
				if (lName.equals("")) {
					lName = "#";
				}
				
				if (numberString.equals("")) {
					numberString = "#";
				}
				
				if (tImage == null) {
					tImage = mImage;
				}
				
				if (selectedDate == null) {
					selectedDate = "1";
				}
				
				if (selectedMonth == null) {
					selectedMonth = "1";
				}
				
				/* Open the original file first */
				try {
					FileInputStream fileIn = new FileInputStream(APPLibrary.WHERE_CONTACTS_FILE);
			        ObjectInputStream in = new ObjectInputStream(fileIn);
			        persons = (ArrayList<Person>) in.readObject(); 
			        in.close();
			        fileIn.close();
			    } catch(Exception exc) {
			    	System.out.println("A new file will be generated");
			    }

				/* New object to add */
				Person toAdd = new Person(fName, lName, numberString, (String) selectedDate, (String) selectedMonth, tBirthday, tEmail, tAddress, tImage);
				persons.add(toAdd);
				
				/* Write to the file */
				try{
					FileOutputStream fout = new FileOutputStream(APPLibrary.WHERE_CONTACTS_FILE);
					ObjectOutputStream oos = new ObjectOutputStream(fout); 
					oos.writeObject(persons);
					oos.close();
					System.out.println("Done");
					
				} catch(Exception ex) {
					ex.printStackTrace();
				}
				
				try {
					ContactsBook newOpen = new ContactsBook();
				} catch (URISyntaxException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				dispose();
			}
		});
		
		JButton close = new JButton("Cancel");
		close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
			
		});
		
		/* The Buttons at the buttom */
		JPanel buttons = new JPanel(new GridLayout(0, 3));
		buttons.add(done);
		buttons.add(addImage);
		buttons.add(close);
		
		JPanel below = new JPanel(new BorderLayout());
		below.add(inputs, BorderLayout.CENTER);
		below.add(buttons, BorderLayout.PAGE_END);
		
		container.add(headings, BorderLayout.PAGE_START);
		container.add(below, BorderLayout.CENTER);
		
		/* When close the window, the application terminates */
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pack();
		setVisible(true);
	}
	
	
}