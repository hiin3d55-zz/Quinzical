package model;

import java.io.File;
import java.io.IOException;

/**
 * This class represents functionalities that retrieves categories for Games Module usage.
 * @author Sherman
 *
 */
public class GamesModuleCategory extends Category{
	
	/**
	 * Gets the categories that are already stored in data. If the data/category folder does not exist,
	 * then create the folder and store 5 categories into it.
	 */
	public String[] getCategories() {
		if (_dataRecordFolder.exists()) {
			return _dataRecordFolder.list();
		} else {
			_dataRecordFolder.mkdirs();
			File file = new File("questionBank");
			String[] categories = randomiseCategories(file.list());
			
			try {
				recordCategories(categories);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return categories;
		}
	}
	
	/**
	 * Create a file for each category in data/category folder.
	 * @param categories
	 * @throws IOException
	 */
	private void recordCategories(String[] categories) throws IOException {
		for (int i = 0; i < categories.length; i++) {
			File file = new File(_dataRecordFolder.getPath() + "/" + categories[i]);
			file.createNewFile();
		}
	}
}
