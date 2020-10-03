package model;
import java.io.File;

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
		return file.list();
	}
}
