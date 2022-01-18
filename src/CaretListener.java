import javax.swing.JSpinner;
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

class WPMListener implements ChangeListener{
	Settings settings;
	
	WPMListener(Settings settings){
		this.settings = settings;
	}
	@Override
	public void stateChanged(ChangeEvent e)  {
		JSpinner wpmSpinner = (JSpinner) e.getSource();
		
		if(settings.getWPM() != (float) wpmSpinner.getValue()) {
			settings.setWPM( (float) wpmSpinner.getValue());
			
			//in case the it's a invalid number 
			if(settings.getWPM() != (float) wpmSpinner.getValue()) {
				wpmSpinner.setValue(settings.getWPM());
			}
		}
	}
}