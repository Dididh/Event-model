import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.*;

public class VerticalSplit{
	//File OUTPUT = new File("results.csv");
	//BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(OUTPUT), "UTF-8"));
	
	public static void main(String[] args){
		int previousID = 0;
		int startID = 0;
		try{			
			File input = new File("C:\\Users\\ijffdrie\\Downloads\\VerticalSplit\\Fixed - Werkwoorden1.csv"/* + args[0]*/);
			Scanner scanner = new Scanner(input, "UTF-8");
			String sentence = "";
			String url = "";
			String preselectedEntities = "";
			ArrayList<ArrayList<String>> sentenceEntities = new ArrayList<ArrayList<String>>(); //a list of the wordEntities for a particular sentence.
			ArrayList<String> finalEntities = new ArrayList<String>(); //The final list of entities from the sentence.
			//scanner.nextLine();
			
			while(scanner.hasNextLine()){
				String line = scanner.nextLine();
				//System.out.println(line);
				String[] components = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				ArrayList<String> entryEntities = new ArrayList<String>(); //contains the raw data from the CSV blocks.

				int nextID = Integer.parseInt(components[136]);
				if(nextID != previousID){
					//Call function for printing the data of the previous ID, reset data for new ID.
					if(previousID != startID){
						finalEntities = finalize(sentenceEntities);
						printLine(sentence, finalEntities);
						System.out.println();
						sentenceEntities = new ArrayList<ArrayList<String>>();
					}
					url = components[140];
					sentence = components[141];
					System.out.println(sentence);
					previousID = nextID;
				}
				
				for(int i = 12 ; i < 42 ; i++){ //Target every sentence that could contain an entity
					if(components[i].length().trim() > 0){
						System.out.println("\""+components[i].trim()+"\"");
						entryEntities.add(components[i].trim());
					}
				}
				sentenceEntities.add(entryEntities);
				
			}
		}
		catch(FileNotFoundException e){
		}
	}
	
	public static ArrayList<String> finalize(ArrayList<ArrayList<String>> sentenceEntities){
		//Convert the list of lists to a single list.
		ArrayList<String> entities = new ArrayList<String>();
		for(ArrayList<String> x: sentenceEntities){
			for(String y : x){
				entities.add(y);
			}
		}
		//Find all unique entities by making a set based off the string.
		HashSet<String> uniqueEntities = new HashSet<String>(entities);
		ArrayList<String> finalEntities = new ArrayList<String>();
		for(String x : uniqueEntities){
			int count = Collections.frequency(entities,x);
			if(count > 9 && !(x.trim().equals("-1--2"))){
				finalEntities.add(x);
			}
		}
		return(finalEntities);
	}
	
	public static void printLine(String sentence, ArrayList<String> finalEntities){
		System.out.println("1");
		String[] sentenceParts = sentence.split(" ");
		for(String x: finalEntities){
			int beginWord;
			int endWord;
			x = x.trim();
			String[] xComponents = x.split("-");
			if(xComponents.length > 1){
				beginWord = Integer.parseInt(xComponents[0]);
				endWord = Integer.parseInt(xComponents[1]);
			}
			else{
				beginWord = Integer.parseInt(x);
				endWord = Integer.parseInt(x);
			}
			
			//test
			int startCoord = sentence.indexOf(sentenceParts[beginWord]);
			int wordLength = 0;
			for(int y = beginWord ; y < endWord + 1 ; y++){
				System.out.print(sentenceParts[y]);
				wordLength = wordLength + sentenceParts[y].length();
			}
			int endCoord = startCoord + wordLength;
			System.out.print("_" + startCoord + "_" + endCoord);
			System.out.print(",");
		}
		
	}
}