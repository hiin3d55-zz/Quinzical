package quinzical.view.gamesModule;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import quinzical.view.AnswerScreen;
import quinzical.view.Question;
import quinzical.view.SolutionScreen;

/**
 * This class represents the screen that shows when the user tries to answer the question.
 * 
 * @author Dave Shin
 *
 */
public class GamesAnswerScreen extends AnswerScreen{
	
	private Button _mainMenuBtn;
	private Button _dontKnowBtn;
	private Question _question;
	
	private int _timeLimit;
	private Text _timerTime;
	
	private boolean _btnPressed;
	
	public GamesAnswerScreen(BorderPane pane, Question question) {
		super(pane, question.getClue());
		
		_mainMenuBtn = (Button)pane.getBottom();
		
		_dontKnowBtn = new Button("Don\'t know");
		_dontKnowBtn.getStyleClass().add("normal-button");
		
		_question = question;
		
		_timeLimit = 15; // Time limit for answering a question is 15 seconds.
		_timerTime = new Text(String.valueOf(_timeLimit));
		_timerTime.getStyleClass().add("header-msg");
		
		_btnPressed = false; // If a button is pressed, the timer will be stopped using this 
							 // boolean variable.
	}
	
	/**
	 * Starts the timer which counts down from the specified time limit. 
	 */
	public void startTimer() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				if (_timeLimit == 0) {
					timer.cancel();
					_timeLimit = 15;
					if (!_btnPressed) {
						TimerTaskCompletedPaper paper = new TimerTaskCompletedPaper(_dontKnowBtn); 
						Platform.runLater(paper);
					}
				} else {
					_timeLimit--;
				}
				_timerTime.setText(String.valueOf(_timeLimit));
			}
		}, 1000, 1000);
	}
	
	/**
	 * Create GUI components for game answer screen.
	 */
	protected void createGUI() {			
		HBox inputAndSoundBtn = new HBox();
		inputAndSoundBtn.getStyleClass().add("center-screen-box");
		
		Text instruction = new Text("Listen to the clue then answer the question.");
		instruction.getStyleClass().add("information-text");
		
		Text multipleAnsInstruction = new Text("For clues that have multiple answers, separate them using \",\"");
		multipleAnsInstruction.getStyleClass().add("normal-text");
		
		inputAndSoundBtn.getChildren().addAll(_attemptInput, _repeatBtn);
		
		HBox buttonBox = new HBox();
		buttonBox.getStyleClass().add("center-screen-box");
		buttonBox.getChildren().addAll(_submitBtn, _dontKnowBtn);
		

		_centerBox.getChildren().addAll(_timerTime, instruction, inputAndSoundBtn, multipleAnsInstruction, buttonBox);
		_pane.setCenter(_centerBox);
	}
	
	/**
	 * Adds listeners to different buttons.
	 */
	protected void handleEvents() {
		super.handleEvents();
		_submitBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {				
				checkAnswerAndDisplayNext();
			}
		});
		
		_attemptInput.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					checkAnswerAndDisplayNext();
				}
			}
		});
		
		_dontKnowBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				_btnPressed = true;
				SolutionScreen solScrn = new GamesSolutionScreen(_pane, _question,
						_question.getSolution()[0]);
				solScrn.displayDontKnow();
			}
		});
		
		_mainMenuBtn.setOnMouseClicked(event -> {
			_btnPressed = true; // Make sure that the timer is stopped when the user exits to the
								// main menu.
		});
	}
	
	/**
	 * This method checks if user has input the correct answer, then display the next screen
	 * depending on what the user's input is.
	 */
	private void checkAnswerAndDisplayNext() {
		_btnPressed = true;
		
		boolean loseScore = true;
		
		String attempt = _attemptInput.getText();
		
		// Make it case-insensitive and trim all leading and trailing spaces.
		attempt = attempt.toLowerCase();
		attempt = attempt.trim();
		
		SolutionScreen solScrn = new GamesSolutionScreen(_pane, _question, 
				_question.getSolution()[0]);
		
		// If the attempt is an empty string, it gets treated as the same way when the Don't Know 
		// button is pressed.
		if (attempt.equals("")) {
			solScrn.displayDontKnow();
			loseScore= false;
		}
		
		// A for loop is used because there can be multiple solutions and we want to 
		// check if the attempt matches with at least one solution.
		for (String solution : _question.getSolution()) {
			solution = solution.toLowerCase();
			solution = solution.trim();
			
			//For clues that have multiple answers separated by ","
			if (solution.contains(",")) {
				String[] solutions = solution.split(",");
				String[] attemptAns = attempt.split(",");
				if (solutions.length == attemptAns.length) {
					int count = 0;
					for (int i = 0; i < solutions.length; i++) {
						if (solutions[i].trim().equals(attemptAns[i].trim())) {
							count++;
						}
					}
					if (count == solutions.length) {
						loseScore = false;
						solScrn.displayCorrect();
					}
				}
			} else if (attempt.equals(solution)) {
				solScrn.displayCorrect();
				loseScore = false;
			}
		}
		
		if (loseScore) {
			solScrn.displayIncorrect();
		}	
	}
}
