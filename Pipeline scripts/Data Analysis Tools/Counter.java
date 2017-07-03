import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.*;

public class Counter{
	public static void main(String[] args){
		try{
			String arg1 = args[0];
			int arg2 = Integer.parseInt(args[1]);
			Scanner scanner = new Scanner(new File(arg1));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("outputCounter.txt"), "UTF-8"));
			scanner.nextLine();
			while(scanner.hasNextLine()){
				String line = scanner.nextLine();
				String[] components = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				String[] entities = components[arg2].split("####");
				for(String x : entities){
					bw.write(components[33] + "####" + x.split("_")[0]);
					bw.newLine();
				}
				bw.flush();
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
			
	}
}