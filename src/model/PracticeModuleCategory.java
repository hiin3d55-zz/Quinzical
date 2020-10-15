package model;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents functionalities that retrieves categories for Practice Module.
 * @author se2062020
 *
 */
public class PracticeModuleCategory extends Category{
	
	/**
	 * Gets all the categories available in the question bank.
	 */
	public String[] getCategories() {
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
}
