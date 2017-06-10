package profile;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import Rune.Rune;
import Rune.RuneBuilder;
import monster.Monster;
import monster.MonsterBuilder;
import parser.JSONFileReader;
import stuff.StuffNode;
import team.Team;
import team.TeamBuilder;

public class ProfileManager {

	private MonsterBuilder monsterBuilder;
	private RuneBuilder runeBuilder;
	private TeamBuilder teamBuilder;
	
	public ProfileManager() {
		this.setMonsterBuilder(new MonsterBuilder());
		this.setRuneBuilder(new RuneBuilder());
		this.setTeamBuilder(new TeamBuilder());
	}
	
	public void loadFromConfigJson(Profile profile, JSONObject json) throws Exception {
		JSONObject dataJson = JSONFileReader.getJsonFromFile(json.getString("data"));
		JSONArray teamsJson = json.getJSONArray("teams");
		JSONArray blockedMonstersJson = json.getJSONArray("blockedMonsters");
		
		this.loadMonstersFromJson(profile, dataJson);
		this.loadRunesFromJson(profile, dataJson);
		this.loadTeamsFromPaths(profile, teamsJson);
		this.loadBannedRunes(profile, blockedMonstersJson);
	}
	
	public void loadRunesFromJson(Profile profile, JSONObject json) throws JSONException {
		JSONArray runesJson = json.getJSONArray("runes");
		Rune[] runes = this.runeBuilder.buildRunes(runesJson);
		profile.setRunes(runes);
	}
	
	public void loadMonstersFromJson(Profile profile, JSONObject json) throws JSONException {
		JSONArray monsterJson = json.getJSONArray("mons");
		Monster[] monsters = this.monsterBuilder.buildMonsters(monsterJson);
		profile.setMonsters(monsters);
		
	}
	
	public void loadTeamFromJson(Profile profile, JSONObject json) throws JSONException {
		Team team = this.teamBuilder.buildTeamFromJson(profile, json);
		profile.addTeam(team);
	}
	
	public void loadTeamsFromPaths(Profile profile, JSONArray json) throws Exception {
		Team[] teams = new Team[json.length()];
		for (int i = 0; i < json.length(); i++) {
			String teamPath = json.getString(i);
			JSONObject teamJson = JSONFileReader.getJsonFromFile(teamPath);
			teams[i] = this.teamBuilder.buildTeamFromJson(profile, teamJson);
		}
		profile.setTeams(teams);
	}
	
	public void loadBannedRunes(Profile profile, JSONArray json) throws JSONException {
		List<Long> bannedUniqueId = new ArrayList<Long>();
		for (int i = 0; i < json.length(); i++) {
			for (Rune rune : profile.getRunes()) {
				if (rune.getMonster().equals(json.getString(i))) {
					bannedUniqueId.add(rune.getUniqueId());
				}
			}
		}
		profile.setBannedRunesUniqueId(bannedUniqueId);
	}
	
	public List<List<Rune>> getSortedRunes(Profile profile, int stars, int level) {
		List<List<Rune>> selectedRunes = new ArrayList<List<Rune>>();
		selectedRunes.add(new ArrayList<Rune>());
		selectedRunes.add(new ArrayList<Rune>());
		selectedRunes.add(new ArrayList<Rune>());
		selectedRunes.add(new ArrayList<Rune>());
		selectedRunes.add(new ArrayList<Rune>());
		selectedRunes.add(new ArrayList<Rune>());
		
		for (Rune rune : profile.getRunes()) {
			if (rune.getLevel() >= level && rune.getStars() >= stars) {
				long id = rune.getUniqueId();
				boolean banned = false;
				for (long bannedRune : profile.getBannedRunesUniqueId()) {
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
	
	public List<StuffNode> buildTeam(Profile profile, Team team) {
		this.teamBuilder.buildTeamStuffs(team, this.getSortedRunes(profile, 5, 9));
		List<StuffNode> stuffsNode = this.teamBuilder.selectStuffsForTeam(team);
		return stuffsNode;
	}	

	public RuneBuilder getRuneBuilder() { return runeBuilder; }
	public void setRuneBuilder(RuneBuilder runeBuilder) { this.runeBuilder = runeBuilder; }

	public MonsterBuilder getMonsterBuilder() { return monsterBuilder; }
	public void setMonsterBuilder(MonsterBuilder monsterBuilder) { this.monsterBuilder = monsterBuilder; }

	public TeamBuilder getTeamBuilder() { return teamBuilder; }
	public void setTeamBuilder(TeamBuilder teamBuilder) { this.teamBuilder = teamBuilder; }
}
