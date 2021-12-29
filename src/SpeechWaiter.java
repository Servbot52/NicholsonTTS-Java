import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.swing.JButton;

class SpeechWaiter{
	SpeechWaiter(JButton playButton, PaneScroll pScroll){
		System.out.println("this is working?");
		this.playButton = playButton;
		this.pScroll = pScroll;
	}
	
	
	JButton playButton;
	private boolean continueReading = false;
	boolean getContinueReading() {
		System.out.println("2");
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
	
	private TextSpeech textSpeech;
	TextSpeech getTextSpeech() {
		if(textSpeech == null) {
			textSpeech = new TextSpeech(this);
		}
		return textSpeech;
	}
	
	boolean isSpeaking = false;
	private boolean isNextPartReady() {
		if(readQueue.currentElement == null) {
			return false;
		}
		else if(readQueue.currentElement.getNextSection() == null) {
			return false;
		}
		return true;
	}
	
	void speakingDone() {
		firstElement = false;
		isSpeaking = false;
		if(isNextPartReady() && getContinueReading()) {
			System.out.println("preparing to speak.");
			pScroll.finishReadingArea();
			getReadQueue().currentElement = getReadQueue().currentElement.getNextSection();
			read();
		}
	}
	void newSectionReady(Section newSection) {
		readQueue.addSection(newSection);
		if(isSpeaking == false && getContinueReading()) {
			pScroll.finishReadingArea();
			
			if(firstElement == false)
				getReadQueue().currentElement = getReadQueue().currentElement.getNextSection();
			read();
		}
	}
	void read() {
		if(isPaused)
			return;
		if(getContinueReading() == false)
			return;
		
		isSpeaking = true;
		Section readSection = getReadQueue().currentElement;
		System.out.println("got here");
		pScroll.readingNewArea(readSection.getStart(), readSection.getEnd() - readSection.getStart());
		getTextSpeech().speakSection(readSection);
	}
	
	private boolean isPaused = false;
	
	void pause() {
		isPaused = true;
		SectionInstruction pauseReading = new SectionInstruction("PAUSE", playTimeStamp);
		try {
			sectionBlockQueue.put(pauseReading);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getTextSpeech().stopSpeaking();
		isSpeaking = false;
	}
	void stopReading() {
		needNewText = true;
		setContinueReading(false);
		endThread();
		getTextSpeech().stopSpeaking();
		isSpeaking = false;
	}
	
	boolean needNewText = true;
	private boolean firstElement = true;
	void inputPlayNew(String selectedText, int selectedStart) {
		isPaused = false;
		stopReading();
		firstElement = true;
		needNewText = false;
		playTimeStamp = System.currentTimeMillis();
		System.out.println(selectedText);
		getReadQueue().startBuildingSections(selectedText, selectedStart);
		setContinueReading(true);
		
		allSectionsBuild = false;


		QueueThread qThread = new QueueThread(getTimeStamp(), this);
		qThread.start();
	}
	void inputResume() {
		isPaused = false;
		SectionInstruction pauseReading = new SectionInstruction("RESUME", playTimeStamp);
		try {
			sectionBlockQueue.put(pauseReading);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void inputPause() {
		
		pause();
	}
	
	
	private void endThread() {
		needNewText = true;
		SectionInstruction sEnder = new SectionInstruction("END", playTimeStamp);
		try {
			sectionBlockQueue.put(sEnder);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pause();
	}
}


