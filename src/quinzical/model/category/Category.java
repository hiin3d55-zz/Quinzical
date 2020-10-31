package quinzical.model.category;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents a set of functionalities required to manipulate the categories in the question bank.
 * @author Sherman
 *
 */
public abstract class Category {
	

	public enum Categories {
		/**
		 * Represents an International category.
		 */
		INTERNATIONAL("International");

		private String _name;		

		private Categories(String name) {
			_name = name;
		}
		
		public String getName() {
			return _name;
		}
	}
	
	protected File _dataRecordFolder;

	public Category() {
		_dataRecordFolder = new File("data/categories");
	}
	
	/**
	 * Retrieve all categories available in the question bank database.
	 * @return
	 */
	public String[] getAllCategories() {
		File file = new File("questionBank");
		String[] categories = file.list();
		List<String> categoriesList = new ArrayList<String>(Arrays.asList(categories));
		for (int i = 0; i < categoriesList.size(); i++) {
			if (categoriesList.get(i).equals(Categories.INTERNATIONAL.getName())) {
				categoriesList.remove(i);
			}
		}
		return categoriesList.toArray(new String[categoriesList.size()]);
	}
	
	/**
	 * Gives a list of categories depending if it is for Games Module or Practice Module.
	 * @return An array of categories.
	 */
	public abstract String[] getCategories();
	
	/**
	 * Store the category into the database. This method must only be called for Games Module.
	 */
	public void recordCategories(String[] categories) {}
	
	/**
	 * Remove all categories stored in the database.
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
