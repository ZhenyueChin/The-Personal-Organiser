package comp2100.ass1;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.junit.Test;

public class ContactsTest {
	
	@Test
	public void testOpenFile() {
		AddContacts testAdd = new AddContacts();
		testAdd.addToList("Zhen-Yue", "Ch'in", "12345", "15", "9", "1994", "u5505995@anu.edu.au", "Earth", "#");
		
		ArrayList<Person> testArray = new ArrayList<Person>();
		Person testPerson = new Person("Zhen-Yue", "Ch'in", "12345", "15", "9", "1994", "u5505995@anu.edu.au", "Earth", "#");
		testArray.add(testPerson);
		
		assertTrue(testAdd.persons.get(testAdd.persons.size() - 1).toString().equals(testPerson.toString()));
	}
	
	@Test
	public void testDeletePerson() {
		ContactsBook testDelete;
		try {
			testDelete = new ContactsBook();
			int originalSize = testDelete.persons.size();
			Person testPerson = new Person("Zhen-Yue", "Ch'in", "12345", "15", "9", "1994", "u5505995@anu.edu.au", "Earth", "#");
			testDelete.persons.add(testPerson);
			testDelete.deleteMethod(testPerson);
			assertTrue(testDelete.persons.size() == originalSize);
		} catch (URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}