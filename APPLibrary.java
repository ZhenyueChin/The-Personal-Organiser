package comp2100.ass1;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.channels.FileChannel;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

/**
 * This is the attributes or methods used frequenty for the application
 * @author Zhenyue Qin, u5505995
 *
 */

public class APPLibrary {
	/* The fonts for the application */
	public static Font arialRoundedThirty = new Font("Arial Rounded MT Bold",Font.ROMAN_BASELINE ,30);
	public static Font arialFifteen = new Font("Arial", Font.ROMAN_BASELINE, 15);
	public static Font arielTwentyFive = new Font("Arial",Font.ITALIC ,25);
	
	public static String CONTACTS_FILE_NAME = "CONTACTS.XML";
	public static String TODO_FILE_NAME = "TODO.XML";
	
	public static String CONTACTS_JSON_FILE_NAME = "CONTACTS_JSON.XML";
	
	public static String WHERE_CONTACTS_FILE = System.getProperty("user.dir") + "//" + CONTACTS_FILE_NAME;
	public static String WHERE_TODO_FILE = System.getProperty("user.dir") + "//" + TODO_FILE_NAME;
	public static String WHERE_CONTACTS_JSON_FILE = System.getProperty("user.dir") + "//" + CONTACTS_JSON_FILE_NAME;
	
	public static String getPath = System.getProperty("user.dir") + "/";
	
	/* How to play the sound */
	//http://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
	public static synchronized void playSound(final java.net.URL url) {
		new Thread(new Runnable() {	
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
			        AudioInputStream inputStream = AudioSystem.getAudioInputStream(url);
			        clip.open(inputStream);
			        clip.start(); 
			      } catch (Exception e) {
			        System.err.println(e.getMessage());
			      }
			}
			}).start();
	}
	
	/* The dialogue to show there is no file there */
	public static void notFoundFile() {
		JOptionPane.showMessageDialog(DialogueFrame.jframe,
			    "THERE IS NO FILE!",
			    "OPEN ERROR",
			    JOptionPane.ERROR_MESSAGE);
	}
	
	/* The dialogue to show there is no choice */
	public static void noChoice() {
		JOptionPane.showMessageDialog(DialogueFrame.jframe,
			    "PLEASE SELECT ONE PEOPLE!",
			    "SELECT ERROR",
			    JOptionPane.ERROR_MESSAGE);
	}
	
	/* Send email failed */
	public static void emailError() {
		JOptionPane.showMessageDialog(DialogueFrame.jframe,
			    "PLEASE INPUT CORRECT EMAIL ADDRESS!",
			    "EMAIL ERROR",
			    JOptionPane.ERROR_MESSAGE);
	}
	
	/* Copy File */
	//http://stackoverflow.com/questions/106770/standard-concise-way-to-copy-a-file-in-java
	public static void copyFile(File sourceFile, File destFile) throws IOException {
	    if(!destFile.exists()) {
	        destFile.createNewFile();
	    }

	    FileChannel source = null;
	    FileChannel destination = null;

	    try {
	        source = new FileInputStream(sourceFile).getChannel();
	        destination = new FileOutputStream(destFile).getChannel();
	        destination.transferFrom(source, 0, source.size());
	    }
	    finally {
	        if(source != null) {
	            source.close();
	        }
	        if(destination != null) {
	            destination.close();
	        }
	    }
	}
	
	public static void saveFile() {
		
	}
}
