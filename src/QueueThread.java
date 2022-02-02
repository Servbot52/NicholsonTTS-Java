import java.util.concurrent.BlockingQueue;

public class QueueThread extends Thread {
	QueueThread(long timeStamp, SpeechWaiter sWaiter, ReadQueue readQueue, PaneScroll pScroll){
		this.timeStamp = timeStamp;
		this.sWaiter = sWaiter;
		this.readQueue = readQueue;
		this.pScroll = pScroll;
	}
	
	private ReadQueue readQueue;
	long timeStamp;
	
	private PaneScroll pScroll;
	SpeechWaiter sWaiter;
	
	boolean allSectionsBuild = false;
	
	BlockingQueue<Section> sectionBlockQueue() {return sWaiter.sectionBlockQueue; }
	public void run() {
		int count = 0;
		while(getContinueReading()) {
			System.out.println("loop: " + count);
			count++;
			try {
				
				Section newItem = sectionBlockQueue().take();
				System.out.println("Section: " + newItem.getReadText());
				if(newItem.getTimeStamp() == timeStamp) {
					if(newItem.getStart() == -1) {
						switch(newItem.getSectionText()) {
						case "END":
							pScroll.finishReadingArea();
							pause();
							setContinueReading(false);
							break;
						case "STEPFORWORD":
							stepForword();
							break;
						case "STEPBACK":
							stepBack();
							break;
						case "RESUME":
							sWaiter.isPaused = false;
							read();
							break;
						case "PAUSE":
							pause();
							break;
						case "allSectionsBuilt":
							allSectionsBuild = true;
							break;
						case "VoiceEnd":
							if(allSectionsBuild && sWaiter.getReadQueue().isCurrentFinal()) {
								thisContinue = false;
								setContinueReading(false);
							}
							speakingDone();
							break;
						}						
					}else {
						newSectionReady(newItem);
					}
				}
				
				System.out.println(count + " " + getContinueReading() + " : " + allSectionsBuild + " : " + sWaiter.getReadQueue().isCurrentFinal());
				System.out.println();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
			}
		}
		if(timeStamp == sWaiter.getTimeStamp()) {
			sWaiter.needNewText = true;
		}
		System.out.println("Thread loop end.");
	}
	
	private boolean thisContinue = true;
	private void setContinueReading(boolean bool) {
		thisContinue = bool;
		sWaiter.setContinueReading(bool);
	}
	private boolean getContinueReading() {return thisContinue && sWaiter.getContinueReading(); }

	private void stepForword() {
		pScroll.finishReadingArea();
		if(sWaiter.isPaused) {
			readQueue.stepForward();
		}else{
			pause();
			readQueue.stepForward();
			sWaiter.isPaused = false;
			read();
		}
	}
	private void stepBack() {
		pScroll.finishReadingArea();
		if(sWaiter.isPaused) {
			readQueue.stepBack();
		}else{
			pause();
			readQueue.stepBack();
			sWaiter.isPaused = false;
			read();
		}
	}
	
	void newSectionReady(Section newSection) {
		readQueue.addSection(newSection);
		if(sWaiter.isSpeaking == false && getContinueReading()) {
			pScroll.finishReadingArea();
			
			if(sWaiter.isPaused)
				return;
			
			if(sWaiter.firstElement == false)
				sWaiter.getReadQueue().stepForward();
			read();
		}
	}
	
	
	void pause() {
		sWaiter.isPaused = true;
		
		sWaiter.getTextSpeech().stopSpeaking();
		sWaiter.isSpeaking = false;
	}
	
	void speakingDone() {
		sWaiter.firstElement = false;
		sWaiter.isSpeaking = false;
		if(sWaiter.isNextPartReady() && getContinueReading()) {
			pScroll.finishReadingArea();
			
			if(sWaiter.isPaused)
				return;
			
			sWaiter.getReadQueue().stepForward();
			read();
		}
	}
	void read() {
		if(sWaiter.isPaused)
			return;
		
		if(getContinueReading() == false)
			return;
		
		if(sWaiter.isSpeaking)
			return;
		
		sWaiter.isSpeaking = true;
		Section readSection = sWaiter.getReadQueue().currentElement;
		pScroll.readingNewArea(readSection.getStart(), readSection.getEnd() - readSection.getStart());
		sWaiter.getTextSpeech().speakSection(readSection);
	}
}
