import com.sun.speech.freetts.Voice;

public class SpeakingThread extends Thread{
	
	private Section theSection;
	private String whatToSay() { return theSection.getReadText(); }
	private SpeechWaiter waiter;
	private Voice theVoice;
	
	SpeakingThread(Section theSection, SpeechWaiter sW, Voice v){
		this.theSection = theSection;
		waiter = sW;
		theVoice = v;
	}
	
	public void run() {
		theVoice.speak(whatToSay());
		
	    try {
			waiter.sectionBlockQueue.put(new SectionInstruction("VoiceEnd", theSection.getTimeStamp()));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
