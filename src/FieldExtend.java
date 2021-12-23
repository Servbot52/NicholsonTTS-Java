import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.plaf.synth.ColorType;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Highlighter.Highlight;
import javax.swing.text.Position;
import javax.swing.text.Utilities;
import javax.swing.text.View;


import javax.swing.text.AttributeSet.ColorAttribute;
import javax.swing.text.BadLocationException;
import javax.swing.JEditorPane;

import java.awt.*;
import java.awt.geom.Rectangle2D;



public class FieldExtend{
	private JScrollPane scroll;
	private JTextPane area;
	private Object readingHighlight;
	
	void highlight(int start, int length) {
		try {
			readingHighlight = area.getHighlighter().addHighlight(start, start + length, new DefaultHighlightPainter(Color.yellow));
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void removeHighLight() {
		area.getHighlighter().removeHighlight(readingHighlight);
	}
	
	void makeItVisable(int start, int length) {
		try {
			Rectangle2D rect = area.modelToView2D(start).getBounds2D();
			
			
			if (rect != null) {
				Rectangle2D visibleRect = scroll.getVisibleRect().getBounds2D();
				if(visibleRect.contains(rect) == false)
					scroll.scrollRectToVisible( (Rectangle) rect);
	        }
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			System.out.println("FieldExtend.makeItVisable");
			e1.printStackTrace();
		}
		
		/*
		try {
			if( Utilities.getRowStart(area, start) == -1 || Utilities.getRowStart(area, start + length) == -1) {
				System.out.println("makeItVisable can't see part.");
			}
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			System.out.println("FieldExtend.makeItVisable");
			e.printStackTrace();
		}
		*/
	}
	/*
	void getLocation(int theCharacter) {
		View rootView = area.getUI().getRootView(area);
		rootView.ge
		area.getUI().getRootView(area).getv;
		
		int index;
	    while ((index = rootView.getViewIndex(theCharacter, Position.Bias.Backward)) >= 0) {
	    	rootView = rootView.getView(index);
	    }
		
	}
	*/
	void rowHeights() {
		area.getRootPane();
	}
}
