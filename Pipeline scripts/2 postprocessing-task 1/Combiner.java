import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.*;

public class Combiner{	
	static BufferedWriter bw;
	public static void main(String[] args){
				try{
					String file1name = args[0];
					String file2name = args[1];
					bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[2] + ".csv"), "UTF-8"));
					File file1 = new File(file1name);
					File file2 = new File(file2name);
					Scanner scanner1 = new Scanner(file1, "UTF-8");
					Scanner scanner2 = new Scanner(file2, "UTF-8");
					bw.write("events1,events2,pair1,pair2,pair3,pair4,pair5,pair6,pair7,pair8,pair9,pair10,pair11,pair12,pair13,pair14,pair15,pair16,pair17,pair18,pair19,pair20,pair21,pair22,pair23,pair24,pair25,pair26,pair27,pair28,pair29,pair30,sentence1,sentence2");
					bw.newLine();
					while(scanner1.hasNextLine() && scanner2.hasNextLine()){
						fuse(scanner1.nextLine(), scanner2.nextLine());
					}
					bw.flush();
				}
				catch(IOException e){
					e.printStackTrace();
				}
	}
	
	public static void fuse(String line1, String line2){
		try{
			String[] lines1 = line1.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
			String[] lines2 = line2.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
			String sentence1 = lines1[0];
			lines1 = shift(lines1);
			String sentence2 = lines2[0];
			lines2 = shift(lines2);
			
			if(lines1.length < 1 || lines2.length < 1){
				//bw.write("SKIPPED,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");
				return;
			}
			
			int x = 0;
			for(x = 0 ; x < lines1.length - 1 ; x++){
				bw.write(lines1[x] + "####");
			}
			bw.write(lines1[x] + ",");
			for(x = 0 ; x < lines2.length - 1 ; x++){
				bw.write(lines2[x] + "####");
			}
			bw.write(lines2[x] + ",");
			
			int positions = 30;
			for(String part1 : lines1){
				for(String part2 : lines2){
					bw.write("\"" + part1 + ", " + part2 + "\",");
					positions--;
				}
			}
			for(x = 0 ; x < positions ; x++){
				bw.write(",");
			}
			bw.write(sentence1 + "," + sentence2);
			bw.newLine();
			bw.flush();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static String[] shift(String[] line){
		String[] returnable = new String[line.length - 1];
		for(int i = 0 ; i < returnable.length ; i++){
			returnable[i] = line[i+1];
		}
		return(returnable);
	}
}
