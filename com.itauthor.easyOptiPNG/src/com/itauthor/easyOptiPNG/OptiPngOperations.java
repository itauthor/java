package com.itauthor.easyOptiPNG;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.commons.lang3.SystemUtils;




// Try running the program and capture its exit code
public class OptiPngOperations {
	
	public static int checkItRuns(String progName, String progFlag) throws IOException {
		int exitValue = 1;  //Assume there was a problem		
		String[] cmd;		
		if (SystemUtils.IS_OS_WINDOWS) {
		   cmd = new String[]{"CMD", "/C", progName, progFlag};	
		} else {
			cmd = new String[]{"/bin/bash", "-c", progName, progFlag};	
		}
	    ProcessBuilder probuilder = new ProcessBuilder( cmd );
	    try {
	    	Process process = probuilder.start();
	        exitValue = process.waitFor();
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }   
	    return exitValue;
	}
	
	public static OutputAndResult run(String progName, String targetType, String target) throws IOException {		
		if (targetType == "d") {
			target += "\\*.png";  //TODO: Change this for Linux
		}
		//The command to run is something such as:
		//optipng.exe -nc -nb -backup -o7 <filename>
//	    String[] command = {"CMD", "/C", progName, "-nc", "-nb", "-backup", "-o7", target};   //TODO: Change this for Linux
		System.out.println("progName: "+progName+"\ntarget: "+target);
	    String[] command = {"CMD", "/C", progName, target};   //TODO: Change this for Linux
	    String stdOutput = "";
	    int exitValue = 1;  //Assume there was a problem
	    ProcessBuilder probuilder = new ProcessBuilder( command );
	    try {
	    	Process process = probuilder.start();
	        InputStream inpStrm = process.getInputStream();
	        InputStreamReader isr = new InputStreamReader(inpStrm);
	        BufferedReader br = new BufferedReader(isr);
	        String line;
	        while ((line = br.readLine()) != null) {
	        	stdOutput += line + "\n";
	        }	        
	    	//TODO: FIGURE OUT WHY THIS BIT APPLIES TO NON-ERROR MESSAGES
	        inpStrm = process.getErrorStream();
	        isr = new InputStreamReader(inpStrm);
	        br = new BufferedReader(isr);
	        while ((line = br.readLine()) != null) {
	        	if (line.startsWith("** Processing:")){
		        	stdOutput += "============================================\n" + 
		        		line + "\n";	        		
	        	} else if (line.startsWith("Trying:") || 
	        			line.startsWith("  zc ") || 
	        			line.startsWith("Selecting parameters:") ||
	        			line.length() < 3){
	        		//Ignore such lines
		        } else {
		        	stdOutput += line + "\n";
	        	}
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

