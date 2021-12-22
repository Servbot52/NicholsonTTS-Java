import javax.swing.JScrollPane;
import javax.swing.plaf.synth.ColorType;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Position;
import javax.swing.text.View;


import javax.swing.text.AttributeSet.ColorAttribute;
import javax.swing.text.BadLocationException;
import javax.swing.JEditorPane;

import java.awt.*;



public class FieldExtend {
	private JScrollPane scroll;
	private JEditorPane area;
	
	
	void highlight(int start, int end) {
		try {
			area.getHighlighter().addHighlight(start, end, new DefaultHighlightPainter(Color.yellow));
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void removeHighLight() {
		area.getHighlighter().removeAllHighlights();
	}
	
	void getLocation(int theCharacter) {
		View rootView = area.getUI().getRootView(area);
		
		area.getUI().getRootView(area).getv;
		
		int index;
	    while ((index = rootView.getViewIndex(theCharacter, Position.Bias.Backward)) >= 0) {
	    	rootView = rootView.getView(index);
	    }
		
	}
}
