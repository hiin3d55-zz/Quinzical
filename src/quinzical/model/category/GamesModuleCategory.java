package quinzical.model.category;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents a set of functionalities to manipulate the categories in Games Module.
 * Module usage.
 * 
 * @author Sherman
 *
 */
public class GamesModuleCategory extends Category {

	/**
	 * Gets the categories that are already stored in data. If the data/category folder does not exist,
	 * then return all categories.
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
			return getAllCategories();
		}
	}
	
	
	
	/**
	 * Store the category into the database. It creates a file for each category in data/category folder.
	 * 
	 * @param categories the name of the files to be created
	 */
	public void recordCategories(String[] categories){
		_dataRecordFolder.mkdirs();
		
		//Also add International clue to the database
		List<String> categoriesList = new ArrayList<>(Arrays.asList(categories));
		categoriesList.add("International");
		categories = categoriesList.toArray(categories);
		
		
		for (int i = 0; i < categories.length; i++) {
			File file = new File(_dataRecordFolder.getPath() + "/" + categories[i]);
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
