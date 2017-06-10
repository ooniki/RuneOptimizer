package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.codehaus.jettison.json.JSONObject;

public class JSONFileReader {
	
	public static JSONObject getJsonFromFile(String path) throws Exception {
		File file = new File(path);
		
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		
		String fileContent = "";
		String currentline = null;
		while ((currentline = br.readLine()) != null) {
			fileContent += currentline;
		}
		br.close();
		fr.close();
		
		JSONObject json = new JSONObject(fileContent);
		return json;
	}
}
