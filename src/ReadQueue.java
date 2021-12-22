public class ReadQueue {
	ReadQueue(SpeechWaiter sWaiter){
		speechWaiter = sWaiter;
	}
	SpeechWaiter speechWaiter;
	
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
		currentElement = currentElement.getPreviousSection();
		if(speechWaiter.isSpeaking) {
			speechWaiter.pause();
			speechWaiter.read();
		}
	}

	void stepForward() {
		currentElement = currentElement.getNextSection();
		if(speechWaiter.isSpeaking) {
			speechWaiter.pause();
			speechWaiter.read();
		}
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
		fullText = new FullText(selectedText, selectedStart, this);
		fullText.start();
	}
}
