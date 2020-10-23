package view;

import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.User;
import model.Users;

public class LeaderBoardScreen {
	private BorderPane _pane;
	private VBox _leaderBoardBox;
	
	private TableView<User> _table;
	
	public LeaderBoardScreen(BorderPane pane) {
		_pane = pane;
		_leaderBoardBox = new VBox();
		_leaderBoardBox.getStyleClass().add("center-screen-box");
		
		_table = new TableView<User>();
		initializeTable();
	}
	
	public void display() {
		Text header = new Text("LeaderBoard!");
		header.getStyleClass().addAll("header-msg");
		
		_leaderBoardBox.getChildren().addAll(header, _table);
		
		_pane.setCenter(_leaderBoardBox);
		_pane.getBottom().getStyleClass().removeAll("invisible-component");
	}
	
	private void initializeTable() {
		
		TableColumn<User, String> userNameColumn = new TableColumn<>("User Name");
		userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
		
		TableColumn<User, String> userIdColumn = new TableColumn<>("User ID");
		userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
		
		TableColumn<User, String> scoreColumn = new TableColumn<>("Score");
		scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
		
		_table.setItems(getUsers());
		_table.getColumns().addAll(Arrays.asList(userNameColumn, userIdColumn, scoreColumn));
		_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}
	
	private ObservableList<User> getUsers() {
		ObservableList<User> usersList = FXCollections.observableArrayList();
		
		Users users = new Users();
		usersList.addAll(users.getUsers());
		
		return usersList;
		
	}
}
