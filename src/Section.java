
public class Section {
	private long timeStamp;
	long getTimeStamp() { return timeStamp; }
	
	
	protected int startPoint;
	int getStart(){return startPoint;}
	
	protected int endPoint;
	int getEnd() {return endPoint;}
	
	protected String sectionText;
	String getSectionText() {return sectionText;}
	
	protected String readText;
	String getReadText() {
		if (readText == null)
			return sectionText;
		return readText;
	}
	String theText() {
		try {
			return readText;
		} catch (Exception e) {
			return sectionText;
		}
	}
	
	Section(int start, int end, String text, long timeStamp){
		this.timeStamp = timeStamp;
		startPoint = start;
		endPoint = end;
		sectionText = text;
	}
	
	private Section previousSection;
	public void setPreviousSection(Section lSection) {
		if(previousSection == null) {
			previousSection = lSection;
		}
	}
	public Section getPreviousSection() {
		if(previousSection == null)
			return this;
		return previousSection;
		
	} 
	
	private Section nextSection;
	public void setNextSection(Section nSection) {
		nextSection = nSection;
	}
	public Section getNextSection() {
		return nextSection;
	}
}

class SectionInstruction extends Section{

	SectionInstruction(int start, int end, String text, long timeStamp) {
		super(-1, -1, text, timeStamp);
		// TODO Auto-generated constructor stub
	}
	SectionInstruction(String instruction, long timeStamp){
		super(-1, -1, instruction, timeStamp);
	}
	
}
