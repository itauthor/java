public class RecursiveFilteredDirContentsLister {
	
	public static void main(String[] args) {
		// Directory to parse, extension to filter on, and max in output list:
		String path = "C:\\Temp\\images1 - Copy (4)";
		String ext = "png";
		int maxToShow = 15;

		int retVal = FileList.getFilePaths(path, ext);	
		if (retVal == 0) {
			System.out.println("Directory not found: " + path);
		} else {
			int numFiles = FileList.contents.size();
			System.out.println("Number of files found: " + numFiles);
			if (numFiles <= maxToShow) {
				for(String f: FileList.contents) {
					System.out.println(f);
				}
			}			
		}
	}
}