package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
	
	public List<User> getUsers() {
		return _users;
	}
	
	private void retrieveUsers() {
		Scanner scanner;
		try {
			scanner = new Scanner(_usersData);
			
			while (scanner.hasNext()) {
				String[] userDetail = formatUserDetail(scanner.nextLine());
				System.out.println(userDetail[0]);
				System.out.println(userDetail[1]);
				System.out.println(userDetail[2]);
				User user = new User(userDetail[0], userDetail[1], userDetail[2]);
				_users.add(user);
			}
			
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		

	}
	
	private String[] formatUserDetail(String userDetail) {
		String[] formattedUserDetail = userDetail.split("[|]");
		return formattedUserDetail;
	}
}
