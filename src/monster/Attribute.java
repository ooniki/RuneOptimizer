package monster;

public enum Attribute {
	DARK,
	LIGHT,
	WIND,
	FIRE,
	WATER;
	
	public String getMarkup() {
		return name().toLowerCase();
	}
	
	public static Attribute fromMarkup(String markup) {
		for (Attribute entry : values()) {
			if (entry.getMarkup().equals(markup)) {
				return entry;
			}
		}
		return null;
	}
}
