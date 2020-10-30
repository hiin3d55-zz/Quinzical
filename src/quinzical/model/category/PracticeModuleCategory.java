package quinzical.model.category;

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
		return getAllCategories();
	}

}
