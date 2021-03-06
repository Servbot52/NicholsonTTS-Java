import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.swing.JButton;

class SpeechWaiter{
	SpeechWaiter(JButton playButton, PaneScroll pScroll, Settings settings){
		this.playButton = playButton;
		this.pScroll = pScroll;
		this.settings = settings;
	}
	
	
	JButton playButton;
	private boolean continueReading = false;
	boolean getContinueReading() {
		return continueReading;
	}
	void setContinueReading(boolean bool) {
		if(bool) {
			playButton.setText("=");
		}else {
			playButton.setText(">");
		}
		continueReading = bool;
	}
	private PaneScroll pScroll;
	
	
	
	private long playTimeStamp = System.currentTimeMillis();
	long getTimeStamp() {return playTimeStamp;}
	
	
	boolean allSectionsBuild = false;
	BlockingQueue<Section> sectionBlockQueue = new ArrayBlockingQueue<Section>(100);
	
	
	private ReadQueue readQueue;
	ReadQueue getReadQueue() {
		if(readQueue == null) {
			readQueue = new ReadQueue(this);
		}
		return readQueue;
	}
	
	private Settings settings; 
	private TextSpeech textSpeech;
	TextSpeech getTextSpeech() {
		if(textSpeech == null) {
			textSpeech = new TextSpeech(this, settings);
		}
		return textSpeech;
	}
	
	boolean isSpeaking = false;
	boolean isNextPartReady() {
		if(readQueue.currentElement == null) {
			return false;
		}
		else if(readQueue.currentElement.getNextSection() == null) {
			return false;
		}
		return true;
	}
	


	
	boolean isPaused = false;
	
	
	void stopReading() {
		needNewText = true;
		setContinueReading(false);
		getTextSpeech().stopSpeaking();
		isSpeaking = false;
	}
	
	boolean needNewText = true;
	boolean firstElement = true;
	void inputPlayNew(String selectedText, int selectedStart) {
		isPaused = false;
		firstElement = true;
		needNewText = false;
		playTimeStamp = System.currentTimeMillis();
		System.out.println(selectedText);
		getReadQueue().startBuildingSections(selectedText, selectedStart);
		setContinueReading(true);
		
		allSectionsBuild = false;


		QueueThread qThread = new QueueThread(getTimeStamp(), this, getReadQueue(), pScroll);
		qThread.start();
	}
	
	
	void inputResume() {
		SectionInstruction resumeReading = new SectionInstruction("RESUME", playTimeStamp);
		try {
			sectionBlockQueue.put(resumeReading);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void inputPause() {
		SectionInstruction pauseReading = new SectionInstruction("PAUSE", playTimeStamp);
		try {
			sectionBlockQueue.put(pauseReading);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void inputStepBack() {
		try {
			sectionBlockQueue.put(new SectionInstruction("STEPBACK", playTimeStamp));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void inputStepForward() {
		try {
			sectionBlockQueue.put(new SectionInstruction("STEPFORWORD", playTimeStamp));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void inputEnd() {
		System.out.println("endThread()");
		needNewText = true;
		SectionInstruction sEnder = new SectionInstruction("END", playTimeStamp);
		try {
			sectionBlockQueue.put(sEnder);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


