import java.io.File;
import java.util.ArrayList;

public class FilteredDirContentsLister {

	public static void main(String[] args) {

		// Directory to parse and extension to filter on:
		String path = "C:\\Alistair\\archive";
		String ext = "docx";

		// Check the directory exists, or exit:
		File theDir = new File(path);
		if (!theDir.exists()) {
			System.out.println("No such directory: " + path);
			System.exit(0);
		}

		// Get a file listing and then filter to a subset of files:
		File[] listOfFiles = getFileListing(theDir);
		ArrayList<String> filteredFiles = new ArrayList<>();
		filteredFiles = filterFileListing(listOfFiles, ext);
		

		// Only list the files if there are fewer than 10:
		int numFiles = filteredFiles.size();
		System.out.println("Number of files found: " + numFiles);
		if (numFiles < 10) {
			for(String f: filteredFiles) {
				System.out.println(f);
			}
		}
	}

	private static File[] getFileListing(File theDir) {
		File[] listOfFiles = theDir.listFiles();
		return listOfFiles;
	}

	private static ArrayList<String> filterFileListing(File[] listOfFiles, String ext) {
		String file;
		ArrayList<String> filteredFiles = new ArrayList<>();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				file = listOfFiles[i].getName();
				if (file.toLowerCase().endsWith("." + ext.toLowerCase())) {
					filteredFiles.add(file);
				}
			}
		}
		return filteredFiles;
	}

}