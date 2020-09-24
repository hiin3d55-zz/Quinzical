import java.io.File;
import java.io.IOException;

public class GamesModuleCategory extends Category{
	public String[] getCategories() {
		if (_dataRecordFolder.exists()) {
			return _dataRecordFolder.list();
		} else {
			_dataRecordFolder.mkdirs();
			File file = new File("questionBank");
			String[] categories = randomiseCategories(file.list());
			
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
}
