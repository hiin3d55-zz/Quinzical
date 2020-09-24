import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Category {
	private File _dataRecordFolder;

	public Category() {
		_dataRecordFolder = new File("data/categories");
	}

	public String[] getCategories(boolean gameModule) {
		File file = new File("questionBank");
		String[] allCategories = file.list();

		if (!gameModule) {
			return allCategories;
		}
			
		if (_dataRecordFolder.exists()) {
			return _dataRecordFolder.list();
		} else {
			_dataRecordFolder.mkdirs();
			String[] categories = randomiseCategories(allCategories);
			
			try {
				recordCategories(categories);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return categories;
		}
	}
	
	private void recordCategories(String[] categories) throws IOException {
		for (int i = 0; i < categories.length; i++) {
			File file = new File(_dataRecordFolder.getPath() + "/" + categories[i]);
			file.createNewFile();
		}
	}

	private String[] randomiseCategories(String[] categories) {
		Set<String> randomed = new HashSet<String>();

		while (randomed.size() < 5) {
			randomed.add(categories[(int)(Math.random()*categories.length)]);
		}
		return randomed.toArray(new String[randomed.size()]);
	}
	
	public void update(String category) {
		File file = new File(_dataRecordFolder + "/" + category);
		file.delete();
	}
}
