import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;  
import javax.swing.JFrame;  
//import javax.swing.JLabel;  
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TextWindow extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JTextArea mainField; 
	protected JButton buttonMoveBack, buttonPlay, buttonMoveForword;
	JFrame theFrame;
	JScrollPane scroll;
	
	SpeechWaiter speechWaiter;
	ReadQueue readQueue() {return speechWaiter.getReadQueue();}
	
	TextWindow (){
		speechWaiter = new SpeechWaiter();
		buttonPlay = new JButton(">");
		speechWaiter.playButton = buttonPlay;
		
		theFrame = new JFrame("NicholsonTTS");
		
		//what happens when closed.
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		

		
		
		
		//play Button
		
		mainField = new JTextArea();
		scroll = new JScrollPane (mainField, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		
		
		PlayListener pListener = new PlayListener(speechWaiter, buttonPlay, mainField);
		buttonPlay.addActionListener(pListener);
		
		theFrame.getContentPane().add(scroll, BorderLayout.CENTER);
		theFrame.getContentPane().add(buttonPlay, BorderLayout.PAGE_START);
		
		

		theFrame.pack();
		theFrame.setVisible(true);
		//JPanel menuPanel = new JPanel();
		
		
		
		
	}
	
	
	
	
	
}

class PlayListener implements ActionListener{
	SpeechWaiter speechWaiter;
	JButton playButton;
	JTextArea mainField;
	
	PlayListener(SpeechWaiter sW, JButton playB, JTextArea mainField){
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