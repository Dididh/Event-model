import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.*;

public class lastHopeCounter{
	public static void main(String[] args){//137 for locations, 135 for events, 74 for dates, 73 for actors;
		System.out.println("Dates:");
		execute("Datums", 74);
		System.out.println("Verbs:");
		execute("Werkwoorden", 135);
		System.out.println("Locations:");
		execute("Locaties", 137);
		System.out.println("Actors:");
		execute("Namen", 73);
	}
	
	static public void execute(String target, int targetID){
		try{
			ArrayList<File> files = listFilesForFolder(new File(target));
			Scanner scanner;
			
			int oldEntities = 0;
			for(File x : files){
				scanner = new Scanner(new File(target + "\\" + x));
				scanner.nextLine();
				while(scanner.hasNextLine()){
					String line = scanner.nextLine();
					String[] components = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
					String[] preselected = components[targetID].split(", ");
					for(String y : preselected){
						if(y.trim().length() != 0){
							oldEntities++;
						}
					}
					for(int y = 0 ; y < 14 ; y++){
						if(scanner.hasNextLine()){
							scanner.nextLine();
						}
					}
				}
			}
			System.out.println("	" + oldEntities + " entities prior to task execution");
			
			/*int removed = 0;
			int totalPost = 0;
			int i = 0;
			int accepted = 0;
			for(File x : files){
				scanner = new Scanner(new File(target + "\\" + x));
				scanner.nextLine();
				int newAccepted = 0;
				int count = 0;
				ArrayList<String> newSelections = new ArrayList<String>();
				while(scanner.hasNextLine()){
					String line = scanner.nextLine();
					String[] components = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
					
					for(int y = 12 ; y < 42 ; y++){
						if(components[y].trim().length() > 0){
							if(components[y].equals("-1--2")){
								newSelections.add("-1--2####" + y);
							}
						}
					}
						
					i++;
					if(i == 15){
						for(int z = 0 ; z < 50 ; z++){
							for(String za : newSelections){
								if(za.split("####")[1].equals(Integer.toString(z))){
									count++;
								}
							}
							if(count > 9){
								newAccepted++;
							}
							count = 0;
						}
						newSelections = new ArrayList<String>();
						accepted = accepted + newAccepted;
						newAccepted = 0;
						i = 0;
					}
				}
			}
			removed = oldEntities - accepted;
			System.out.println("	" + removed + " were removed."); */
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