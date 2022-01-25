import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

class Settings {
	Settings(){
		System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
	}
	String[] getVoiceOptions() {
		Voice[] voices = VoiceManager.getInstance().getVoices();
		String[] names = new String[voices.length];
		for(int i = 0; i < voices.length; i++) {
			System.out.println("Voice: "+voices[i].getName());
			names[i] = voices[i].getName();
		}
		return names;
	}
	
	
	//words per minute.
	private float maxWPM = 999;
	float getMaxWPM() { return maxWPM; }
	private float minWPM = 1;
	float getMinWPM() { return minWPM; }
	private float wpm = 200;
	float getWPM() { return wpm; }
	void setWPM(float wpm) {
		if(wpm < minWPM)
			wpm = minWPM;
		else if(wpm > maxWPM)
			wpm = maxWPM;
		
		this.wpm = wpm;
		settingsHaveChanged = true;
	}
	
	private float pitch;
	float getPitch() { return pitch; }
	void setPitch(float pitch) {
		this.pitch = pitch;
		settingsHaveChanged = true;
	}
	
	private float volume;
	float getVolume() { return volume; }
	void setVolume(float volume) {
		this.volume = volume;
		settingsHaveChanged = true;
	}
	
	private String voiceName;
	String getVoiceName() {
		return voiceName;
	}
	void setVoiceName(String voiceName) {
		settingsHaveChanged = true;
		this.voiceName = voiceName;
	}
	
	
	private boolean settingsHaveChanged = false;
	void saveSettings() {
		if(settingsHaveChanged == false)
			return;
		try {
			FileOutputStream saveFile = new FileOutputStream("NicholsonTTSSettings.sav");
			ObjectOutputStream save = new ObjectOutputStream(saveFile);
			save.writeObject(wpm);
			save.writeObject(pitch);
			save.writeObject(volume);
			save.writeObject(voiceName);
			save.close();
		}
        catch (Exception e) {
            e.getStackTrace();
        }
	}
	void loadSettings() {
		try {
			FileInputStream saveFile = new FileInputStream("NicholsonTTSSettings.sav");
			ObjectInputStream restore = new ObjectInputStream(saveFile);
			wpm = (float) restore.readObject();
			pitch = (float) restore.readObject();
			volume = (float) restore.readObject();
			voiceName = (String) restore.readObject();
			restore.close();
		} catch (Exception  e) {
			// TODO Auto-generated catch block
			wpm = 200;
			pitch = 150;
			volume = 3;
			voiceName = null;
			e.printStackTrace();
		}
	}
}
