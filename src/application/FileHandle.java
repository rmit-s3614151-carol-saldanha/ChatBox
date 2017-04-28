package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHandle {

	private String ipAddress;
	public FileHandle() {

	}

	
	public FileHandle(String text) {
		this.ipAddress = text;
	}


	public void writeToFile(String msg)
	{
		try{
		    PrintWriter writer = new PrintWriter("/Applications/MAMP/htdocs/RSA.txt", "UTF-8");
		    writer.write(msg);
		    writer.close();
		} catch (IOException e) {
		   // do something
		}
	}
	
	public String readFile()
	{
		try {
			   URL url = new URL("http://"+ipAddress+":8888/RSA.txt");
			   Scanner s = new Scanner(url.openStream());
			  return  s.nextLine();
			   // read from your scanner
			}
			catch(IOException ex) {
			   // there was some connection problem, or the file did not exist on the server,
			   // or your URL was not in the right format.
			   // think about what to do now, and put it here.
			   ex.printStackTrace(); // for now, simply output it.
			}
		return null;
	}
}
