package view;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public abstract class SolutionScreen {
	protected BorderPane _pane;
	protected String _solution;
	protected Button _returnBtn;
	protected VBox _centerBox;
	
	public SolutionScreen(BorderPane pane, String solution, Button returnBtn) {
		_pane = pane;
		_solution = solution;
		
		_returnBtn = returnBtn;
		_returnBtn.getStyleClass().add("golden-button");
		
		_centerBox = new VBox();
		_centerBox.getStyleClass().add("center-screen-box");
	}
	
	public abstract void displayCorrect();
	
	public abstract void displayIncorrect();
	
	public void displayDontKnow() {}
		
	/**
	 * This method uses bash commands to use festival to speak any sentences.
	 * @param speech A string which will be spoken out using festival.
	 */
	protected void speak(String speech) {
		String cmd = "echo \""+ speech + "\" | festival --tts";	
//		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);
//		try {
//			Process process = builder.start();
//			process.toString();
//		}
//		catch (IOException e) {
//			System.out.println("Error with using festival to read out the question.");
//			e.printStackTrace();
//		}
	}	
}
