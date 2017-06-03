package profile;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import Rune.Rune;
import Rune.RuneBuilder;
import monster.Monster;
import monster.MonsterBuilder;
import stuff.Stuff;
import stuff.StuffBuilder;
import team.Team;
import team.TeamBuilder;

public class Profile {
	
	private MonsterBuilder monsterBuilder;
	private Monster[] monsters;
	
	private RuneBuilder runeBuilder;
	private Rune[] runes;
	
	private StuffBuilder stuffBuilder;
	private Stuff[] allStuff;
	
	private TeamBuilder teamBuilder;
	private Team[] teams;
	
	private List<Long> bannedRunesUniqueId;
	
	public Profile() {
		this.setMonsterBuilder(new MonsterBuilder());
		this.setMonsters(new Monster[0]);
		this.setRuneBuilder(new RuneBuilder());
		this.setRunes(new Rune[0]);
		this.setStuffBuilder(new StuffBuilder());
		this.setAllStuff(new Stuff[0]);
		this.setTeamBuilder(new TeamBuilder());
		this.setTeams(new Team[0]);
		this.setBannedRunesUniqueId(new ArrayList<Long>());
	}	
	
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
	
	public void loadFromJson(JSONObject json) {
		try {
			JSONArray runesJson = json.getJSONArray("runes");
			JSONArray monsterJson = json.getJSONArray("mons");
			
			this.monsters = this.monsterBuilder.buildMonsters(monsterJson);
			this.runes = this.runeBuilder.buildRunes(runesJson);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void buildTeams(JSONArray teams) throws Exception {
		this.teams = this.teamBuilder.buildTeams(this, teams);
	}
	
	public void buildAllStuff() {
		List<List<Rune>> selectedRunes = this.getSortedRunes(5, 9, this.bannedRunesUniqueId);
		this.allStuff = this.stuffBuilder.buildStuffs(selectedRunes);
	}
	
	public void printAllRunes() {
		for (Rune rune : this.getRunes()) {
			System.out.println(rune.toString());
		}
	}
	
	public List<List<Rune>> getSortedRunes(int stars, int level, List<Long> bannedRunesUniqueId) {
		List<List<Rune>> selectedRunes = new ArrayList<List<Rune>>();
		selectedRunes.add(new ArrayList<Rune>());
		selectedRunes.add(new ArrayList<Rune>());
		selectedRunes.add(new ArrayList<Rune>());
		selectedRunes.add(new ArrayList<Rune>());
		selectedRunes.add(new ArrayList<Rune>());
		selectedRunes.add(new ArrayList<Rune>());
		
		for (Rune rune : this.runes) {
			if (rune.getLevel() >= level && rune.getStars() >= stars) {
				long id = rune.getUniqueId();
				boolean banned = false;
				for (long bannedRune : bannedRunesUniqueId) {
					if (id == bannedRune) {
						banned = true;
						break;
					}
				}
				if (!banned)
					selectedRunes.get(rune.getSlot() - 1).add(rune);
			}
				
		}
		
		return selectedRunes;
	}
	
	public Monster getMonsterByName(String name) {
		for (Monster monster : this.monsters)
			if (monster.getName().equals(name))
				return monster;
		return null;
	}
	
	public Monster[] getMonsters() { return monsters; }
	public void setMonsters(Monster[] monsters) { this.monsters = monsters; }

	public Rune[] getRunes() { return runes; }
	public void setRunes(Rune[] runes) { this.runes = runes; }

	public StuffBuilder getStuffBuilder() { return stuffBuilder; }
	public void setStuffBuilder(StuffBuilder stuffBuilder) { this.stuffBuilder = stuffBuilder; }

	public Stuff[] getAllStuff() { return allStuff; }
	public void setAllStuff(Stuff[] allStuff) { this.allStuff = allStuff; }

	public RuneBuilder getRuneBuilder() { return runeBuilder; }
	public void setRuneBuilder(RuneBuilder runeBuilder) { this.runeBuilder = runeBuilder; }

	public MonsterBuilder getMonsterBuilder() { return monsterBuilder; }
	public void setMonsterBuilder(MonsterBuilder monsterBuilder) { this.monsterBuilder = monsterBuilder; }

	public TeamBuilder getTeamBuilder() { return teamBuilder; }
	public void setTeamBuilder(TeamBuilder teamBuilder) { this.teamBuilder = teamBuilder; }

	public Team[] getTeams() { return teams; }
	public void setTeams(Team[] teams) { this.teams = teams; }

	public List<Long> getBannedRunesUniqueId() { return bannedRunesUniqueId; }
	public void setBannedRunesUniqueId(List<Long> bannedRunesUniqueId) { this.bannedRunesUniqueId = bannedRunesUniqueId; }
}
