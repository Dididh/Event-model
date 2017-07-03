import java.io.File;
import java.io.IOException;
import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

class FactInterpreter{
	public static void main(String[] args){
		String origin = args[0];
		String pred1 = "";
		String pred2 = "";
		String relation1 = "";
		String relation2 = "";
		try{
			pred1 = args[1];
			pred2 = args[2];
			relation1 = args[3];
			relation2 = args[4];
		}
		catch(ArrayIndexOutOfBoundsException e){
		}
		ArrayList<String> lines = new ArrayList<String>();
		
		File file = new File(origin);
		try{
			Scanner scanner = new Scanner(file, "UTF-8");
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("output.txt"), "UTF-8"));
			
			while(scanner.hasNextLine()){
				String line = scanner.nextLine();
				while(line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)").length < 50 && scanner.hasNextLine()){
					line = line + "#####" + scanner.nextLine();
				}
				lines.add(line);
			}
			
			ArrayList<String> relations = new ArrayList<String>();
			HashSet<String> sentences = new HashSet<String>();
			
			for(String x : lines){
				String[] components = x.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				String relationEntry = components[13];
				String sentence = components[48];
				//Detects and removes quotes in beginning and end of sentences
				if(relationEntry.startsWith("\"") && relationEntry.endsWith("\"")){
					relationEntry = relationEntry.substring(1, relationEntry.length()-1);
				}
				relationEntry = relationEntry + "#######" + sentence;
				relations.add(relationEntry);
				sentences.add(sentence);
			}
			
			ArrayList<String> relationsSplit = new ArrayList<String>();
			HashSet<String> uniqueRelations = new HashSet<String>();
			
			for(String x : relations){
				String[] components = x.split("#######"); 
				String sentence = components[1];
				components = components[0].split("#####");
				for(String y : components){
					if(!(y.equals("no_relation"))){
						relationsSplit.add(y);
						uniqueRelations.add(y + "#######" + sentence);
					}
				}
			}
			
			HashSet<String> acceptedFacts = new HashSet<String>();
			int y = 0;
			
			for(String x : uniqueRelations){
				String[] components = x.split("#######"); 
				String sentence = components[1];
				sentence = sentence.replaceAll("\"","");
				x = components[0];
				if(Collections.frequency(relationsSplit,x) > 9){
					components = x.split("-r-");
					if(components.length < 2 ){
						components = x.split("--");
					}
					String[] components1;
					String[] components2;
					if(components.length > 1){
						components1 = components[0].split("_");
						components2 = components[1].split("_");
						String eventName = "\"" + sentence.substring(0,5) + components[0] + "\""; 
						String shortEventName = eventName.substring(1, eventName.length() - 1);
						bw.write("niodpipe:" + eventName + " rdf:type " + "sem:Event");
						bw.newLine();
						bw.write("niodpipe:\"" + components1[0] + "\" rdf:type " + "sem:EventType");
						bw.newLine();
						bw.write("niodpipe:\"" + components2[0] + "\" rdf:type " + "sem:Time");
						bw.newLine();
						bw.write("niodpipe:\"" + shortEventName + "\" sem:eventType " + "niodpipe:\"" + components1[0] + "\"");
						bw.newLine();
						bw.write("niodpipe:\"" + shortEventName + "\" sem:hasTime " + "niodpipe:\"" + components2[0] + "\"");
						bw.newLine();
					}
					bw.flush();
					y++;
				}
			}
			
			
			
		}
		catch(IOException e){
			e.printStackTrace();
		}
			
	}
}