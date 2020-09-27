
import java.io.File;
import java.util.HashSet;
import java.util.Set;

public abstract class Category {
	protected File _dataRecordFolder;

	public Category() {
		_dataRecordFolder = new File("data/categories");
	}

	public abstract String[] getCategories();

	protected String[] randomiseCategories(String[] categories) {
		Set<String> randomed = new HashSet<String>();

		while (randomed.size() < 5) {
			randomed.add(categories[(int)(Math.random()*categories.length)]);
		}
		return randomed.toArray(new String[randomed.size()]);
	}
	
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
