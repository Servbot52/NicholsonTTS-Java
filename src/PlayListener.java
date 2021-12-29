import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JTextPane;

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
		if(mainField.getCaretPosition() == mainField.getText().length() && mainField.getSelectedText() == null) {
			mainField.moveCaretPosition(0);
		}
		
		if(speechWaiter.needNewText) {
			newText();
		}else if(speechWaiter.getContinueReading()) {
			speechWaiter.inputPause();
		}else {
			speechWaiter.inputResume();
		}
	}
	
	private void newText() {
		String fullText;
		int point_A;
		int point_B;
		if(mainField.getSelectedText() != null) {
			point_A = mainField.getSelectionStart();
			point_B = mainField.getSelectionEnd();
		}else{
			point_A = mainField.getCaretPosition();
			point_B = mainField.getText().length();
		}
		if(point_A == point_B)
			return;
		
		fullText = mainField.getText().substring(point_A, point_B);
		speechWaiter.inputPlayNew(fullText, point_A);
	}
}