package profile;
import java.util.ArrayList;
import java.util.List;

import Rune.Rune;
import monster.Monster;
import stuff.Stuff;
import team.Team;

public class Profile {
	
	private Monster[] monsters;
	private Rune[] runes;
	private Stuff[] allStuff;
	private Team[] teams;
	
	private List<Long> bannedRunesUniqueId;
	
	public Profile() {
		this.setMonsters(new Monster[0]);
		this.setRunes(new Rune[0]);
		this.setAllStuff(new Stuff[0]);
		this.setTeams(new Team[0]);
		this.setBannedRunesUniqueId(new ArrayList<Long>());
	}	
	
	public void addTeam(Team team) {
		Team[] newTeams = new Team[this.teams.length + 1];
		for (int i = 0; i < this.teams.length; i++)
			newTeams[i] = this.teams[i];
		newTeams[this.teams.length] = team;
		this.teams = newTeams;
	}
	
	public void printAllRunes() {
		for (Rune rune : this.getRunes()) {
			System.out.println(rune.toString());
		}
	}
	
	public Monster getMonsterByName(String name) {
		for (Monster monster : this.monsters)
			if (monster.getName().equals(name))
				return monster;
		return null;
	}
	
	public Team getTeamByName(String name) {
		name = name.toLowerCase();
		for (Team teams : this.teams)
			if (teams.getName().toLowerCase().equals(name))
				return teams;
		return null;
	}
	
	public Monster[] getMonsters() { return monsters; }
	public void setMonsters(Monster[] monsters) { this.monsters = monsters; }

	public Rune[] getRunes() { return runes; }
	public void setRunes(Rune[] runes) { this.runes = runes; }

	public Stuff[] getAllStuff() { return allStuff; }
	public void setAllStuff(Stuff[] allStuff) { this.allStuff = allStuff; }

	public Team[] getTeams() { return teams; }
	public void setTeams(Team[] teams) { this.teams = teams; }

	public List<Long> getBannedRunesUniqueId() { return bannedRunesUniqueId; }
	public void setBannedRunesUniqueId(List<Long> bannedRunesUniqueId) { this.bannedRunesUniqueId = bannedRunesUniqueId; }
}
