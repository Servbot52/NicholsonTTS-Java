import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

class TextSpeech {
	SpeechWaiter speechWaiter;
	
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
		//make sure voice is up to date.
		Voice voice = VoiceManager.getInstance().getVoice("kevin16");
		
	    voice.allocate();// Allocating Voice
	    voice.setRate(190);// Setting the rate of the voice
	    voice.setPitch(150);// Setting the Pitch of the voice
	    voice.setVolume(3);// Setting the volume of the voice
	    
		thread = new SpeakingThread(theSection, speechWaiter, voice);
		thread.start();
	}
	
	private String voiceName;
	String[] voiceOptions() {
		Voice[] voices = VoiceManager.getInstance().getVoices();
		String[] names = new String[voices.length];
		for(int i = 0; i < voices.length; i++) {
			names[i] = voices[i].getName();
		}
		return names;
	}
	
}
