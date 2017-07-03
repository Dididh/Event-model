import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class Fixer{
	public static void main(String[] args){
		try{
			try{			
				File input = new File("C:\\Users\\ijffdrie\\Downloads\\Namen3.csv"/* + args[0]*/);
				Scanner scanner = new Scanner(input, "UTF-8");
				scanner.nextLine();
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Namen3.csv"), "UTF-8"));
				
				while(scanner.hasNextLine()){
					String line = scanner.nextLine();
					String[] components = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
					String text = components[components.length-1];
					boolean quotes = false;
					if(text.startsWith("\"") && text.endsWith("\"")){
						text = text.substring(1, text.length()-1);
						quotes = true;
					}
					text = text.replaceAll("[.!?,\\/\"'>)(<-]", " $0 "); //Need fix for not occuring when between two letters. 
					text = text.replaceAll("Ã«", "ë");
					text = text.replaceAll("   "," ");
					text = text.replaceAll("  "," ");
					text = text.replaceAll("  "," ");
					if(quotes){
						text = "\"" + text + "\"";
					}
					components[components.length-1] = text;
					for(String x : components){
						bw.write(x + ",");
					}
					bw.newLine();
					bw.flush();
				}
			}
			catch(FileNotFoundException e){
				e.printStackTrace();
			}
		}catch(IOException e2){
			e2.printStackTrace();
			
		}
		
	}
}