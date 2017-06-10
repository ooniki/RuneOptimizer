package team;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import Rune.ERuneSet;
import Rune.EStatType;
import Rune.Rune;
import Rune.Stat;
import logger.Logger;
import monster.Monster;
import monster.MonsterStats;
import profile.Profile;
import stuff.Stuff;
import stuff.StuffNode;
import stuff.StuffSelector;
import stuff.StuffedMonster;

public class TeamBuilder {

	private StuffSelector stuffSelector;
	
	public TeamBuilder() {
		this.stuffSelector = new StuffSelector();
	}
	
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
					teamMate.addRequiredStat(EStatType.fromMarkup2(requiredStatJson.getString("type")) , requiredStatJson.getInt("value"));
				}
				JSONArray evalStatsJson = monsterJson.getJSONArray("evalStats");
				for (int j = 0; j < evalStatsJson.length(); j++) {
					JSONObject evalStatJson = evalStatsJson.getJSONObject(j);
					teamMate.addEvalStat(EStatType.fromMarkup2(evalStatJson.getString("type")) , evalStatJson.getInt("value"));
				}
				JSONArray requiredSetsJson = monsterJson.getJSONArray("requiredSets");
				for (int j = 0; j < requiredSetsJson.length(); j++) {
					JSONObject requiredSetJson = requiredSetsJson.getJSONObject(j);
					teamMate.addRequiredSet(ERuneSet.fromMarkup(requiredSetJson.getString("type")) , requiredSetJson.getInt("value"));
				}
				team.addTeamMate(teamMate);
			} else {
				Logger.error(0, "MONSTER: " + monsterJson.getString("name") + " FROM " + teamJson.getString("name") + " TEAM NOT FOUND");
			}
			
		}
		return team;
	}
	
	private void computeBonus(int[] bonusOld, int[] bonusNew, Rune rune) {
		for (int i = 0; i < bonusOld.length; i++) {
			bonusNew[i] = bonusOld[i];	
		}
		for (Stat stat : rune.getStats()) {
			bonusNew[stat.getType().ordinal()] += stat.getValue();
		}
	}
	
	public void buildTeamStuffs(Team team, List<List<Rune>> selectedRunes) {		
		long totalNbStuffs = selectedRunes.get(0).size() * selectedRunes.get(1).size() * selectedRunes.get(2).size() * selectedRunes.get(3).size() * selectedRunes.get(4).size() * selectedRunes.get(5).size();
		int currentNbStuff = 0;
		long currentPourcent = -1;

		Map<TeamMate, MonsterStats> maxStats = new HashMap<TeamMate, MonsterStats>();
		Map<TeamMate, int[]> failures = new HashMap<TeamMate, int[]>();
		Map<TeamMate, List<StuffedMonster>> preSelectedsRunes = new HashMap<TeamMate, List<StuffedMonster>>();
		for (TeamMate teamMate : team.getTeamMates()) {
			preSelectedsRunes.put(teamMate, new ArrayList<StuffedMonster>());
			maxStats.put(teamMate, new MonsterStats(0, 0, 0, 0, 0, 0, 0, 0));
			failures.put(teamMate, new int[9]);
		}
		
		int[] bonusBase = new int[EStatType.values().length];
		int[] bonus1 = new int[EStatType.values().length];
		int[] bonus2 = new int[EStatType.values().length];
		int[] bonus3 = new int[EStatType.values().length];
		int[] bonus4 = new int[EStatType.values().length];
		int[] bonus5 = new int[EStatType.values().length];
		for (Rune rune1 : selectedRunes.get(0)) {
			this.computeBonus(bonusBase, bonus1, rune1);
			for (Rune rune2 : selectedRunes.get(1)) {
				this.computeBonus(bonus1, bonus2, rune2);
				for (Rune rune3 : selectedRunes.get(2)) {
					this.computeBonus(bonus2, bonus3, rune3);
					for (Rune rune4 : selectedRunes.get(3)) {
						this.computeBonus(bonus3, bonus4, rune4);
						for (Rune rune5 : selectedRunes.get(4)) {
							this.computeBonus(bonus4, bonus5, rune5);
							for (Rune rune6 : selectedRunes.get(5)) {
								int[] bonusFinal = new int[EStatType.values().length];
								this.computeBonus(bonus5, bonusFinal, rune6);
								Stuff stuff = new Stuff(rune1, rune2, rune3, rune4, rune5, rune6, bonusFinal);
								for (TeamMate teamMate : team.getTeamMates()) {
									StuffedMonster stuffedMonster = this.stuffSelector.goodStuff(stuff, teamMate.getMonster(), teamMate.getRequiredStats(), teamMate.getRequiredSets(), teamMate.isBrokenSet(), maxStats.get(teamMate), failures.get(teamMate));
									if (stuffedMonster != null) {
										preSelectedsRunes.get(teamMate).add(stuffedMonster);
									}	
								}
								currentNbStuff++;
							}
						}	
					}
						
				}
					
			}
				
			long pourcent = ((long)currentNbStuff) * 100 / totalNbStuffs;
			if (pourcent != currentPourcent) {
				currentPourcent = pourcent;
				System.out.println(currentPourcent + "% BuildStuff");
			}
		}		
		
		for (TeamMate teamMate : team.getTeamMates()) {
			PriorityQueue<StuffedMonster>  selecteds = new PriorityQueue<StuffedMonster>(new Comparator<StuffedMonster>() {

				@Override
				public int compare(StuffedMonster o1, StuffedMonster o2) {
					if (o2.eval(maxStats.get(teamMate), teamMate.getEvalStats()) < o1.eval(maxStats.get(teamMate), teamMate.getEvalStats()))
						return -1;
					else
						return 1;
				}
			});
			
			for (StuffedMonster selected : preSelectedsRunes.get(teamMate)) {
				selecteds.add(selected);
			}
			
			
			int selectedsSize = selecteds.size() > 16000 ? 16000 : selecteds.size();
			StuffedMonster[] selectedsArray = new StuffedMonster[selectedsSize];
			for (int i = 0; i < selectedsSize; i++) {
				selectedsArray[i] = selecteds.poll();
			}

			if (selectedsArray.length == 0) {
				System.err.println(teamMate.getMonster().getName() + " NO RUNAGE FOUND");
				System.err.println("Failures: " + this.failuresToString(failures.get(teamMate)));
				
			}
			teamMate.setSelectedStuff(selectedsArray);
		}
	}
	
	private String failuresToString(int[] failures) {
		StringBuilder builder = new StringBuilder();
		builder.append("HP: ").append(failures[0]);
		builder.append(" ATK: ").append(failures[1]);
		builder.append(" DEF: ").append(failures[2]);
		builder.append(" SPD: ").append(failures[3]);
		builder.append(" CRATE: ").append(failures[4]);
		builder.append(" CDMG: ").append(failures[5]);
		builder.append(" RES: ").append(failures[6]);
		builder.append(" ACC: ").append(failures[7]);
		builder.append(" SET: ").append(failures[8]);
		return builder.toString();
	}
	
	public List<StuffNode> selectStuffsForTeam(Team team) {
		return this.stuffSelector.selectStuffsForTeam(team);
	}	
}
