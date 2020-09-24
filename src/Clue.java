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
