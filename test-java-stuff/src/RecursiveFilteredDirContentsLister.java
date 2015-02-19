import java.io.File;

public class RecursiveFilteredDirContentsLister {
	
	public static void main(String[] args) {
		// Directory to parse, extension to filter on, and max in output list:
		String path = "C:\\Alistair\\family\\";
		String ext = "pdf";
		int maxToShow = 15;

		// Check the directory exists, or exit:
		File theDir = new File(path);
		if (!theDir.exists()) {
			System.out.println("No such directory: " + path);
			System.exit(0);
		}

		// Build the list:
		FileList.getFilePaths(theDir, ext);		

		// Only list the files if there are fewer than 10:
		int numFiles = FileList.contents.size();
		System.out.println("Number of files found: " + numFiles);
		if (numFiles <= maxToShow) {
			for(String f: FileList.contents) {
				System.out.println(f);
			}
		}
	}

}