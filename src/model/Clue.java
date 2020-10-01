package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public abstract class Clue {
	protected File _dataRecordFolder;
	
	public Clue() {
		_dataRecordFolder = new File("data/categories");
	}
		
	public abstract String[] getClues(String category);
	
	protected String[] getAllClues(String category) {
		File file = new File("questionBank/" + category);
		List<String> clues = new ArrayList<String>();
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNext()) {
				clues.add(scanner.nextLine());
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String[] cluesArray = clues.toArray(new String[clues.size()]);
		return formatClues(cluesArray);
	}
	
	protected String[] randomiseClues(String[] clues, int count) {
		Set<String> randomised = new HashSet<String>();
		
		while (randomised.size() < count) {
			randomised.add(clues[(int)(Math.random() * clues.length)]);
		}
		return randomised.toArray(new String[randomised.size()]);
	}
	
	protected String[] formatClues(String[] clues) {
		String[] res = new String[clues.length];
		for (int i = 0; i < clues.length; i++) {
			res[i] = clues[i].split("[|]")[0];
		}
		return res;
	}
	
	public String[] getAnswer(String category, String clue) {
		File file = new File("questionBank/" + category);
		String ans = null;
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNext()) {
				ans = scanner.nextLine();
				if (ans.matches(clue + ".*")) {
					ans = ans.split("[|]")[2];
					break;
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return ans.split("/");
	}
	
	public void update(String category, String clue) {}
	
	public abstract int[] getClueValues(String category);
}
