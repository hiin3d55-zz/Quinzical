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
	
	public void addUser(String userName, String userId, String score) {
		_users.add(new User(userName, userId, score));
		try {
			FileWriter fw = new FileWriter(_usersData, true);
			fw.write(userName + "|" + userId + "|" + score + "\n");
			fw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		_users = new ArrayList<User>();
		retrieveUsers();
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
