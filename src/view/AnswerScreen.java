package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public abstract class AnswerScreen {
	protected BorderPane _pane;
	protected Button _submitBtn;
	protected Button _repeatBtn;
	protected TextField _attemptInput;
	protected VBox _centerBox;
	
	protected String _clue;
	
	public AnswerScreen(BorderPane pane, String clue) {
		_pane = pane;
		_centerBox = new VBox();
		_centerBox.getStyleClass().add("center-screen-box");
		
		_attemptInput = new TextField();

		_submitBtn = new Button("Submit");
		_submitBtn.getStyleClass().add("golden-button");
		
		_repeatBtn = new Button("Repeat Clue");
		_repeatBtn.getStyleClass().add("golden-button");
		
		_clue = clue;
	}
	
	/**
	 * Displays the GUI for respective answer screen and also speaks the clue after.
	 */
	public void display() {
		createGUI();
		handleEvents();
		speak(_clue);
	}
	
	/**
	 * Creates the GUI required for the screen
	 */
	protected abstract void createGUI();
	
	/**
	 * Add a listener to the repeat button which speaks the clue again
	 */
	protected void handleEvents() {
		_repeatBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				speak(_clue);
			}
		});
	}
	
	/**
	 * This method uses bash commands to use festival to speak any sentences.
	 * @param speech A string which will be spoken out using festival.
	 */
	protected void speak(String speech) {
		
		// Bash command for speaking out the clue.
		String cmd = "echo \"" + speech + "\" | festival --tts";
//		
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
