import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.*;

public class PostCounter{
	public static void main(String[] args){
		System.out.println("Dates:");
		execute("Datums");
		System.out.println("Verbs:");
		execute("Werkwoorden");
		System.out.println("Locations:");
		execute("Locaties");
		System.out.println("Actors:");
		execute("Namen");
	}
	
	static public void execute(String target){
		try{
			ArrayList<File> files = listFilesForFolder(new File(target));
			Scanner scanner;
			
			int oldEntities = 0;
			for(File x : files){
				scanner = new Scanner(new File(target + "\\" + x));
				while(scanner.hasNextLine()){
					String line = scanner.nextLine();
					String[] components = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
					oldEntities = oldEntities + (components.length - 1);
				}
			}
			System.out.println("	" + oldEntities + " entities after task execution");
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