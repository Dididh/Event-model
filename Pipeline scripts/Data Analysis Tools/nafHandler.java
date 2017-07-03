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

public class nafHandler{
	private static final File INPUT = new File("C:\\Users\\ijffdrie\\Downloads\\wiki_naf_out.tar\\wiki_naf_out\\test"); 
	private static final String OUTPUT = "C:\\Users\\ijffdrie\\Downloads\\wiki_naf_out.tar\\wiki_naf_out\\testResults.txt";
	private static BufferedWriter bw;
	
	public static void main(String []args){
		
		try{
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(OUTPUT), "UTF-8"));
		}
		catch (IOException e) {

			e.printStackTrace();
		}
		
		if(INPUT.isDirectory()){
			for(File file : INPUT.listFiles()){
				perFile(file);
			}
		}
	}
	
	private static void perFile(File file){
		System.out.println(file.getName());
		
			try{
				String[] filenameFull = file.getName().split(".naf");
				filenameFull = filenameFull[0].split("__");
				String filename = filenameFull[1];
				filename = filename.replaceAll(" ","_");
				String source = ("https://nl.wikipedia.org/wiki/" + filename);
				Scanner scanner = new Scanner(file, "UTF-8");
				int step = 0;
				NafEntity naf = new NafEntity(source);
				
				//Step 0: Nafhandlers - Skipping
				while(scanner.hasNextLine() && step == 0){
					String line = scanner.nextLine().trim();
					if(line.equals("</nafHeader>")){
						step++;
					}
				}
				
				//Step 1: RawText - Has complications because tags are on
				//same line as text
				while(scanner.hasNextLine() && step == 1){
					String line = scanner.nextLine().trim();
					if(line.equals("<text>")){
						step++;
					}
					else{
						line = line.replaceAll("<raw>","");
						line = line.replaceAll("</raw>","");
						naf.addText(line);
					}
				}
				
				//Step 2: Text - skipping
				while(scanner.hasNextLine() && step == 2){
					String line = scanner.nextLine().trim();
					if(line.equals("<terms>")){
						step++;
					}
				}
				
				//Step 3: Terms
				List<String> subject = new ArrayList<String>();
				while(scanner.hasNextLine() && step == 3){
					String line = scanner.nextLine().trim();
					String[] components = line.split(" ");
					if(components[0].equals("<term") || components[0].equals("<target") || components[0].equals("<externalRef")){
						subject.add(line);
					}
					components = components[0].split("-");
					if(components[0].equals("<!")){
						subject.add(line);
					}
					else if(components[0].equals("</term>")){
						naf.addTerm(subject);
						subject = new ArrayList<String>();
					}
					else if(components[0].equals("<deps>")){
						step++;
					}
				}
				
				//Step 4: Deps
				while(scanner.hasNextLine() && step == 4){
					String line = scanner.nextLine().trim();
					if(line.equals("</deps>")){
						step++;
					}
					else{
						String line2 = scanner.nextLine().trim();
						naf.addDep(line, line2);
					}
				}
				
				//Step 5: Opinions - skip
				while(scanner.hasNextLine() && step == 5){
					String line = scanner.nextLine().trim();
					if(line.equals("<entities>")){
						step++;
					}
				}
				
				//Step 6: Entities
				subject = new ArrayList<String>();
				while(scanner.hasNextLine() && step == 6){
					String line = scanner.nextLine().trim();
					String[] components = line.split(" ");
					if(components[0].equals("<entity") || components[0].equals("<target") || components[0].equals("<externalRef")){
						subject.add(line);
					}
					components = components[0].split("-");
					if(components[0].equals("<!")){
						subject.add(components[2]);
					}
					else if(components[0].equals("</entity>")){
						naf.addEntity(subject);
						subject = new ArrayList<String>();
					}
					else if(components[0].equals("<coreferences>")){
						step++;
					}
				}
				
				//Step 7: Coreferences - skip
				while(scanner.hasNextLine() && step == 7){
					String line = scanner.nextLine().trim();
					if(line.equals("</coreferences>")){
						step++;
					}
				}
				
				//Step 8: Constituency - skip
				while(scanner.hasNextLine() && step == 8){
					String line = scanner.nextLine().trim();
					if(line.equals("</constituency>")){
						step++;
					}
				}
				
				//step 9: SRL - skip 
				while(scanner.hasNextLine() && step == 9){
					String line = scanner.nextLine().trim();
					if(line.equals("</srl>")){
						step++;
					}
				}
				
				//Step 10: TimeExpressions
				subject = new ArrayList<String>();
				boolean oneRun = true;
				while(scanner.hasNextLine() && step == 10){
					String line = scanner.nextLine().trim();
					String[] components = line.split(" ");
					
					//The first timex3 is indicative for the document, not a timexpression in itself. 
					if(components[0].equals("<timex3") && oneRun){
						oneRun = false;
					}
					else if(components[0].equals("<timex3") || components[0].equals("<target") || components[0].equals("<externalRef")){
						subject.add(line);
					}
					components = components[0].split("-");
					if(components[0].equals("<!")){
						subject.add(line);
					}
					else if(components[0].equals("</timex3>")){
						naf.addTimeExpression(subject);
						subject = new ArrayList<String>();
					}
					else if(components[0].equals("<opinions>")){
						step++;
					}
				}
				
			}
			catch (FileNotFoundException e){
				e.printStackTrace();
			}
		}
}