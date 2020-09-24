import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class GamesModuleClue extends Clue{
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

	public String getAnswer(String category, String clue) {
		String ans = super.getAnswer(category, clue);
		update(category, clue);
		return ans;
	}

	private void update(String category, String clue) {
		List<String> cluesList = new ArrayList<String>();
		Collections.addAll(cluesList, getClues(category));
		File file = new File(_dataRecordFolder + "/" + category);
		file.delete();
		try {
			file.createNewFile();
			FileWriter fw = new FileWriter(file);	
			int pos = 0;
			for (int i = 0; i < cluesList.size(); i++) {

				if (cluesList.get(i).matches(clue + ".*")) {
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
}
