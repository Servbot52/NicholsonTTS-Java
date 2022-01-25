import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class VoiceNameListener implements ActionListener {
	Settings settings;
	
	VoiceNameListener(Settings settings){
		this.settings = settings;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)  {
		@SuppressWarnings("unchecked")
		JComboBox<String> nameCombo =  (JComboBox<String>) e.getSource();
		String selectedName = (String) nameCombo.getSelectedItem();
		if(selectedName != settings.getVoiceName()) {
			settings.setVoiceName(selectedName);
		}
	}
	
}
