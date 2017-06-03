package Rune;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Rune {

	private long id;
	private long uniqueId;
	private RuneSet set;
	private int stars;
	private int level;
	private int slot;
	private String monster;
	
	private Stat[] stats;

	
	public Rune(long id, long uniqueId, RuneSet set, int stars, int level, int slot, String monster, Stat[] stats) {
		this.setId(id);
		this.setUniqueId(uniqueId);
		this.setSet(set);
		this.setStars(stars);
		this.setLevel(level);
		this.setSlot(slot);
		this.setMonster(monster);
		this.setStats(stats);
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("id: ").append(id).append("\n");
		builder.append("uniqueId: ").append(uniqueId).append("\n");
		builder.append("set: ").append(set).append("\n");
		builder.append("stars: ").append(stars).append("\n");
		builder.append("level: ").append(level).append("\n");
		builder.append("slot: ").append(slot).append("\n");
		for (Stat stat : this.stats) {
			builder.append(stat).append("\n");
		}
		builder.append("location: ");
		if (this.monster.equals("Unknown name"))
			builder.append("storage").append("\n");
		else
			builder.append(this.monster).append("\n");
		return builder.toString();
	}
	
	public JSONObject toJSON() throws JSONException {
		JSONObject result = new JSONObject();
		result.put("stars", this.stars);
		result.put("level", this.level);
		result.put("slot", this.slot);
		result.put("set", this.set);
		if (this.monster.equals("Unknown name"))
			result.put("location", "storage");
		else
			result.put("location", this.monster);
		JSONArray stats = new JSONArray(); 
		for (Stat stat : this.stats) {
			stats.put(stat.toJSON());
		}
		result.put("stats", stats);
		return result;
	}
	
	public long getId() { return id; }
	public void setId(long id) { this.id = id; }

	public long getUniqueId() { return uniqueId; }
	public void setUniqueId(long uniqueId) { this.uniqueId = uniqueId; }

	public RuneSet getSet() { return set; }
	public void setSet(RuneSet set) { this.set = set; }

	public int getStars() { return stars; }
	public void setStars(int stars) { this.stars = stars; }

	public int getLevel() { return level; }
	public void setLevel(int level) { this.level = level; }

	public Stat[] getStats() { return stats; }
	public void setStats(Stat[] stats) { this.stats = stats; }

	public int getSlot() { return slot; }
	public void setSlot(int slot) { this.slot = slot; }

	public String getMonster() { return monster; }
	public void setMonster(String monster) { this.monster = monster; }
	
}
