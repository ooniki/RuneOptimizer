package Rune;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class RuneBuilder {

	public Stat buildStat(JSONObject obj, String prefix, StatPos type) throws JSONException {
		String typeString = obj.getString(prefix + "_t");
		StatType statType = StatType.fromMarkup(typeString);
		if (statType == null)
			return null;
		int statValue = obj.getInt(prefix + "_v");
		boolean enchanted = false;
		int enchantValue = 0;
		if (obj.has(prefix + "_data")) {
			JSONObject enchantJson = obj.getJSONObject(prefix + "_data");
			if (enchantJson.has("enchanted")) {
				enchanted = enchantJson.getBoolean("enchanted");
				enchantValue = enchantJson.getInt("gvalue");
			}
		}
		
		Stat stat =  new Stat(statType, statValue, type, enchanted, enchantValue);
		return stat;
	}
	
	public Stat[] buildStats(JSONObject obj) throws JSONException {
		int nbStats = 0;		
		
		Stat mainStat  = this.buildStat(obj, "m", StatPos.MAIN);
		nbStats++;
		Stat subMainStat  = this.buildStat(obj, "i", StatPos.SUBMAIN);
		if (subMainStat != null)
			nbStats++;
		Stat s1Stat  = this.buildStat(obj, "s1", StatPos.SUB1);
		if (s1Stat != null)
			nbStats++;
		Stat s2Stat  = this.buildStat(obj, "s2", StatPos.SUB2);
		if (s2Stat != null)
			nbStats++;
		Stat s3Stat  = this.buildStat(obj, "s3", StatPos.SUB3);
		if (s3Stat != null)
			nbStats++;
		Stat s4Stat  = this.buildStat(obj, "s4", StatPos.SUB4);
		if (s4Stat != null)
			nbStats++;
		
		Stat[] stats = new Stat[nbStats];
		int statPos = 0;
		stats[statPos] = mainStat;
		statPos++;
		if (subMainStat != null) {
			stats[statPos] = subMainStat;
			statPos++;
		}
		if (s1Stat != null) {
			stats[statPos] = s1Stat;
			statPos++;
		}
		if (s2Stat != null) {
			stats[statPos] = s2Stat;
			statPos++;
		}
		if (s3Stat != null) {
			stats[statPos] = s3Stat;
			statPos++;
		}
		if (s4Stat != null) {
			stats[statPos] = s4Stat;
			statPos++;
		}			
		
		return stats;
	}
	
	public Rune buildRune(JSONObject obj) {
		try {
			long id = obj.getLong("id");
			long uniqueId = obj.getLong("unique_id");
			String set = obj.getString("set");
			RuneSet runeSet = RuneSet.fromMarkup(set.toLowerCase());
			int stars = obj.getInt("grade");
			int level = obj.getInt("level");
			int slot = obj.getInt("slot");
			String monster = obj.getString("monster_n");
			
			Stat[] stats = this.buildStats(obj);
			
			Rune rune = new Rune(id, uniqueId, runeSet, stars, level, slot, monster, stats);
			
			return rune;
		} catch (JSONException e) {
			e.printStackTrace();
		}	
		return null;
	}
	
	public Rune[] buildRunes(JSONArray runesJson) throws JSONException {
		Rune[] runes = new Rune[runesJson.length()];
		for (int i = 0; i < runesJson.length(); i++) {
			JSONObject obj = runesJson.getJSONObject(i);
			runes[i] = this.buildRune(obj);
		};
		return runes;
	}
}
