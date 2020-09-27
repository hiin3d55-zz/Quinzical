package model;

import java.io.File;

public class PracticeModuleCategory extends Category{
	public String[] getCategories() {
		File file = new File("questionBank");
		return file.list();
	}
}
