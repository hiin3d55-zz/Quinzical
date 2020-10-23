package view;

import java.util.Arrays;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
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
		_table.setMaxWidth(650);
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
		userNameColumn.setSortable(false);

		TableColumn<User, String> userIdColumn = new TableColumn<>("User ID");
		userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
		userIdColumn.setSortable(false);

		
		TableColumn<User, String> scoreColumn = new TableColumn<>("Score");
		scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
		scoreColumn.setSortable(false);
		
		TableColumn<User, Integer> indexColumn = new TableColumn<>("Ranking");
		indexColumn.setCellFactory(col -> {
			TableCell<User, Integer> indexCell = new TableCell<>();
			ReadOnlyObjectProperty<TableRow<User>> rowProperty = indexCell.tableRowProperty();
			ObjectBinding<String> rowBinding = Bindings.createObjectBinding(() -> {
				TableRow<User> row = rowProperty.get();
				if (row != null) {
					int rowIndex = row.getIndex();
					if (rowIndex < row.getTableView().getItems().size()) {
						return Integer.toString(rowIndex + 1);
					}
				}
				return null;
			}, rowProperty);
			indexCell.textProperty().bind(rowBinding);
			return indexCell;
		});
		indexColumn.setSortable(false);

		
		_table.setItems(getUsers());
		_table.getColumns().addAll(Arrays.asList(indexColumn, userNameColumn, userIdColumn, scoreColumn));
		_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);		
	}

	private ObservableList<User> getUsers() {
		ObservableList<User> usersList = FXCollections.observableArrayList();

		Users users = new Users();
		usersList.addAll(users.getUsers());

		return usersList;

	}
}
