package comp2100.ass1;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The finding feature for the application
 * @author Zhenyue Qin, u5505995
 *
 */

@SuppressWarnings("serial")
public class FindLastName extends JFrame implements Serializable{
	/* The ArrayList persons are all the objects stored */
	static ArrayList<Person> persons = new ArrayList<Person>();
	
	/* The HashMap is used to find the corresponding object based on the name String in the JList */
	HashMap<String, Person> nameMatching = new HashMap<String, Person>();
	
	/* The Vector names is to store names in the JList */
	//Idea is from the Internet
	Vector<String> names = new Vector<String>();
	
	public FindLastName() {
		super("Find based on the last name");
		
		Container container = getContentPane();
		container.setLayout(new GridLayout(0, 1));
		container.setBackground(Color.WHITE);
		
		/* Load the file */
		openFile();
		
		final JTextField inputName = new JTextField("Please input the last name here");
		
		JPanel buttons = new JPanel(new GridLayout(0, 2));
		
		JButton done = new JButton("Done");
		done.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String theName = inputName.getText();
				boolean got = false;
				
				for (Person element : persons) {
					if (element.getLastName().equals(theName)) {
						java.net.URL incomingURL = this.getClass().getResource(
							    "/comp2100/ass1/IncomingTransmission.wav");
						APPLibrary.playSound(incomingURL);
						ShowInformation check = new ShowInformation(element);
						got = true;
						break;
					}
				}
				if (!got) {
					java.net.URL information_Not_FoundURL = this.getClass().getResource(
						    "/comp2100/ass1/Information_Not_Found.wav");
					APPLibrary.playSound(information_Not_FoundURL);
					DialogueFrame dialogue = new DialogueFrame();
					JOptionPane.showMessageDialog(dialogue.jframe, "Sorry! There is no this person");
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
		
		buttons.add(done);
		buttons.add(close);
		
		container.add(inputName);
		container.add(buttons);
		pack();
		setVisible(true);
		
	}
	
	/* Load the file */
	public void openFile() {
		try {
			FileInputStream fileIn = new FileInputStream(APPLibrary.WHERE_CONTACTS_FILE);
	        ObjectInputStream in = new ObjectInputStream(fileIn);
	        persons = (ArrayList<Person>) in.readObject();
	        System.out.println(persons);
	        in.close();
	        fileIn.close();
	    } catch (Exception ex) {
	    	APPLibrary.notFoundFile();
	    }
	}

	public static void main(String[] args) {
		FindLastName launch = new FindLastName();
		launch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}