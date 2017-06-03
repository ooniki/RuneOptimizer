package Rune;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Stat {

	private StatType type;
	private int value;
	private StatPos pos;
	
	private boolean enchanted;
	private int enchantValue;

	public Stat(StatType type, int value, StatPos pos, boolean enchanted, int enchantValue) {
		this.setType(type);
		this.setValue(value);
		this.setPos(pos);
		this.setEnchanted(enchanted);
		this.setEnchantValue(enchantValue);
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("pos: ").append(pos);
		builder.append(", type: ").append(type);
		builder.append(", value: ").append(value);
		if (!(pos == StatPos.MAIN || pos == StatPos.SUBMAIN)) {
			builder.append(", enchanted: ").append(enchanted);
			builder.append(", enchantValue: ").append(enchantValue);
		}
		return builder.toString();
	}
	
	public JSONObject toJSON() throws JSONException {
		JSONObject result = new JSONObject();
		result.put("type", this.type);
		result.put("value", this.value);
		result.put("pos", this.pos);
		return result;
	}
	
	public StatType getType() { return type; }
	public void setType(StatType type) { this.type = type; }

	public int getValue() { return value; }
	public void setValue(int value) { this.value = value; }

	public StatPos getPos() { return pos; }
	public void setPos(StatPos pos) { this.pos = pos; }
	
	public boolean isEnchanted() { return enchanted; }
	public void setEnchanted(boolean enchanted) { this.enchanted = enchanted; }

	public int getEnchantValue() { return enchantValue; }
	public void setEnchantValue(int enchantValue) { this.enchantValue = enchantValue; }
	
}
