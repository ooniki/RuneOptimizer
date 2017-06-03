package stuff;

import java.util.List;

import Rune.Rune;
import Rune.Stat;
import Rune.StatType;

public class StuffBuilder {

	private void computeBonus(int[] bonusOld, int[] bonusNew, Rune rune) {
		for (int i = 0; i < bonusOld.length; i++) {
			bonusNew[i] = bonusOld[i];	
		}
		for (Stat stat : rune.getStats()) {
			bonusNew[stat.getType().ordinal()] += stat.getValue();
		}
	}
	
	public Stuff[] buildStuffs(List<List<Rune>> selectedRunes) {
		long totalNbStuffs = selectedRunes.get(0).size() * selectedRunes.get(1).size() * selectedRunes.get(2).size() * selectedRunes.get(3).size() * selectedRunes.get(4).size() * selectedRunes.get(5).size();
		Stuff[] stuffs = new Stuff[(int)totalNbStuffs];
		int currentNbStuff = 0;
		long currentPourcent = -1;
		
		int[] bonusBase = new int[StatType.values().length];
		int[] bonus1 = new int[StatType.values().length];
		int[] bonus2 = new int[StatType.values().length];
		int[] bonus3 = new int[StatType.values().length];
		int[] bonus4 = new int[StatType.values().length];
		int[] bonus5 = new int[StatType.values().length];
		for (Rune rune1 : selectedRunes.get(0)) {
			this.computeBonus(bonusBase, bonus1, rune1);
			for (Rune rune2 : selectedRunes.get(1)) {
				this.computeBonus(bonus1, bonus2, rune2);
				for (Rune rune3 : selectedRunes.get(2)) {
					this.computeBonus(bonus2, bonus3, rune3);
					for (Rune rune4 : selectedRunes.get(3)) {
						this.computeBonus(bonus3, bonus4, rune4);
						for (Rune rune5 : selectedRunes.get(4)) {
							this.computeBonus(bonus4, bonus5, rune5);
							for (Rune rune6 : selectedRunes.get(5)) {
								int[] bonusFinal = new int[StatType.values().length];
								this.computeBonus(bonus5, bonusFinal, rune6);
								Stuff stuff = new Stuff(rune1, rune2, rune3, rune4, rune5, rune6, bonusFinal);
								stuffs[currentNbStuff] = stuff;
								currentNbStuff++;
							}
						}
							
					}
						
				}
					
			}
				
			long pourcent = ((long)currentNbStuff) * 100 / totalNbStuffs;
			if (pourcent != currentPourcent) {
				currentPourcent = pourcent;
				System.out.println(currentPourcent + "% BuildStuff");
			}
		}
			
		return stuffs;
	}
	
}
