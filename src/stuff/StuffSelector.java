package stuff;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import Rune.Rune;
import team.Team;

public class StuffSelector {

	public List<Long> selectTeamStuff(Team team, List<StuffNode> stuffsNode, int nbStuff) {
		List<Long> usedUniqueId = new ArrayList<Long>();
		
		try {
			nbStuff = nbStuff > stuffsNode.size() ? stuffsNode.size() : nbStuff;
			for (int i = 0; i < nbStuff; i++) {
				StuffNode current = stuffsNode.get(i);
				
				JSONObject currentObj = new JSONObject();
				currentObj.put("id", i);
				
				JSONArray teamJson = new JSONArray();
				while(current != null) {
					teamJson.put(current.getStuffedMonster().toJSON());			
					current = current.getParent();
				}
				currentObj.put("team", teamJson);
				
				File file = new File("./" + team.getName() + i + ".stuff");
				FileWriter fw = new FileWriter(file);
				fw.write(currentObj.toString());
				fw.close();
				
			}
			
			int selectedId = -1;
			while (selectedId < 0 || selectedId >= nbStuff) {
				System.out.println("Select one team for " + team.getName() + ". Plz give team id:");
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				String selected = reader.readLine();
				selectedId = Integer.parseInt(selected);
			}
			
			for (int i = 0; i < nbStuff; i++) {
				if (i != selectedId) {
					File file = new File("./" + team.getName() + i + ".stuff");
					file.delete();
				}	
			}
			
			
			StuffNode selectedStuff = stuffsNode.get(selectedId);
			while(selectedStuff != null) {
				for (Rune rune :selectedStuff.getStuffedMonster().getStuff().getRunes())
					usedUniqueId.add(rune.getUniqueId());
				selectedStuff = selectedStuff.getParent();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return usedUniqueId;
	}
	
}
