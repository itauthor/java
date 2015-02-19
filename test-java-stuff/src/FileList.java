import java.io.File;
import java.util.ArrayList;


public class FileList {

	// Instantiate the ArrayList that will contain the list of files:
	static ArrayList<String> contents = new ArrayList<>();

	
	public static void getFilePaths(File path, String ext) {
        File[] filesInDir = path.listFiles();
        if (filesInDir == null) return;
        
        String file;

        for (File f: filesInDir) {
            if (f.isDirectory()) {
            	getFilePaths(f, ext);
            }
            else {
				file = f.getAbsolutePath();
				if (file.toLowerCase().endsWith("." + ext.toLowerCase())) {
					contents.add(file);
				}            	
            }
        }
    }	

}
