package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Users {
	private List<User> _users;
	private File _usersData;
	
	public Users() {
		_users = new ArrayList<User>();
		
		_usersData = new File("data/users");
		if (!_usersData.exists()) {
			File dir = new File("data");
			dir.mkdir();
			try {
				_usersData.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			retrieveUsers();
		}
	}
	
	public void addUser(User user) {
		try {
			FileWriter fw = new FileWriter(_usersData, true);
			fw.write(user.getUserName() + "|" + user.getUserId() + "|" + user.getScore() + "\n");
			fw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Updates the list by re-reading database
		_users = new ArrayList<User>();
		retrieveUsers();
	}
	
	public String getRanking(User user) {
		for (int i = 0; i < _users.size(); i++) {
			if (_users.get(i).getUserId().equals(user.getUserId())) {
				return Integer.toString(i + 1);
			}
		}
		return null;
	}
	
	public List<User> getUsers() {
		return _users;
	}
	
	public boolean userIdExists(String userId) {
		for (User u: _users) {
			if (u.getUserId().equals(userId)) {
				return true;
			}
		}
		return false;
	}
	
	private void retrieveUsers() {
		Scanner scanner;
		try {
			scanner = new Scanner(_usersData);
			
			while (scanner.hasNext()) {
				String[] userDetail = formatUserDetail(scanner.nextLine());
				User user = new User(userDetail[0], userDetail[1], userDetail[2]);
				_users.add(user);
			}
			
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//Sort by users' score
		Collections.sort(_users);

	}
	
	private String[] formatUserDetail(String userDetail) {
		String[] formattedUserDetail = userDetail.split("[|]");
		return formattedUserDetail;
	}
}
