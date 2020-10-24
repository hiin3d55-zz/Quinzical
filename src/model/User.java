package model;

import javafx.beans.property.SimpleStringProperty;

/**
 * Represents a User that finished the game.
 * @author Sherman
 *
 */
public class User implements Comparable<User> {
	private SimpleStringProperty _ranking;
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
	
	public String getRanking() {
		return _ranking.get();
	}
	
	public void setRanking(String ranking) {
		_ranking = new SimpleStringProperty(ranking);
	}
	
	/**
	 * Rank user's score by descending order.
	 */
	@Override
	public int compareTo(User o) {
		return -getScore().compareTo(o.getScore());
	}
	

	
}
