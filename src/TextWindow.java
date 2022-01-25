import java.awt.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;

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
	JSpinner WPMBox;
	JComboBox<String> voiceNameBox;
	
	SpeechWaiter speechWaiter;
	//ReadQueue readQueue() {return speechWaiter.getReadQueue();}
	
	TextWindow (Settings settings){
		buttonPlay = new JButton(">");
		buttonMoveBack = new JButton("<<");
		
		buttonMoveForword = new JButton(">>");
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(buttonMoveBack, LEFT_ALIGNMENT);
		buttonPanel.add(buttonPlay, CENTER_ALIGNMENT);
		buttonPanel.add(buttonMoveForword, RIGHT_ALIGNMENT);
		
		
		
		SpinnerNumberModel wpmModel = new SpinnerNumberModel(settings.getWPM(), settings.getMinWPM(), settings.getMaxWPM(), (float) 10);
		WPMBox = new JSpinner(wpmModel);
		WPMBox.setValue(settings.getWPM());
		WPMBox.addChangeListener(new WPMListener(settings));
		
		
		
		voiceNameBox = new JComboBox<String>(settings.getVoiceOptions());
		voiceNameBox.addActionListener(new VoiceNameListener(settings));
		
		
		JPanel spinnerPanel = new JPanel();
		spinnerPanel.add(voiceNameBox, LEFT_ALIGNMENT);
		spinnerPanel.add(WPMBox, CENTER_ALIGNMENT);
		
		JPanel barPanel = new JPanel();
		barPanel.add(buttonPanel, LEFT_ALIGNMENT);
		barPanel.add(spinnerPanel, CENTER_ALIGNMENT);
		
		theFrame = new JFrame("NicholsonTTS");
		
		//what happens when closed.
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		

		
		mainField = new JTextPane();
		mainField.setText("Hold on to your hats. I don't know if this will work.");
		scroll = new JScrollPane (mainField, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		
		theFrame.getContentPane().add(scroll, BorderLayout.CENTER);
		theFrame.getContentPane().add(barPanel, BorderLayout.PAGE_START);

		theFrame.pack();
		theFrame.setVisible(true);
		
	}
	
	
	
}