package view;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LeaderBoardScreen {
	private BorderPane _pane;
	private VBox _leaderBoardBox;
	
	public LeaderBoardScreen(BorderPane pane) {
		_pane = pane;
		_leaderBoardBox = new VBox();
		_leaderBoardBox.getStyleClass().add("center-screen-box");
	}
	
	public void display() {
		Text header = new Text("LeaderBoard!");
		header.getStyleClass().addAll("header-msg");
		
		_leaderBoardBox.getChildren().addAll(header);
		
		
		_pane.setCenter(_leaderBoardBox);
		_pane.getBottom().getStyleClass().removeAll("invisible-component");
	}
}
