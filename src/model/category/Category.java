package model.category;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents the functionalities required to obtain categories from question bank.
 * @author Sherman
 *
 */
public abstract class Category {
	
	protected File _dataRecordFolder;

	public Category() {
		_dataRecordFolder = new File("data/categories");
	}

	public String[] getAllCategories() {
		File file = new File("questionBank");
		String[] categories = file.list();
		List<String> categoriesList = new ArrayList<String>(Arrays.asList(categories));
		for (int i = 0; i < categoriesList.size(); i++) {
			if (categoriesList.get(i).equals("International")) {
				categoriesList.remove(i);
			}
		}
		return categoriesList.toArray(new String[categoriesList.size()]);
	}
	
	/**
	 * Gives a list of categories depending if it is for Games Module or Practice Module.
	 * @return
	 */
	public abstract String[] getCategories();
	
	public void recordCategories(String[] categories) {}
	
	/**
	 * Removes all the category files in data/category
	 */
	public void removeCategoryData() {
		File[] allFiles = _dataRecordFolder.listFiles();

		if (allFiles != null) {
			for (File file: allFiles) {
				file.delete();
			}
		}
		_dataRecordFolder.delete();
	}
}
