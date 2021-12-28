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
	//private static final long serialVersionUID = 1L;
	protected JTextPane mainField; 
	protected JButton buttonMoveBack, buttonPlay, buttonMoveForword;
	PaneScroll paneScroll;
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
		scroll = new JScrollPane (mainField, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		theFrame.getContentPane().add(scroll, BorderLayout.CENTER);
		theFrame.getContentPane().add(buttonPlay, BorderLayout.PAGE_START);

		theFrame.pack();
		theFrame.setVisible(true);
		
		
		
		
		//move following to launcher?
		speechWaiter = new SpeechWaiter(buttonPlay, paneScroll);

		speechWaiter.playButton = buttonPlay;
		
		PlayListener pListener = new PlayListener(speechWaiter, buttonPlay, mainField);
		buttonPlay.addActionListener(pListener);
		
		
		//JPanel menuPanel = new JPanel();
		
	}
	
	
	
	
	
}

class PlayListener implements ActionListener{
	SpeechWaiter speechWaiter;
	JButton playButton;
	JTextPane mainField;
	
	PlayListener(SpeechWaiter sW, JButton playB, JTextPane mainField){
		speechWaiter = sW;
		playButton = playB;
		this.mainField = mainField;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(speechWaiter.needNewText) {
			newText();
		}else if(speechWaiter.getContinueReading()) {
			speechWaiter.inputPause();
		}else {
			speechWaiter.inputContinue();
		}
	}
	
	private void newText() {
		String fullText;
		int point_A;
		int point_B;
		if(mainField.getSelectedText() != null) {
			point_A = mainField.getSelectionStart();
			point_B = mainField.getSelectionEnd();
		}else {
			point_A = mainField.getCaretPosition();
			point_B = mainField.getText().length();
		}
		fullText = mainField.getText().substring(point_A, point_B);
		speechWaiter.inputPlayNew(fullText, point_A);
	}
}

class CaretListener implements ChangeListener{
	SpeechWaiter speechWaiter;
	JTextField mainField;
	
	CaretListener(SpeechWaiter sW, JTextField mainField){
		speechWaiter = sW;
		this.mainField = mainField;
	}
	
	@Override
	public void stateChanged(ChangeEvent e)  {
		speechWaiter.needNewText = true;
		speechWaiter.pause();
	}
}