package stuff;
import Rune.Rune;
import Rune.RuneSet;
import Rune.StatType;

public class Stuff {

	private Rune[] runes;
	private int[] bonus;
	private char[] set;
	
	public Stuff(Rune rune1, Rune rune2, Rune rune3, Rune rune4, Rune rune5, Rune rune6, int[] bonus) {
		this.setRunes(new Rune[6]);
		this.setBonus(bonus);
		this.setSet(new char[RuneSet.values().length]);
		this.runes[0] = rune1;
		this.set[rune1.getSet().ordinal()]++;
		this.runes[1] = rune2;
		this.set[rune2.getSet().ordinal()]++;
		this.runes[2] = rune3;
		this.set[rune3.getSet().ordinal()]++;
		this.runes[3] = rune4;
		this.set[rune4.getSet().ordinal()]++;
		this.runes[4] = rune5;
		this.set[rune5.getSet().ordinal()]++;
		this.runes[5] = rune6;
		this.set[rune6.getSet().ordinal()]++;
	}
	
	public int computeBonusHp(int baseHp) {
		int bonusBySet = (int) (baseHp * (this.set[RuneSet.ENERGY.ordinal()] / 2 * 0.15));
		return this.bonus[StatType.HP_FLAT.ordinal()]  + (baseHp * this.bonus[StatType.HP.ordinal()] / 100) + bonusBySet;
	}
	
	public int computeBonusAtk(int baseAtk) {
		int bonusBySet = (int) (baseAtk * (this.set[RuneSet.FATAL.ordinal()] / 4 * 0.35));
		return this.bonus[StatType.ATK_FLAT.ordinal()]  + (baseAtk * this.bonus[StatType.ATK.ordinal()] / 100) + bonusBySet;
	} 
	
	public int computeBonusDef(int baseDef) {
		int bonusBySet = (int) (baseDef * (this.set[RuneSet.GUARD.ordinal()] / 2 * 0.15));
		return this.bonus[StatType.DEF_FLAT.ordinal()]  + (baseDef * this.bonus[StatType.DEF.ordinal()] / 100) + bonusBySet;
	}
	
	public int computeSpd(int baseSpd) {
		int bonusBySet = (int) (baseSpd * (this.set[RuneSet.SWIFT.ordinal()] / 4) * 0.25);
		return this.bonus[StatType.SPD.ordinal()] + bonusBySet;
	}
	
	public int computeCrate() {
		return this.bonus[StatType.CRATE.ordinal()] + ((this.set[RuneSet.BLADE.ordinal()] / 2) * 12);
	}
	
	public int computeCdmg() {
		return this.bonus[StatType.CDMG.ordinal()] + ((this.set[RuneSet.RAGE.ordinal()] / 4) * 40);
	}
	
	public int computeRes() {
		return this.bonus[StatType.RES.ordinal()] + ((this.set[RuneSet.ENDURE.ordinal()] / 2) * 20);
	}
	
	public int computeAcc() {
		return this.bonus[StatType.ACC.ordinal()] + ((this.set[RuneSet.FOCUS.ordinal()] / 2) * 20);
	}
	
	public Rune[] getRunes() { return runes; }
	public void setRunes(Rune[] runes) { this.runes = runes; }

	public int[] getBonus() { return bonus; }
	public void setBonus(int[] bonus) { this.bonus = bonus; }

	public char[] getSet() { return set; }
	public void setSet(char[] set) { this.set = set; }
}
