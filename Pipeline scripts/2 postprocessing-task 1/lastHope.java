import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.*;

public class lastHope{
	private static String output; 
	private static int number;
	
	public static void main(String[] args){
		Scanner scanner = new Scanner("Empty sentence");
		
		ArrayList<ArrayList<String>> allEntities = new ArrayList<ArrayList<String>>();
		int runningCounter = 0;
		output = "C:\\Users\\ijffdrie\\Downloads\\lastHope\\" + args[0];
		String origin = args[1];
		number = Integer.parseInt(args[2]); //137 for locations, 135 for events, 74 for dates, 73 for actors;
		
		File file = new File("C:\\Users\\ijffdrie\\Downloads\\" + origin);
		try{
			scanner = new Scanner(file, "UTF-8");
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), "UTF-8"));
		
			scanner.nextLine();
			while(scanner.hasNextLine()){
				String entry = scanner.nextLine();
				
				runningCounter++;
				allEntities.add(rowEntities(entry));
				
				if(runningCounter == 15){
					
					//Filters all entities so that only phrases with 10 or more entries are visible. Also combines entries where the only difference is punctuation in beginning or end.
					ArrayList<String> filtered = filter(allEntities);
					
					String[] components = entry.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
					String sentence = components[140];
					//Detects and removes quotes in beginning and end of sentences
					if(sentence.startsWith("\"") && sentence.endsWith("\"")){
						sentence = sentence.substring(1, sentence.length()-1);
					}
					
					ArrayList<String> finalized = finalize(filtered, sentence);
					for(int x = 0 ; x < finalized.size() -1 ; x++){
						bw.write(finalized.get(x) + ",");
					}
					bw.write(finalized.get(finalized.size() -1));
					bw.newLine();
					
					
					/*for(String x : finalized){
						System.out.print(x + ", ");
					}
					System.out.println();*/
					
					
					allEntities = new ArrayList<ArrayList<String>>();
					runningCounter = 0;
				}
			}
			bw.flush();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static ArrayList<String> rowEntities(String entry){
		String[] preselectedEntities = new String[0];
		String[] newEntityLocations;
		String[] newEntities;
		
		String[] components = entry.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
		String sentence = components[140];
		String id = components[136];
			
		//Detects and removes quotes in beginning and end of sentences
		if(sentence.startsWith("\"") && sentence.endsWith("\"")){
			sentence = sentence.substring(1, sentence.length()-1);
		}
			
		String preselectedSentence = components[number]; //137 for locations, 135 for events, 74 for dates, 73 for actors;
		preselectedEntities = preselectedOrder(entry);
		
		ArrayList<String> newSelections = new ArrayList<String>();
		
		for(int i = 12 ; i < 42 ; i++){ //Target every sentence that could contain an entity, retrieve the locations of all entities.
			if(components[i].trim().length() > 0){
				if((!(newSelections.contains(components[i].trim()))) && !(components[i].equals("-1--2"))){
					newSelections.add(components[i].trim());
				}
				if(components[i].equals("-1--2")){
					int compensated = (i - 12);
					if(compensated > 0 && compensated < 11){
						compensated = compensated + 9;
					}
					else if(compensated > 11 && compensated < 22){
						compensated = compensated + 8;
					}
					else if(compensated == 11){
						compensated = 1;
					}
					else if(compensated > 21){
						compensated = compensated-20;
					}
					newSelections.add("-1--2####" + compensated);
				}
			}
		}
		
		ArrayList<String> entities = new ArrayList<String>();
		String[] sentenceSplit = sentence.split(" ");
		for(String x : newSelections){
			String[] y = x.split("####");
			if((y[0]).equals("-1--2")){
				entities.add(preselectedEntities[Integer.parseInt(y[1])]);
			}
			else{
				y = x.split("-");
				if(y.length > 1){
					int start = Integer.parseInt(y[0]);
					int end = Integer.parseInt(y[1]);
					String combined = "";
					for( start = start ; start < (end + 1) ; start++){
						combined = combined + " " + sentenceSplit[start];
					}
					entities.add(combined);
				}
				else{
					entities.add(sentenceSplit[Integer.parseInt(x)]);
				}
			}
		}
		return entities;
	}
	
	public static ArrayList<String> filter(ArrayList<ArrayList<String>> allEntities){
		ArrayList<String> listEntities = new ArrayList<String>();
		HashSet<String> returnableHash = new HashSet<String>();
		ArrayList<String> returnable = new ArrayList<String>();
		
		for(ArrayList<String> x: allEntities){
			for(String y : x){
				y = y.replaceAll("^\\p{Punct}|\\p{Punct}$", "").trim();
				listEntities.add(y);
			}
		}
		
		for(String x : listEntities){
			if(Collections.frequency(listEntities,x) > 9){
				returnableHash.add(x);
			}
		}
		
		for(String x : returnableHash){
			returnable.add(x);
		}
		return returnable;
	}
	
	public static ArrayList<String> finalize(ArrayList<String> filtered, String sentence){
		ArrayList<String> returnable = new ArrayList<String>();
		returnable.add("\"" + sentence + "\"");
		for(String x: filtered){
			int startCoord = sentence.indexOf(x);
			if(startCoord < 0){
				System.out.println(" ERROR ERROR ERROR " + x + " ERROR ERROR ERROR ");
			}
			int endCoord = startCoord + x.length();
			returnable.add(x + "_" + startCoord + "_" + endCoord);
		}
		return returnable;
	}
	
	public static String[] preselectedOrder(String entry){
		String[] components = entry.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
		String preselectedSentence = components[number];
		String sentence = components[components.length-1];
		
		
		if(sentence.startsWith("\"") && sentence.endsWith("\"")){
			sentence = sentence.substring(1, sentence.length()-1);
		}
		if(preselectedSentence.startsWith("\"") && preselectedSentence.endsWith("\"")){
			preselectedSentence = preselectedSentence.substring(1, preselectedSentence.length()-1);
		}
		String[] preselectedComponents = preselectedSentence.split(", ");
		if(preselectedComponents.length == 0){
			return(preselectedComponents);
		}
		
		sentence = " " +sentence+ " ";
		int latest = sentence.length();
		
		int[] numberList = new int[preselectedComponents.length];
		int y = 0;
		for(String x: preselectedComponents){
			int index = sentence.indexOf(" " + x + " ");
			if(index == -1){
				numberList[y] = latest;
				latest++;
			}
			else{
				numberList[y] = index;
			}
			y++;
		}
		
		//Double Bubble sort
		int tempInt;
		String tempString;
		for(int i = 0 ; i < numberList.length ; i++){
			for(int j = 1 ; j < (numberList.length - i) ; j++){
				if(numberList[j-1] > numberList[j]){
					tempInt = numberList[j-1];
					tempString = preselectedComponents[j-1];
					numberList[j-1] = numberList[j];
					preselectedComponents[j-1] = preselectedComponents[j];
					numberList[j] = tempInt;
					preselectedComponents[j] = tempString;
				}
			}
		}
		return preselectedComponents;
	}
}