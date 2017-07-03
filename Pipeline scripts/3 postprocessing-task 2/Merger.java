import java.io.File;
import java.io.IOException;
import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

class Merger{
	static String folder = "C:\\Users\\ijffdrie\\Downloads\\ToRDF\\merging input";
	
	public static void main(String[] args){
		try{
			ArrayList<File> files = listFilesForFolder(new File(folder));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("merged.txt"), "UTF-8"));
			Scanner scanner;
			HashSet<String> allLines = new HashSet<String>();
			bw.write("@prefix niodpipe: <http://example.org/path/> .");
			bw.newLine();
			bw.write("@prefix sem: <http://semanticweb.cs.vu.nl/2009/11/sem/,/> .");
			bw.newLine();
			bw.newLine();
			
			for(File x : files){
				scanner = new Scanner(new File(folder + "\\" + x));
				while(scanner.hasNextLine()){
					String line = scanner.nextLine();
					allLines.add(line);
				}
			}
			for(String x : allLines){
				bw.write(x + " .");
				bw.newLine();
			}
			bw.flush();
		}
		catch(IOException e){
			e.printStackTrace();
		}
    }
    
    
	static public ArrayList<File> listFilesForFolder(File folder) {
		ArrayList<File> returnable = new ArrayList<File>();
		for(File x : folder.listFiles()) {
				returnable.add(new File(x.getName()));
		}
		return(returnable);
	}
}