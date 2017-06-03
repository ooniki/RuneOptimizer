package team;
import java.util.HashMap;
import java.util.Map;

import Rune.RuneSet;
import Rune.StatType;
import monster.Monster;
import stuff.StuffedMonster;

public class TeamMate {

	private Map<StatType, Integer> requiredStats;
	private Map<StatType, Integer> evalStats;
	private Map<RuneSet, Integer> requiredSets;
	private Monster monster;
	private boolean brokenSet;
	
	private StuffedMonster[] selectedStuff;
	
	public TeamMate(Monster monster) {
		this.setRequiredStats(new HashMap<StatType, Integer>());
		this.setEvalStats(new HashMap<StatType, Integer>());
		this.setSelectedStuff(new StuffedMonster[0]);
		this.setRequiredSets(new HashMap<RuneSet, Integer>());
		this.setMonster(monster);
		this.setBrokenSet(true);
	}

	public void addRequiredStat(StatType type, int value) {
		this.requiredStats.put(type, value);
	}
	
	public void addEvalStat(StatType type, int value) {
		this.evalStats.put(type, value);
	}
	
	public void addRequiredSet(RuneSet type, int value) {
		this.requiredSets.put(type, value);
	}
	
	public Map<StatType, Integer> getRequiredStats() { return requiredStats; }
	public void setRequiredStats(Map<StatType, Integer> requiredStats) { this.requiredStats = requiredStats; }

	public Monster getMonster() { return monster; }
	public void setMonster(Monster monster) { this.monster = monster; }

	public StuffedMonster[] getSelectedStuff() { return selectedStuff; }
	public void setSelectedStuff(StuffedMonster[] selectedStuff) { this.selectedStuff = selectedStuff; }

	public Map<StatType, Integer> getEvalStats() { return evalStats; }
	public void setEvalStats(Map<StatType, Integer> evalStats) { this.evalStats = evalStats; }

	public Map<RuneSet, Integer> getRequiredSets() { return requiredSets; }
	public void setRequiredSets(Map<RuneSet, Integer> requiredSets) { this.requiredSets = requiredSets; }

	public boolean isBrokenSet() { return brokenSet; }
	public void setBrokenSet(boolean brokenSet) { this.brokenSet = brokenSet; }
	
}
