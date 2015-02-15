import java.io.File;

// This program takes a command-line argument such as:
// java DirectoryContentsLister "/C/Temp"
// and lists the directory contents at that path.

public class DirectoryContentsLister {
	public static void main(String[] args) {
		File path = null;
		String[] results;
		
		try{
			path = new File(args[0]);
			
			results = path.list();
			
			for(String r: results){
				System.out.println(r);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
