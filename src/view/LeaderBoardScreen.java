package view;

import java.util.Arrays;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.User;
import model.Users;

public class LeaderBoardScreen {
	private BorderPane _pane;
	private VBox _leaderBoardBox;

	private TableView<User> _table;
	private ScrollBar _scrollBar;
	
	public LeaderBoardScreen(BorderPane pane) {
		_pane = pane;
		_leaderBoardBox = new VBox();
		_leaderBoardBox.getStyleClass().add("center-screen-box");

		_table = new TableView<User>();
		_table.setMaxWidth(650);
		_table.setMinWidth(650);
		_table.setDisable(true);
		initializeTable();
		
		_scrollBar = new ScrollBar();
		_scrollBar.setId("leader-board-scroll");
		
		_scrollBar.setOrientation(Orientation.VERTICAL);
		_scrollBar.setMax(10);
		_scrollBar.setMin(0);
		_scrollBar.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> value, Number n1, Number n2) {
				_table.scrollTo(n2.intValue());
			}

		});	
	}

	public void display() {
		Text header = new Text("LeaderBoard!");
		header.getStyleClass().addAll("header-msg");
		
		HBox tableAndScroll = new HBox();
		tableAndScroll.getStyleClass().add("center-screen-box");
		tableAndScroll.getChildren().addAll(_table, _scrollBar);
		
		_leaderBoardBox.getChildren().addAll(header, tableAndScroll);
		
		_pane.setCenter(_leaderBoardBox);
		_pane.getBottom().getStyleClass().removeAll("invisible-component");
	}

	private void initializeTable() {
		TableColumn<User, String> rankingColumn = new TableColumn<>("Ranking");
		rankingColumn.setCellValueFactory(new PropertyValueFactory<>("ranking"));
		
		TableColumn<User, String> userNameColumn = new TableColumn<>("User Name");
		userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));

		TableColumn<User, String> userIdColumn = new TableColumn<>("User ID");
		userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

		TableColumn<User, String> scoreColumn = new TableColumn<>("Score");
		scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

		
		_table.setItems(getUsers());
		_table.getColumns().addAll(Arrays.asList(rankingColumn, userNameColumn, userIdColumn, scoreColumn));
		_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	private ObservableList<User> getUsers() {
		ObservableList<User> usersList = FXCollections.observableArrayList();

		Users users = new Users();
		usersList.addAll(users.getUsers());

		return usersList;

	}
}
