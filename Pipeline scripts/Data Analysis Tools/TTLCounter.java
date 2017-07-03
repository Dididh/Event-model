import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.*;

public class TTLCounter{
	public static void main(String[] args){
		try{
			Scanner scanner = new Scanner(new File("Task 2 output.txt"));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("outputttl.txt"), "UTF-8"));
			scanner.nextLine();
			scanner.nextLine();
			scanner.nextLine();
			int verb = 0;
			int place = 0;
			int time = 0;
			int actor = 0;
			while(scanner.hasNextLine()){
				String line = scanner.nextLine();
				String[] components = line.split(" (?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				String type = components[1];
				
				if(!(type.equals("rdf:type"))){
					if(type.equals("sem:eventType")){
						verb++;
					}
					else if(type.equals("sem:hasPlace")){
						place++;
					}
					else if(type.equals("sem:hasTime")){
						time++;
					}
					else if(type.equals("sem:hasActor")){
						actor++;
					}
					
				}
				
				
			}
				System.out.println("Events have been made for a total of " + verb + " verbs.");
				System.out.println("These have relations with a total of " + place + " places,");
				System.out.println("a total of " + time + " times");
				System.out.println("and a total of " + actor + " actors.");
		}
		catch(IOException e){
			e.printStackTrace();
		}
			
	}
}