import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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