import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;  
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TextWindow extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JTextPane mainField; 
	protected JButton buttonMoveBack, buttonPlay, buttonMoveForword;
	//PaneScroll paneScroll;
	JFrame theFrame;
	JScrollPane scroll;
	
	SpeechWaiter speechWaiter;
	//ReadQueue readQueue() {return speechWaiter.getReadQueue();}
	
	TextWindow (){
		buttonPlay = new JButton(">");
		
		theFrame = new JFrame("NicholsonTTS");
		
		//what happens when closed.
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		

		
		mainField = new JTextPane();
		mainField.setText("Hold on to your hats. I don't know if this will work.");
		scroll = new JScrollPane (mainField, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		
		theFrame.getContentPane().add(scroll, BorderLayout.CENTER);
		theFrame.getContentPane().add(buttonPlay, BorderLayout.PAGE_START);

		theFrame.pack();
		theFrame.setVisible(true);
		
	}
	
	
	
}