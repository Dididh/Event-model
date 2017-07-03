import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Permutator{
	private static final String OUTPUT = "C:\\Users\\ijffdrie\\Downloads\\wiki_naf_out.tar\\wiki_naf_out\\testResults.txt";
		
	static ArrayList<ArrayList<String>> peoplePermutations = new ArrayList<ArrayList<String>>();
	static ArrayList<ArrayList<String>> placesPermutations = new ArrayList<ArrayList<String>>();
	static ArrayList<ArrayList<String>> eventsPermutations = new ArrayList<ArrayList<String>>();
	static ArrayList<ArrayList<String>> timesPermutations = new ArrayList<ArrayList<String>>();
	static ArrayList<ArrayList<String>> organisationsPermutations = new ArrayList<ArrayList<String>>();
	
	public static void main(String[] args){
		ArrayList<String> people = new ArrayList<String>();
		ArrayList<String> places = new ArrayList<String>();
		ArrayList<String> events = new ArrayList<String>();
		ArrayList<String> times = new ArrayList<String>();
		ArrayList<String> organisations = new ArrayList<String>();
		
		
		//Reads the files
		try{			
			File file = new File("C:\\Users\\ijffdrie\\Downloads\\wiki_naf_out.tar\\wiki_naf_out\\" + args[0]);
			Scanner scanner = new Scanner(file, "UTF-8");
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(OUTPUT), "UTF-8"));
			while(scanner.hasNextLine()){
				String[] contents = scanner.nextLine().split("\t");
				contents[0] = contents[0].trim();
				contents[1] = contents[1].trim();
				
				if(contents[1].equals("PER")){
					people.add(contents[0]);
				}
				else if(contents[1].equals("LOC")){
					places.add(contents[0]);
				}
				else if(contents[1].equals("event") || contents[1].equals("EVE")){
					events.add(contents[0]);
				}
				else if(contents[1].equals("DATE")){
					times.add(contents[0]);
				}
				else if(contents[1].equals("ORG")){
					organisations.add(contents[0]);
				}
			}
		}
		catch (FileNotFoundException e){
			e.printStackTrace();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		//Passes it on to permutatations
		permute(unique(people), new ArrayList<String>(), "people");
		permute(unique(places), new ArrayList<String>(), "places");
		permute(unique(events), new ArrayList<String>(), "events");
		permute(unique(times), new ArrayList<String>(), "times");
		permute(unique(organisations), new ArrayList<String>(), "orgs");
		
		System.out.println();
		System.out.println("People permutations:");
		printArrayArray(peoplePermutations);
		System.out.println();
		System.out.println("Place permutations:");
		printArrayArray(placesPermutations);
		System.out.println();
		System.out.println("event permutations:");
		printArrayArray(eventsPermutations);
		System.out.println();
		System.out.println("Time permutations:");
		printArrayArray(timesPermutations);
		System.out.println();
		System.out.println("Organisation permutations:");
		printArrayArray(organisationsPermutations);
	}
	
	static public ArrayList<String> unique(ArrayList<String> original){
		ArrayList<String> returnable = new ArrayList<String>();
		HashSet<String> uniques = new HashSet<String>();
		for(String x : original){
			uniques.add(x);
		}
		Iterator<String> iterator = uniques.iterator();
		while(iterator.hasNext()){
			returnable.add(iterator.next());
		}
		return returnable;
	}
	
	static public void permute(ArrayList<String> adding, ArrayList<String> currentList, String target){
		addRight(currentList, target);
		
		for(int x = 0 ; x < adding.size() ; x++){
			ArrayList<String> nextAdding = new ArrayList<String>();
			for(int y = x ; y < adding.size() ; y ++){
				if(x != y){
					nextAdding.add(adding.get(y));
				}
			}
			
			ArrayList<String> nextCurrent = new ArrayList<String>();
			for(int y = 0 ; y < currentList.size() ; y++){
				nextCurrent.add(currentList.get(y));
			}
			nextCurrent.add(adding.get(x));
			permute(nextAdding, nextCurrent, target);
		}
	}
	
	static public void addRight(ArrayList<String> list, String target){
		if(target.equals("people")){
			peoplePermutations.add(list);
			return;
		}
		if(target.equals("places")){
			placesPermutations.add(list);
			return;
		}
		if(target.equals("events")){
			eventsPermutations.add(list);
			return;
		}
		if(target.equals("times")){
			timesPermutations.add(list);
			return;
		}
		if(target.equals("orgs")){
			organisationsPermutations.add(list);
			return;
		}
	}
	
	public static void printArrayArray(ArrayList<ArrayList<String>> listList){
		for(ArrayList<String> x : listList){
			for(String y : x){
				System.out.print(y + ", ");
			}
			System.out.println();
		}
	}
}