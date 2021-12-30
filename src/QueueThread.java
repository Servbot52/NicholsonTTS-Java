import java.util.concurrent.BlockingQueue;

public class QueueThread extends Thread {
	QueueThread(long timeStamp, SpeechWaiter sWaiter, ReadQueue readQueue){
		this.timeStamp = timeStamp;
		this.sWaiter = sWaiter;
		this.readQueue = readQueue;
	}
	private ReadQueue readQueue;
	long timeStamp;
	
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
						if(newItem.getSectionText() == "END") {
							sWaiter.pause();
							setContinueReading(false);
						}
						if(newItem.getSectionText() == "STEPFORWORD") {
							stepForword();
						}
						if(newItem.getSectionText() == "STEPBACK") {
							stepBack();
						}
						if(newItem.getSectionText() == "RESUME") {
							sWaiter.isPaused = false;
							sWaiter.read();
						}
						if(newItem.getSectionText() == "PAUSE") {
							System.out.println("-----pause------");
							sWaiter.pause();
						}
						if(newItem.getSectionText() == "allSectionsBuilt") {
							allSectionsBuild = true;
						}
						if(newItem.getSectionText() == "VoiceEnd") {
							if(allSectionsBuild && sWaiter.getReadQueue().isCurrentFinal()) {
								thisContinue = false;
								setContinueReading(false);
							}
							sWaiter.speakingDone();
						}
						
					}else {
						sWaiter.newSectionReady(newItem);
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
			System.out.println("end of loop______________");
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
		sWaiter.pause();
		readQueue.stepForward();
		sWaiter.isPaused = false;
		sWaiter.read();
	}
	private void stepBack() {
		sWaiter.pause();
		readQueue.stepBack();
		sWaiter.isPaused = false;
		sWaiter.read();
	}
}
