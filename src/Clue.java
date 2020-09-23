import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Clue {
	private File _categoryRecordFolder;
	
	public Clue() {
		_categoryRecordFolder = new File("data/");
	}
		
	public String[] getClues(String category, boolean gameModule) {
		String[] clues = getAllClues(category);
		
		if (!gameModule) {
			return randomiseClues(clues, 1);
		}
		
		File file = new File(_categoryRecordFolder.getPath() + category);
		if (file.length() == 0) {
			recordClues(category);
		}
		
		clues = getRecordedClues(category);
		return clues;
	}
	
	private void recordClues(String category) {
		String[] clues = getAllClues(category);
		clues = randomiseClues(clues, 5);
		FileWriter fw;
		try {
			fw = new FileWriter(_categoryRecordFolder.getPath() + category, true);
			for (int j = 0; j < clues.length; j++) {
				fw.write(clues[j] + "\n");
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String[] getRecordedClues(String category) {
		File file = new File(_categoryRecordFolder.getPath() + category);
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
		return clues.toArray(new String[clues.size()]);
	}
	
	private String[] getAllClues(String category) {
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
	
	private String[] randomiseClues(String[] clues, int count) {
		Set<String> randomised = new HashSet<String>();
		
		while (randomised.size() < count) {
			randomised.add(clues[(int)(Math.random() * clues.length)]);
		}
		return randomised.toArray(new String[randomised.size()]);
	}
	
	private String[] formatClues(String[] clues) {
		String[] res = new String[clues.length];
		for (int i = 0; i < clues.length; i++) {
			String question = clues[i].split("[(]")[0];
			int pos = 0;
			while (!Character.isAlphabetic(question.charAt(question.length() - 1 - pos))) {
				pos++;
			}
			
			res[i] = question.substring(0, question.length() - pos);
		}
		return res;
	}
	
	public String getAnswer(String category, String clue) {
		File file = new File("questionBank/" + category);
		String line = null;
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNext()) {
				line = scanner.nextLine();
				if (line.matches(clue + ".*")) {
					line = line.split("[)]")[1];
					line = line.trim();
					break;
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return line;
	}
}
