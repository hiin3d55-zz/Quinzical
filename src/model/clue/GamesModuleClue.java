package model.clue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class GamesModuleClue extends Clue{
	
	/**
	 * Get all the clues remaining in a category. Generate clues for the category that has 0 clues in it.
	 * If file does not exit, then all clues have been attempted for that category.
	 * @param category The category that the clues are in.
	 * @return An array of clues. If returned null, all clues have been attempted.
	 */
	public String[] getClues(String category) {
		File file = new File(_dataRecordFolder.getPath() + "/" + category);
		if (!file.exists()) {
			return null;
		}
		if (file.length() == 0) {
			recordClues(category);
		}
		String[] clues = getRecordedClues(category);
		return clues;
	}
	/**
	 * Get clues from question bank and record them into respective category files in data/category directory
	 * @param category The category that the clues are in.
	 */
	private void recordClues(String category) {
		String[] clues = getAllClues(category);	
		clues = randomiseClues(clues, 5);
		FileWriter fw;
		try {
			fw = new FileWriter(_dataRecordFolder.getPath() + "/" + category, true);
			for (int j = 0; j < clues.length; j++) {
				fw.write(clues[j] + "\n");
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get clues that are already stored into a file in data/category folder
	 * @param category The category that the clues are in.
	 * @return An array of clues that is stored in the category file in data/category folder
	 */
	private String[] getRecordedClues(String category) {
		File file = new File(_dataRecordFolder.getPath() + "/" + category);
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

	/**
	 * Remove the clue in a category file in data/category folder.
	 * @param category The category that the clue is in .
	 * @param clue The clue to be removed.
	 */
	public void update(String category, String clue) {
		List<String> cluesList = new ArrayList<String>();
		Collections.addAll(cluesList, getClues(category));
		File file = new File(_dataRecordFolder + "/" + category);
		file.delete();
		try {
			file.createNewFile();
			FileWriter fw = new FileWriter(file);	
			int pos = 0;
			for (int i = 0; i < cluesList.size(); i++) {
				if (cluesList.get(i).equals(clue)) {
					pos = i;
				} else {
					fw.write(cluesList.get(i) + "\n");
				}
			}
			fw.close();
			cluesList.remove(pos);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (cluesList.size() == 0) {
			file.delete();
		}
	}
	
	/**
	 * This method determines the value of a clue in a category. The first clue is given 100, second clue
	 * is 200, and so forth.
	 * @param category The category that the values are needed for.
	 * @return An array of int where each int represents the value of a question.
	 */
	public int[] getClueValues(String category) {
		int clues = getRecordedClues(category).length;
		
		int[] values = new int[clues];
		for (int i = 5; i > (5 - clues); i--) {
			values[clues - (5 - i) - 1] = i * 100;
		}
		return values;
	}
}
