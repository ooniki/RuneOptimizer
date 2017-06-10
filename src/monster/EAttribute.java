package monster;

public enum EAttribute {
	DARK,
	LIGHT,
	WIND,
	FIRE,
	WATER;
	
	public String getMarkup() {
		return name().toLowerCase();
	}
	
	public static EAttribute fromMarkup(String markup) {
		for (EAttribute entry : values()) {
			if (entry.getMarkup().equals(markup)) {
				return entry;
			}
		}
		return null;
	}
}
