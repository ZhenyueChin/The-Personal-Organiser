package comp2100.ass1;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
 
 
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.awt.print.*;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
 
public class PrintingSystem implements Printable, ActionListener {
	
	static ArrayList<Person> persons = new ArrayList<Person>();
	
    public int print(Graphics g, PageFormat pf, int page) throws
                                                        PrinterException {
 
    	
    	
        if (page > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }
 
        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
 
        /* Now we perform our rendering */
        int increment = 1;
        System.out.println(persons);
        
        for (Person element : persons) {
        	g.drawString(element.getFullName(), 80, increment*50);
        	increment++;
        	g.drawString(element.getAddress(), 80, increment*50);
        	increment++;
        }
 
        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }
 
    public void actionPerformed(ActionEvent e) {
         PrinterJob job = PrinterJob.getPrinterJob();
         job.setPrintable(this);
         boolean ok = job.printDialog();
         if (ok) {
             try {
                  job.print();
             } catch (PrinterException ex) {
              /* The job did not successfully complete */
             }
         }
    }
    
    public static void openFile() {
    	try {
			FileInputStream fileIn = new FileInputStream(APPLibrary.WHERE_CONTACTS_FILE);
	        ObjectInputStream in = new ObjectInputStream(fileIn);
	        persons = (ArrayList<Person>) in.readObject();
	        in.close();
	        fileIn.close();
	    } catch (Exception ex) {
	    	
	    	APPLibrary.notFoundFile();
	    }
    }
 
    public static void main(String args[]) {
    	openFile();
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        JFrame f = new JFrame("The Printing System");
        f.addWindowListener(new WindowAdapter() {
           public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        JButton printButton = new JButton("Print the Address Book");
        printButton.addActionListener(new PrintingSystem());
        f.add("Center", printButton);
        f.pack();
        f.setVisible(true);
    }
}