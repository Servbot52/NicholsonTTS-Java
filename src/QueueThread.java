import java.util.concurrent.BlockingQueue;

public class QueueThread extends Thread {
	QueueThread(long timeStamp, SpeechWaiter sWaiter){
		this.timeStamp = timeStamp;
		this.sWaiter = sWaiter;
	}
	
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
							setContinueReading(false);
						}
						if(newItem.getSectionText() == "RESUME") {
							sWaiter.read();
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
		if(timeStamp == sWaiter.getTimeStamp())
			sWaiter.needNewText = true;
		
		System.out.println(getContinueReading() + "freedom from this loop.");
	}
	
	private boolean thisContinue = true;
	private void setContinueReading(boolean bool) {
		thisContinue = bool;
		sWaiter.setContinueReading(bool);
	}
	private boolean getContinueReading() {return thisContinue && sWaiter.getContinueReading(); }
}
