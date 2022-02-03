
import java.util.concurrent.BlockingQueue;

public class FullText  extends Thread{
    private String fullText;
    int getFullLength() { return fullText.length();}
    private int fullStart;
    
    private int startLocation = 0;
    public int getStartLoc() {return startLocation + fullStart;}
    
    private int endLocation = 0;
    public int getEndLoc() {return startLocation + fullStart;}
    
    private boolean isLastChar(int indexPoint){
        return (fullText.length() - 1 == indexPoint);
    }
    
    boolean reachedEnd() {
    	if(startLocation >= fullText.length()) {
    		return true;
    	}
    	return false;
    }
    
    private static char[] endMarks = {'.', '!', '?', '\n', '\t'};
    
    
    FullText(String theText, int sLocation, long timeStamp, BlockingQueue<Section> sectionBlockQueue){
    	fullStart = sLocation;
        fullText = theText;
        this.timeStamp = timeStamp;
        this.sectionBlockQueue = sectionBlockQueue;
    }
    private BlockingQueue<Section> sectionBlockQueue;
    
    private long timeStamp;
    private long timeStamp() { return timeStamp; }
    
    public void run() {
    	while(reachedEnd() == false) {
    		Section nextSection = getNewSection();
    		//add pronunciation here
    		
    		try {
				sectionBlockQueue.put(nextSection);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	//tell the SpeechWaiter that it is finished.
    	SectionInstruction fullyBuilt = new SectionInstruction("allSectionsBuilt", timeStamp());
    	try {
			sectionBlockQueue.put(fullyBuilt);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    private Section getNewSection(){
    	//if(endLocation == startLocation) endLocation++;
    	
        startLocation = endLocation;
        cleanWhiteSpace();
        
        endLocation = findNextPoint(startLocation) + 1;
        
        return new Section(startLocation + fullStart, endLocation + fullStart, fullText.substring(startLocation, endLocation), timeStamp());
    }
    
    private void cleanWhiteSpace(){
        if(fullText.length() == startLocation)
            return;
        
        if(Character.isWhitespace(fullText.charAt(startLocation))){
            startLocation++;
            cleanWhiteSpace();
        }
    }
    
    private int findNextPoint(int placeToStart){
        if(isLastChar(placeToStart)) return placeToStart;
        
        int nextEndCharIndex = -1;
        for(char endChar : endMarks){
            int charIndex = fullText.indexOf(endChar, placeToStart);
            if(charIndex > -1){
                if(nextEndCharIndex == -1 || nextEndCharIndex > charIndex){
                    nextEndCharIndex = charIndex;
                }
            }
        }
        
        if(nextEndCharIndex == -1)
            return fullText.length() - 1;
        
        //need to check for abbreviation, & other oddities
        
        
        //check for quote
        if(fullText.charAt(nextEndCharIndex) != '\n' && checkForQuotes(nextEndCharIndex))
            nextEndCharIndex += 1;
        return nextEndCharIndex;
    }
    
    private static char[] quoteChars = {'\"', '\''};
    private boolean checkForQuotes(int nextEndChar){
        if(isLastChar(nextEndChar))
            return false;
        
        //need to add other " marks
        for(char quoteChar : quoteChars){
            if(fullText.charAt(nextEndChar + 1) == quoteChar)
                return true;
        }
        return false;
    }
    
}
