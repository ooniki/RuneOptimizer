import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import Rune.Rune;
import profile.Profile;
import stuff.StuffNode;
import stuff.StuffSelector;
import team.Team;

public class Main {
	
	public void run(JSONObject config) {
		try {
			System.out.println("Rune Optimizer");
			
			JSONObject json = Profile.getJsonFromFile(config.getString("data"));
//			Iterator<?> keys = json.keys();
//			while( keys.hasNext() ) {
//			    String key = (String)keys.next();
//			    System.out.println(key);
//			}
			
			Profile profile = new Profile();
			profile.loadFromJson(json);
			System.out.println("Runes Count: " + profile.getRunes().length);
			
			JSONArray blockedMonsters = config.getJSONArray("blockedMonsters");
			List<Long> bannedUniqueId = new ArrayList<Long>();
			for (int i = 0; i < blockedMonsters.length(); i++) {
				for (Rune rune : profile.getRunes()) {
					if (rune.getMonster().equals(blockedMonsters.getString(i))) {
						bannedUniqueId.add(rune.getUniqueId());
					}
				}
			}
			profile.getBannedRunesUniqueId().addAll(bannedUniqueId);
			
			profile.buildTeams(config.getJSONArray("teams"));
			
			for (Team team : profile.getTeams()) {
				System.out.println("Start team: " + team.getName());
				profile.buildAllStuff();
				System.out.println("Total stuff: " + profile.getAllStuff().length);
				
				Optimizer optimizer = new Optimizer();
				List<StuffNode> stuffsNode = optimizer.optimizeTeam(profile.getAllStuff(), team);
				
				StuffSelector stuffSelector = new StuffSelector();
				List<Long> usedUniqueId = stuffSelector.selectTeamStuff(team, stuffsNode, 3);
				profile.getBannedRunesUniqueId().addAll(usedUniqueId);
			}
			
			System.out.println("Finish");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		Main main = new Main();
		JSONObject config = Profile.getJsonFromFile(args[0]);
		System.out.println("CONFIG:");
		System.out.println(config.toString());
		main.run(config);
	}

}
