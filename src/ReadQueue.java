import java.util.concurrent.BlockingQueue;

public class ReadQueue {
	ReadQueue(SpeechWaiter sWaiter){
		speechWaiter = sWaiter;
	}
	
	SpeechWaiter speechWaiter;
	private long timeStamp() { return speechWaiter.getTimeStamp(); }
	private BlockingQueue<Section> blockQueue(){ return speechWaiter.sectionBlockQueue; }
	
	private Section firstElement;
	private Section finalElement;
	boolean isCurrentFinal() { return finalElement == currentElement; }
	
	void addSection(Section newSection) {
		if(firstElement == null) {
			firstElement = newSection;
			finalElement = newSection;
			currentElement = firstElement;
		}else {
			finalElement.setNextSection(newSection);
			newSection.setPreviousSection(finalElement);
			finalElement = newSection;
		}
	}
	
	Section currentElement;
	
	void stepBack() {
		if(currentElement.getPreviousSection() != null)
			currentElement = currentElement.getPreviousSection();
	}

	void stepForward() {
		if(currentElement.getNextSection() != null)
			currentElement = currentElement.getNextSection();
	}
	
	
	FullText fullText;
		
	int getSectionCount() {
		if(firstElement == null) {
			return 0;
		}
		Section aSection = firstElement;
		int count = 1;
		while(aSection.getNextSection() != null){
			aSection = aSection.getNextSection();
			count++;
		}
		return count;
	}
	boolean isFullTextConverted() {
		if(firstElement == null) {
			return false;
		}
		Section aSection = firstElement;
		int charCount = 0;
		do {
			charCount= charCount + aSection.getSectionText().length();
		}while(aSection.getNextSection() != null);

		if(charCount == fullText.getFullLength()){
			return true;
		}
		return false;
	}
	
	void startBuildingSections(String selectedText, int selectedStart) {		
		firstElement = null;
		finalElement = null;
		currentElement = null;
		fullText = new FullText(selectedText, selectedStart, timeStamp(), blockQueue());
		fullText.start();
	}
}
