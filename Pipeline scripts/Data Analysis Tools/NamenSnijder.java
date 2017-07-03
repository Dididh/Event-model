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

public class NamenSnijder{
	private static final String INPUT = "C:\\Users\\ijffdrie\\Downloads\\NameCarver 1\\Wikipediapagina's extra info voor Antske - Sheet1.tsv";
	private static final String OUTPUT = "C:\\Users\\ijffdrie\\Downloads\\wiki_naf_out.tar\\wiki_naf_out\\naamResultaten.txt";
	private static BufferedWriter bw;
	
	public static void main(String[] args){
		int currentID = 3000;
		
		try{
			BufferedWriter bm = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(OUTPUT), "UTF-8"));
			Scanner scanner = new Scanner(new File(INPUT), "UTF-8");
			while(scanner.hasNextLine()){
				String line = scanner.nextLine();
				String[] components = line.split("	");
				String filename = components[0];
				String name = components[1];
				String gender = components[2];
				String[] nameParts = name.split(" ",2);
				ArrayList<String> firstNames = new ArrayList<String>();
				ArrayList<String> lastNames = new ArrayList<String>();
				//First name is always a first name, last name is a last name. The rest of the name is divided between firstNames and lastNames by the loop. It is assumed that words like 'van' and 'der' divide the first names and last names (and belong to last names themselves). If there is no splitting term, it will assume all remaining names are last names.
				firstNames.add(nameParts[0]);
				if(nameParts.length > 1){
					String[] allNames = nameParts[1].split(" ");
					String[] subComponents = nameParts[1].split(" vander | van | von | de | du | den | der ");
					String[] extraNames;
					if(subComponents.length > 1){
						extraNames = subComponents[0].split(" ");
						int count = 0;
						for(String extraFirstName : extraNames){
							//for the cases where second name is 'van'.
							if(!extraFirstName.equals("van")){
								firstNames.add(extraFirstName);
								count++;
							}
						}
						String[] newNames = new String[allNames.length - count];
						for(int x = count ; x < allNames.length ; x++){
							newNames[x - count] = allNames[x];
						}
						allNames = newNames;
					}
					for(String extraLastName : allNames){
						lastNames.add(extraLastName);
					}						
				}
				//write sID
				bm.write(currentID + "	");
				
				//writes Gender Boolean
				if(gender.equals("Man")){
					bm.write("1	");
				}
				else if(gender.equals("Vrouw")){
					bm.write("2	");
				}
				else{
					bm.write("0	");
				}
				
				//writes last names
				bm.write("ln:");
				for(int x = 0 ; x < lastNames.size() ; x++){
					bm.write(lastNames.get(x));
					if(x + 1 != lastNames.size()){
						bm.write(" ");
					}
					else{
						bm.write(":");
					}
				}
				
				//writes first names
				bm.write("fn:");
				for(int x = 0 ; x < firstNames.size() ; x++){
					bm.write(firstNames.get(x));
					if(x + 1 != firstNames.size()){
						bm.write(" ");
					}
					else{
						bm.write(":");
					}
				}
				
				bm.write("in::");
				bm.write("inf:");
				bm.newLine();
				currentID++;
			}
		}
		catch (IOException e) {

			e.printStackTrace();
		}
	}
	
}