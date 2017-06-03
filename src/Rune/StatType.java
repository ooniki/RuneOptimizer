package Rune;

public enum StatType {
	SPD,
	ATK,
	ATK_FLAT,
	HP,
	HP_FLAT,
	DEF,
	DEF_FLAT,
	CDMG,
	CRATE,
	ACC,
	RES;
	
	public static StatType fromMarkup(String markup) {
		switch (markup) {
		case "SPD":
			return StatType.SPD;
		case "ATK%":
			return StatType.ATK;
		case "ATK flat":
			return StatType.ATK_FLAT;
		case "HP%":
			return StatType.HP;
		case "HP flat":
			return StatType.HP_FLAT;
		case "DEF%":
			return StatType.DEF;
		case "DEF flat":
			return StatType.DEF_FLAT;
		case "CDmg":
			return StatType.CDMG;
		case "CRate":
			return StatType.CRATE;
		case "ACC":
			return StatType.ACC;
		case "RES":
			return StatType.RES;
		case "":
			return null;
		default:
			System.err.println("UNKNOW STAT TYPE:" + markup);
			return null;
		}
	}
	
	public static StatType fromMarkup2(String markup) {
		switch (markup) {
		case "SPD":
			return StatType.SPD;
		case "ATK":
			return StatType.ATK;
		case "HP":
			return StatType.HP;
		case "DEF":
			return StatType.DEF;
		case "CDMG":
			return StatType.CDMG;
		case "CRATE":
			return StatType.CRATE;
		case "ACC":
			return StatType.ACC;
		case "RES":
			return StatType.RES;
		case "":
			return null;
		default:
			System.err.println("UNKNOW STAT TYPE:" + markup);
			return null;
		}
	}
}
