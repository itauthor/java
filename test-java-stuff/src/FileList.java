import java.io.File;
import java.util.ArrayList;

public class FileList {

	// Instantiate the ArrayList that will contain the list of files:
	static ArrayList<String> contents = new ArrayList<>();

	public static int getFilePaths(String path, String ext) {
		
		File theDir = new File(path);
		
		if (!theDir.exists()) {
			return 0;
		}

		File[] filesInDir = theDir.listFiles();
		for (File f : filesInDir) {
			String file = f.getAbsolutePath();
			if (f.isDirectory()) {
				getFilePaths(file, ext);
			} else {
				if (file.toLowerCase().endsWith("." + ext.toLowerCase())) {
					contents.add(file);
				}
			}
		}
		return 1;
	}

}
