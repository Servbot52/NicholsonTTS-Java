import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

class TextSpeech {
	private SpeechWaiter speechWaiter;
	
	TextSpeech(SpeechWaiter sWaiter){
		speechWaiter = sWaiter;
		System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
	    
		//need to search for voices to use;
	}
	
	private SpeakingThread thread;
	void stopSpeaking() {
		if(thread == null)
			return;
		try {
			thread.theVoice.deallocate();
		} catch(Exception e) {
			System.out.println("Stop Speaking Error");
		}
	}
	
	
	void speakSection(Section theSection)  {
		voiceOptions();
		//make sure voice is up to date.
		Voice voice = VoiceManager.getInstance().getVoice(getVoice());
		
	    voice.allocate();// Allocating Voice
	    voice.setRate(getWPM());// Setting the rate of the voice
	    voice.setPitch(150);// Setting the Pitch of the voice
	    voice.setVolume(3);// Setting the volume of the voice
	    
		thread = new SpeakingThread(theSection, speechWaiter, voice);
		thread.start();
	}
	
	private String voiceName = "kevin16";
	String getVoice() {
		if(voiceName == null) {
			return VoiceManager.getInstance().getVoices()[0].getName();
		}
		return voiceName;
	}
	void setVoice(String newVoiceName) {
		for(Voice v : VoiceManager.getInstance().getVoices()) {
			if(v.getName() == newVoiceName)
				voiceName = newVoiceName;
		}
	}
	String[] voiceOptions() {
		Voice[] voices = VoiceManager.getInstance().getVoices();
		String[] names = new String[voices.length];
		for(int i = 0; i < voices.length; i++) {
			System.out.println("Voice: "+voices[i].getName());
			names[i] = voices[i].getName();
		}
		return names;
	}
	
	
	private float wpm = 190;
	float getWPM() { return wpm; }
	void setWPM(float wpm) { this.wpm = wpm; }
}
