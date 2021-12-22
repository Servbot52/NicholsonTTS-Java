import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.swing.JButton;

class SpeechWaiter{

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
	boolean isNextPartReady() {
		if(readQueue.currentElement == null) {
			return false;
		}
		else if(readQueue.currentElement.getNextSection() == null) {
			return false;
		}
		System.out.println("c");
		return true;
	}
	
	void speakingDone() {
		firstElement = false;
		isSpeaking = false;
		if(isNextPartReady() && getContinueReading()) {
			getReadQueue().currentElement = getReadQueue().currentElement.getNextSection();
			read();
		}
	}
	void newSectionReady(Section newSection) {
		readQueue.addSection(newSection);
		if(isSpeaking == false && getContinueReading()) {
			if(firstElement == false)
				getReadQueue().currentElement = getReadQueue().currentElement.getNextSection();
			read();
		}
	}
	void read() {
		if(getContinueReading() == false)
			return;
		
		isSpeaking = true;
		Section readSection = getReadQueue().currentElement;
		getTextSpeech().speakSection(readSection);
	}
	void pause() {
		setContinueReading(false);
		getTextSpeech().stopSpeaking();
		isSpeaking = false;
	}
	private void stopReading() {
		needNewText = true;
		setContinueReading(false);
		endThread();
	}
	
	boolean needNewText = true;
	private boolean firstElement = true;
	void inputPlayNew(String selectedText, int selectedStart) {
		firstElement = true;
		needNewText = false;
		playTimeStamp = System.currentTimeMillis();
		System.out.println(selectedText);
		getReadQueue().startBuildingSections(selectedText, selectedStart);
		setContinueReading(true);
		//queueLoop();
		allSectionsBuild = false;


		QueueThread qThread = new QueueThread(getTimeStamp(), this);
		qThread.start();
	}
	void inputContinue() {
		setContinueReading(true);
		//queueLoop();
		allSectionsBuild = false;
		
		//resume instruction
		
	}
	void inputPause() {
		
		pause();
	}
	
	
	private void endThread() {
		SectionInstruction sEnder = new SectionInstruction("END", playTimeStamp);
		try {
			sectionBlockQueue.put(sEnder);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


