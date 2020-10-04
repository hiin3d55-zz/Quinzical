package model;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

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

	public abstract String[] getCategories();
	
	/**
	 * This method randomly picks five categories from the given input.
	 * @param categories The categories to be randomly picked
	 * @return An array of randomised categories.
	 */
	protected String[] randomiseCategories(String[] categories) {
		Set<String> randomed = new HashSet<String>();

		while (randomed.size() < 5) {
			randomed.add(categories[(int)(Math.random()*categories.length)]);
		}
		return randomed.toArray(new String[randomed.size()]);
	}
	
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
