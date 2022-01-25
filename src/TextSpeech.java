import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

class TextSpeech {
	private SpeechWaiter speechWaiter;
	private Settings settings;
	
	TextSpeech(SpeechWaiter sWaiter, Settings settings){
		speechWaiter = sWaiter;
		this.settings = settings;
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
		Voice voice = VoiceManager.getInstance().getVoice(settings.getVoiceName());
		
		System.out.println("WPM: " + settings.getWPM());
	    voice.allocate();// Allocating Voice
	    voice.setRate(settings.getWPM());// Setting the rate of the voice
	    voice.setPitch(150);// Setting the Pitch of the voice
	    voice.setVolume(3);// Setting the volume of the voice
	    
		thread = new SpeakingThread(theSection, speechWaiter, voice);
		thread.start();
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
	
}
