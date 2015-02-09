package runOptiPNG;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

// Try running the program and capture its exit code
public class ExternalOperation {
	
	public static int getExitCode(String progName, String progFlag) throws IOException {
		int exitValue = 1;  //Assume there was a problem
		
	    String[] command = {"CMD", "/C", progName, progFlag};
	    ProcessBuilder probuilder = new ProcessBuilder( command );
	    try {
	    	Process process = probuilder.start();
	        exitValue = process.waitFor();
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }   
	    return exitValue;
	}
	
	public static OutputAndResult getOutputAndResult(String progName, String progFlag) throws IOException {		
	    String[] command = {"CMD", "/C", progName, progFlag};
	    String stdOutput = "";
	    int exitValue = 1;  //Assume there was a problem
	    ProcessBuilder probuilder = new ProcessBuilder( command );
	    try {
	    	Process process = probuilder.start();
	        //Read out output
	        InputStream inpStrm = process.getInputStream();
	        InputStreamReader isr = new InputStreamReader(inpStrm);
	        BufferedReader br = new BufferedReader(isr);
	        String line;
	        while ((line = br.readLine()) != null) {
	        	stdOutput += line + "\n";
	        }	        
	        inpStrm = process.getErrorStream();
	        isr = new InputStreamReader(inpStrm);
	        br = new BufferedReader(isr);
	        while ((line = br.readLine()) != null) {
	        	stdOutput += line + "\n";
	        }	        
	        // WAIT TO GET THE EXIT CODE:
	        exitValue = process.waitFor();
	        
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }  
	        
	    OutputAndResult stdoutAndExitcode = new OutputAndResult(stdOutput, exitValue);
	    return stdoutAndExitcode;
	}		
}

