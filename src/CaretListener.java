import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class CaretListener implements ChangeListener{
	SpeechWaiter speechWaiter;
	
	CaretListener(SpeechWaiter sW){
		speechWaiter = sW;
	}
	
	@Override
	public void stateChanged(ChangeEvent e)  {
		speechWaiter.inputEnd();
	}
}