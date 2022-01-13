import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

import java.awt.*;
import java.awt.geom.Rectangle2D;



public class PaneScroll{
	private JScrollPane scroll;
	private JTextPane area;
	private Object readingHighlight;
	
	PaneScroll(JScrollPane scroll, JTextPane pane){
		area = pane;
		this.scroll = scroll;
	}
	
	void readingNewArea(int start, int length) {
		highlight(start, length);
		makeItVisable(start, length);
	}
	void finishReadingArea() {
		removeHighLight();
	}
	
	
	private void highlight(int start, int length) {
		System.out.println("highlight:" + start + " for " + length);
		try {
			DefaultHighlighter.DefaultHighlightPainter highlightPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
			readingHighlight = area.getHighlighter().addHighlight(start, start + length, highlightPainter);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void removeHighLight() {
		System.out.println("remove highlight");
		if(readingHighlight != null)
			area.getHighlighter().removeHighlight(readingHighlight);
	}
	
	private void makeItVisable(int start, int length) {
		if(length == 0)
			return;
		try {
			Rectangle2D rect = area.modelToView2D(start);
			rect.add(area.modelToView2D(start + length));
			rect = rect.getBounds2D();
			
			
			int scrollMin = scroll.getVerticalScrollBar().getValue();
			int scrollMax = scroll.getVerticalScrollBar().getHeight() + scrollMin;
			
			int minY = (int) rect.getMinY();
			int maxY = (int) rect.getMaxY();
			
			if(!(minY < scrollMax && minY > scrollMin) || !(maxY < scrollMax && maxY > scrollMin)) {
				if(minY > scroll.getVerticalScrollBar().getMaximum())
					minY = scroll.getVerticalScrollBar().getMaximum();
				
				scroll.getVerticalScrollBar().setValue(minY);
			}
			
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			System.out.println("FieldExtend.makeItVisable");
			e1.printStackTrace();
		}
		

	}
	
}
