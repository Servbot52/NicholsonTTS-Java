
public class Launcher {
	public static void main(String[] args) {
		TextWindow window = new TextWindow();
		//move following to launcher?
		PaneScroll paneScroll = new PaneScroll(window.scroll, window.mainField);
		SpeechWaiter speechWaiter = new SpeechWaiter(window.buttonPlay, paneScroll);

		//speechWaiter.playButton = buttonPlay;
				
		PlayListener pListener = new PlayListener(speechWaiter, window.buttonPlay, window.mainField);
		window.buttonPlay.addActionListener(pListener);
	}
}
