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
		if(e.getSource().getClass() == JSpinner.class) {
			System.out.println("class jspinner");
			wpmChange((JSpinner) e.getSource());
		}
	}
	
	private void wpmChange(JSpinner wpmSpinner) {
		//JSpinner wpmSpinner = (JSpinner) e.getSource();
		float spinnerValue = ((Double) wpmSpinner.getValue()).floatValue();
		if( settings.getWPM() != spinnerValue) {
			settings.setWPM( spinnerValue );
		}
	}
	
}