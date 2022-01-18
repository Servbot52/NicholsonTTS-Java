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
	JSpinner wpmSpinner;
	
	WPMListener(Settings settings, JSpinner wpmSpinner){
		this.settings = settings;
		this.wpmSpinner = wpmSpinner;
	}
	@Override
	public void stateChanged(ChangeEvent e)  {
		if(settings.getWPM() != (float) wpmSpinner.getValue()) {
			settings.setWPM( (float) wpmSpinner.getValue());
			//in case the it's a invalid number 
			if(settings.getWPM() != (float) wpmSpinner.getValue()) {
				wpmSpinner.setValue(settings.getWPM());
			}
		}
	}
}