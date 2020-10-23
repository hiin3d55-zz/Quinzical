package model;

import javafx.beans.property.SimpleStringProperty;

public class User {
	private SimpleStringProperty _userName;
	private SimpleStringProperty _userId;
	private SimpleStringProperty _score;
	
	public User (String userName, String userId, String score) {
		_userName = new SimpleStringProperty(userName);
		_userId = new SimpleStringProperty(userId);
		_score = new SimpleStringProperty(score);
	}

	public String getUserName() {
		return _userName.get();
	}
	
	public String getUserId() {
		return _userId.get();
	}
	
	public String getScore() {
		return _score.get();
	}
	

	
}
