package Rune;
public enum RuneSet {
	ENERGY,
	FATAL,
	BLADE,
	SWIFT,
	FOCUS,
	DESPAIR,
	VIOLENT,
	SHIELD,
	WILL,
	RAGE,
	REVENGE,
	NEMESIS,
	ENDURE,
	VAMPIRE,
	GUARD,
	DESTROY,
	FIGHT,
	DETERMINATION,
	ENHANCE,
	ACCURACY,
	TOLERANCE;
	
	public String getMarkup() {
		return name().toLowerCase();
	}
	
	public static RuneSet fromMarkup(String markup) {
		markup = markup.toLowerCase();
		for (RuneSet entry : values()) {
			if (entry.getMarkup().equals(markup)) {
				return entry;
			}
		}
		return null;
	}
}
