import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class SkipButtonListener implements ActionListener{
	private SpeechWaiter speechWaiter;
	private JButton backB;
	private JButton forwardB;
	SkipButtonListener(SpeechWaiter speechWaiter, JButton backB, JButton forwardB){
		this.speechWaiter = speechWaiter;
		this.backB = backB;
		this.forwardB = forwardB;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == backB) {
			speechWaiter.inputStepBack();
		}else if(e.getSource() == forwardB) {
			speechWaiter.inputStepForward();
		}
	}
	
}