import java.io.File;

import org.apache.commons.lang3.SystemUtils;

// This program takes a command-line argument such as:
// java DirectoryContentsLister "/C/Temp"
// and lists the directory contents at that path.

public class DirectoryContentsLister {
	public static void main(String[] args) {
		File path = null;
		String[] results;

		if (args.length == 1) {
			try {

				if (SystemUtils.IS_OS_WINDOWS) {
					System.out.println("Windows");
				} else {
					System.out.println("not Windows");
				}

				path = new File(args[0]);

				results = path.list();

				for (String r : results) {
					System.out.println(r);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out
					.println("ERROR: You must pass 1 argument to this program.");
		}

	}
}
