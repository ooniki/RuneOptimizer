package stuff;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import Rune.Rune;
import Rune.StatType;
import monster.Monster;
import monster.MonsterStats;

public class StuffedMonster {
	
	private Monster monster;
	private Stuff stuff;
	private MonsterStats bonusStats;
	private MonsterStats finalStats;
	
	private double eval = -1;
	
	public StuffedMonster(Monster monster, Stuff stuff, MonsterStats bonusStats, MonsterStats finalStats) {
		this.setMonster(monster);
		this.setStuff(stuff);
		this.setBonusStats(bonusStats);
		this.setFinalStats(finalStats);
	}

	public double eval(MonsterStats maxStats, Map<StatType, Integer> evalStats) {
		if (eval == -1) {
			eval = 0;
			if (evalStats.containsKey(StatType.HP))
				eval += (double)finalStats.getHp() / (double)maxStats.getHp();
			if (evalStats.containsKey(StatType.ATK))
				eval += (double)finalStats.getAtk() / (double)maxStats.getAtk();
			if (evalStats.containsKey(StatType.DEF))
				eval += (double)finalStats.getDef() /(double) maxStats.getDef();
			if (evalStats.containsKey(StatType.SPD))
				eval += (double)finalStats.getSpd() /(double) maxStats.getSpd();
			if (evalStats.containsKey(StatType.CRATE))
				eval += (double)finalStats.getCrate() / (double)maxStats.getCrate();
			if (evalStats.containsKey(StatType.CDMG))
				eval += (double)finalStats.getCdmg() / (double)maxStats.getCdmg();
			if (evalStats.containsKey(StatType.RES))
				eval += (double)finalStats.getRes() / (double)maxStats.getRes();
			if (evalStats.containsKey(StatType.ACC))
				eval += (double)finalStats.getAcc() / (double)maxStats.getAcc();
		}
		return eval;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.eval);
		return builder.toString();
	}
	
	public JSONObject toJSON() throws JSONException {
		JSONObject result = new JSONObject();
		result.put("name", this.getMonster().getName());
		result.put("stats", this.finalStats.toJSON());
		JSONArray runes = new JSONArray();
		for (Rune rune : this.stuff.getRunes()) {
			runes.put(rune.toJSON());
		}
		result.put("runes", runes);
		return result;
	}
	
	public Monster getMonster() { return monster; }
	public void setMonster(Monster monster) { this.monster = monster; }

	public MonsterStats getBonusStats() { return bonusStats; }
	public void setBonusStats(MonsterStats bonusStats) { this.bonusStats = bonusStats; }

	public MonsterStats getFinalStats() { return finalStats; }
	public void setFinalStats(MonsterStats finalStats) { this.finalStats = finalStats; }

	public Stuff getStuff() { return stuff; }
	public void setStuff(Stuff stuff) { this.stuff = stuff; }

	public double getEval() { return eval; }
	public void setEval(long eval) { this.eval = eval; }
	
}
