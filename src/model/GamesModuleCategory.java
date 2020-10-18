package model;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents functionalities that retrieves categories for Games
 * Module usage.
 * 
 * @author Sherman
 *
 */
public class GamesModuleCategory extends Category {

	/**
	 * Gets the categories that are already stored in data. If the data/category folder does not exist,
	 * then create the folder and store 6 categories into it, including International category.
	 */
	public String[] getCategories() {
		if (_dataRecordFolder.exists()) {
			String[] categories = _dataRecordFolder.list();
			for (int i = 0; i < categories.length; i++) {
				
				//The International category is to be placed last.
				if (categories[i].equals("International")) {
					categories[i] = categories[categories.length - 1];
					categories[categories.length - 1] = "International";
					break;
				}
			}
			return categories;
			
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
	 * This method randomly picks six categories from the given input, including International category.
	 * @param categories The categories to be randomly picked
	 * @return An array of randomised categories.
	 */
	private String[] randomiseCategories(String[] categories) {
		Set<String> randomed = new HashSet<String>();

		while (randomed.size() < 6) {
			randomed.add(categories[(int)(Math.random()*categories.length)]);
		}
		
		String[] randomedArray = randomed.toArray(new String[randomed.size()]);
		
		if (!randomed.contains("International")) {
			randomedArray[randomedArray.length - 1] = "International";
		}
		
		return randomedArray;
	}
	
	/**
	 * Create a file for each category in data/category folder.
	 * 
	 * @param categories the name of the files to be created
	 * @throws IOException
	 */
	private void recordCategories(String[] categories) throws IOException {
		for (int i = 0; i < categories.length; i++) {
			File file = new File(_dataRecordFolder.getPath() + "/" + categories[i]);
			file.createNewFile();
		}
	}
}
