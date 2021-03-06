
public class Launcher {
	public static void main(String[] args) {
		Settings settings = new Settings();
		
		TextWindow window = new TextWindow(settings);
		//move following to launcher?
		PaneScroll paneScroll = new PaneScroll(window.scroll, window.mainField);
		SpeechWaiter speechWaiter = new SpeechWaiter(window.buttonPlay, paneScroll, settings);

		//speechWaiter.playButton = buttonPlay;
		SkipButtonListener skipListener = new SkipButtonListener(speechWaiter, window.buttonMoveBack, window.buttonMoveForword);
		window.buttonMoveBack.addActionListener(skipListener);
		window.buttonMoveForword.addActionListener(skipListener);
		
		
		PlayListener pListener = new PlayListener(speechWaiter, window.buttonPlay, window.mainField);
		window.buttonPlay.addActionListener(pListener);
		
		CaretListener caretListener = new CaretListener(speechWaiter);
		window.mainField.getCaret().addChangeListener(caretListener);
	}
}