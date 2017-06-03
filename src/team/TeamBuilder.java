package team;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import Rune.RuneSet;
import Rune.StatType;
import monster.Monster;
import profile.Profile;

public class TeamBuilder {

	public Team buildTeamFromJson(Profile profile, JSONObject teamJson) throws JSONException {
		Team team = new Team(teamJson.getString("name"));
		JSONArray monstersJson = teamJson.getJSONArray("monsters");
		for (int i = 0; i < monstersJson.length(); i++) {
			JSONObject monsterJson = monstersJson.getJSONObject(i);
			Monster monster = profile.getMonsterByName(monsterJson.getString("name"));
			if (monster != null) {
				TeamMate teamMate = new TeamMate(monster);
				teamMate.setBrokenSet(monsterJson.getBoolean("brokenSet"));
				JSONArray requiredStatsJson = monsterJson.getJSONArray("requiredStats");
				for (int j = 0; j < requiredStatsJson.length(); j++) {
					JSONObject requiredStatJson = requiredStatsJson.getJSONObject(j);
					teamMate.addRequiredStat(StatType.fromMarkup2(requiredStatJson.getString("type")) , requiredStatJson.getInt("value"));
				}
				JSONArray evalStatsJson = monsterJson.getJSONArray("evalStats");
				for (int j = 0; j < evalStatsJson.length(); j++) {
					JSONObject evalStatJson = evalStatsJson.getJSONObject(j);
					teamMate.addEvalStat(StatType.fromMarkup2(evalStatJson.getString("type")) , evalStatJson.getInt("value"));
				}
				JSONArray requiredSetsJson = monsterJson.getJSONArray("requiredSets");
				for (int j = 0; j < requiredSetsJson.length(); j++) {
					JSONObject requiredSetJson = requiredSetsJson.getJSONObject(j);
					teamMate.addRequiredSet(RuneSet.fromMarkup(requiredSetJson.getString("type")) , requiredSetJson.getInt("value"));
				}
				team.addTeamMate(teamMate);
			} else {
				System.err.println("MONSTER: " + monsterJson.getString("name") + " FROM " + teamJson.getString("name") + " TEAM NOT FOUND");
			}
			
		}
		
		return team;
	}
	
	public Team[] buildTeams(Profile profile, JSONArray teamsJson) throws Exception {
		Team[] teams = new Team[teamsJson.length()];
		
		for (int i = 0; i < teamsJson.length(); i++) {
			String teamPath = teamsJson.getString(i);
			JSONObject teamJson = Profile.getJsonFromFile(teamPath);
			teams[i] = this.buildTeamFromJson(profile, teamJson);
		}
		
//		Team[] teams = new Team[5];
//		teams[0] = this.buildGiantTeam(profile);
//		teams[1] = this.buildHwaTeam(profile);
//		teams[2] = this.buildRaoqTeam(profile);
//		teams[3] = this.buildKumarTeam(profile);
//		teams[4] = this.buildVigorTeam(profile);
		return teams;
	}
	
	public Team buildGiantTeam(Profile profile) {
		Team team = new Team("Giant");
		
		TeamMate orochi = new TeamMate(profile.getMonsterByName("Orochi"));
		orochi.addRequiredStat(StatType.HP, 16000);
		orochi.addRequiredStat(StatType.SPD, 170);
		orochi.addRequiredStat(StatType.ATK, 1200);
		orochi.addRequiredStat(StatType.ACC, 60);
		orochi.addRequiredStat(StatType.CRATE, 85);
		orochi.addRequiredStat(StatType.CDMG, 100);
		orochi.addEvalStat(StatType.ATK, 1);
		orochi.addEvalStat(StatType.SPD, 1);
		orochi.addEvalStat(StatType.CDMG, 1);
		team.addTeamMate(orochi);
		//orochi.setSelectedStuff(this.buildSelectedStuff(stuffs, orochi));
		
		TeamMate bastet = new TeamMate(profile.getMonsterByName("Bastet"));
		bastet.addRequiredStat(StatType.HP, 17000);
		bastet.addRequiredStat(StatType.SPD, 150);
		bastet.addRequiredStat(StatType.ACC, 50);
		bastet.addEvalStat(StatType.HP, 1);
		bastet.addEvalStat(StatType.SPD, 1);
		bastet.addRequiredSet(RuneSet.DESPAIR, 4);
		team.addTeamMate(bastet);
		
		TeamMate bernard = new TeamMate(profile.getMonsterByName("Bernard"));
		bernard.addRequiredStat(StatType.HP, 17000);
		bernard.addRequiredStat(StatType.SPD, 180);
		bernard.addRequiredStat(StatType.ACC, 60);
		bernard.addEvalStat(StatType.SPD, 1);
		team.addTeamMate(bernard);
		
		TeamMate belladeon = new TeamMate(profile.getMonsterByName("Belladeon"));
		belladeon.addRequiredStat(StatType.HP, 17000);
		belladeon.addRequiredStat(StatType.SPD, 170);
		belladeon.addRequiredStat(StatType.ACC, 60);
		belladeon.addEvalStat(StatType.SPD, 1);
		team.addTeamMate(belladeon);
		
		TeamMate veromos = new TeamMate(profile.getMonsterByName("Veromos"));
		veromos.addRequiredStat(StatType.HP, 20000);
		veromos.addRequiredStat(StatType.SPD, 170);
		veromos.addRequiredStat(StatType.ACC, 60);
		veromos.addEvalStat(StatType.HP, 1);
		veromos.addEvalStat(StatType.SPD, 1);
		team.addTeamMate(veromos);
		
		return team;
	}
	
	public Team buildHwaTeam(Profile profile) {
		Team team = new Team("Hwa");
		
		TeamMate monster = new TeamMate(profile.getMonsterByName("Hwa"));
		monster.addRequiredStat(StatType.HP, 15000);
		monster.addRequiredStat(StatType.SPD, 170);
		monster.addRequiredStat(StatType.ATK, 1100);
		monster.addRequiredStat(StatType.ACC, 30);
		monster.addRequiredStat(StatType.CRATE, 70);
//		monster.addRequiredStat(StatType.CDMG, 100);
		monster.addEvalStat(StatType.ATK, 1);
		monster.addEvalStat(StatType.SPD, 1);
		monster.addEvalStat(StatType.CDMG, 1);
		team.addTeamMate(monster);
		
		return team;
	}
	
	public Team buildRaoqTeam(Profile profile) {
		Team team = new Team("Raoq");
		
		TeamMate monster = new TeamMate(profile.getMonsterByName("Raoq"));
		monster.addRequiredStat(StatType.HP, 12000);
		monster.addRequiredStat(StatType.SPD, 140);
		monster.addRequiredStat(StatType.ATK, 1100);
		monster.addRequiredStat(StatType.ACC, 40);
		monster.addRequiredStat(StatType.CRATE, 60);
		monster.addRequiredStat(StatType.CDMG, 90);
		monster.addEvalStat(StatType.CDMG, 1);
		team.addTeamMate(monster);
		
		return team;
	}
	
	public Team buildKumarTeam(Profile profile) {
		Team team = new Team("Kumar");
		
		TeamMate monster = new TeamMate(profile.getMonsterByName("Kumar"));
		monster.addRequiredStat(StatType.HP, 15000);
		monster.addRequiredStat(StatType.SPD, 150);
		monster.addRequiredStat(StatType.ACC, 50);
		monster.addEvalStat(StatType.HP, 1);
		team.addTeamMate(monster);
		
		return team;
	}
	
	public Team buildVigorTeam(Profile profile) {
		Team team = new Team("Vigor");
		
		TeamMate monster = new TeamMate(profile.getMonsterByName("Vigor"));
		monster.addRequiredStat(StatType.HP, 15000);
		monster.addRequiredStat(StatType.SPD, 170);
		monster.addRequiredStat(StatType.ACC, 50);
		monster.addRequiredStat(StatType.CRATE, 60);
		monster.addRequiredStat(StatType.CDMG, 90);
		monster.addEvalStat(StatType.CDMG, 1);
		team.addTeamMate(monster);
		
		return team;
	}
	
}
