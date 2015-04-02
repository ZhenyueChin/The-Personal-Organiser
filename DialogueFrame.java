package comp2100.ass1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

/**
 * This class is for generating dialogues
 * @author Zhenyue Qin, u5505995
 *
 */

//https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html

public class DialogueFrame implements Runnable, ActionListener{
	private static final Object EXITCOMMAND = null;
	public static JFrame jframe;
	
	public static void main(String[] args) {
		
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals(EXITCOMMAND)) {
			System.exit(0);
		}
		
	}

	@Override
	public void run() {

	}

}
