package monster;

public class Monster {

	private long masterId;
	private long id;
	
	private String name;
	private Attribute attribute;
	
	private int stars;
	private int level;
	
	private MonsterStats monsterStats;
	
	public Monster(long masterId, long id, String name, Attribute attribute, int stars, int level, MonsterStats monsterStats) {
		this.setMasterId(masterId);
		this.setId(id);
		this.setName(name);
		this.setAttribute(attribute);
		this.setStars(stars);
		this.setLevel(level);
		this.setMonsterStats(monsterStats);
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.name);
		return builder.toString();
	}
	
	public long getMasterId() { return masterId; }
	public void setMasterId(long masterId) { this.masterId = masterId; }

	public long getId() { return id; }
	public void setId(long id) { this.id = id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public Attribute getAttribute() { return attribute; }
	public void setAttribute(Attribute attribute) { this.attribute = attribute; }

	public int getStars() { return stars; }
	public void setStars(int stars) { this.stars = stars; }

	public int getLevel() { return level; }
	public void setLevel(int level) { this.level = level; }

	public MonsterStats getMonsterStats() { return monsterStats; }
	public void setMonsterStats(MonsterStats monsterStats) { this.monsterStats = monsterStats; }
	
}
