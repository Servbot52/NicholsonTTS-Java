import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class WPMListener implements ChangeListener{
	Settings settings;
	
	WPMListener(Settings settings){
		this.settings = settings;
	}
	
	@Override
	public void stateChanged(ChangeEvent e)  {
		JSpinner wpmSpinner = (JSpinner) e.getSource();
		float spinnerValue = ((Double) wpmSpinner.getValue()).floatValue();
		if( settings.getWPM() != spinnerValue) {
			settings.setWPM( spinnerValue );
			//in case the it's a invalid number 
			if(settings.getWPM() != spinnerValue) {
				wpmSpinner.setValue( (double) settings.getWPM());
			}
		}
	}
}