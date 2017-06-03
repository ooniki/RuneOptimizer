import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Map.Entry;

import Rune.Rune;
import Rune.RuneSet;
import Rune.StatType;
import monster.Monster;
import monster.MonsterStats;
import stuff.Stuff;
import stuff.StuffNode;
import stuff.StuffedMonster;
import team.Team;
import team.TeamMate;

public class Optimizer {

	private MonsterStats maxStats;
	
	private int[] failures;
	
	public boolean checkNoBrokenSet(Stuff stuff) {
		for (RuneSet runeset : RuneSet.values()) {
			if (runeset == RuneSet.SWIFT || runeset == RuneSet.FATAL || runeset == RuneSet.DESPAIR || runeset == RuneSet.VIOLENT || runeset == RuneSet.VAMPIRE) {
				if (stuff.getSet()[runeset.ordinal()] % 4 != 0)
					return false;
			} else {
				if (stuff.getSet()[runeset.ordinal()] % 2 != 0)
					return false;
			}
		}
		return true;
	}
	
	private String failuresToString(int[] failures) {
		StringBuilder builder = new StringBuilder();
		builder.append("HP: ").append(failures[0]);
		builder.append(" ATK: ").append(failures[1]);
		builder.append(" DEF: ").append(failures[2]);
		builder.append(" SPD: ").append(failures[3]);
		builder.append(" CRATE: ").append(failures[4]);
		builder.append(" CDMG: ").append(failures[5]);
		builder.append(" RES: ").append(failures[6]);
		builder.append(" ACC: ").append(failures[7]);
		builder.append(" SET: ").append(failures[8]);
		return builder.toString();
	}
	
	public StuffedMonster goodStuff(Stuff stuff, Monster monster, Set<Map.Entry<StatType,Integer>> requiredStatsEntrySet, Set<Map.Entry<RuneSet,Integer>> requiredSetsEntrySet, boolean brokenSet) {
		MonsterStats statsBonus = new MonsterStats(-1, -1, -1, -1, -1, -1, -1, -1);
		MonsterStats finalStats = new MonsterStats(0, 0, 0, 0, 0, 0, 0, 0);
		
		if (!brokenSet && !this.checkNoBrokenSet(stuff)) {
			this.failures[8]++;
			return null;
		}
			
		
		for (Entry<RuneSet, Integer> set : requiredSetsEntrySet) {
			if (!(stuff.getSet()[set.getKey().ordinal()] >= set.getValue())) {
				this.failures[8]++;
				return null;
			}	
		}
		
		for (Entry<StatType, Integer> requiredStat : requiredStatsEntrySet) {
			StatType type = requiredStat.getKey();
			int value = requiredStat.getValue();
			
			if (type == StatType.HP) {
				statsBonus.setHp(stuff.computeBonusHp(monster.getMonsterStats().getHp()));
				finalStats.setHp(statsBonus.getHp() + monster.getMonsterStats().getHp());
				if (finalStats.getHp() < value) {
					this.failures[0]++;
					return null;
				}
					
			} else if (type == StatType.ATK) {
				statsBonus.setAtk(stuff.computeBonusAtk(monster.getMonsterStats().getAtk()));
				finalStats.setAtk(statsBonus.getAtk() + monster.getMonsterStats().getAtk());
				if (finalStats.getAtk() < value){
					this.failures[1]++;
					return null;
				}
			} else if (type == StatType.DEF) {
				statsBonus.setDef(stuff.computeBonusDef(monster.getMonsterStats().getDef()));
				finalStats.setDef(statsBonus.getDef() + monster.getMonsterStats().getDef());
				if (finalStats.getDef() < value){
					this.failures[2]++;
					return null;
				}
			} else if (type == StatType.SPD) {
				statsBonus.setSpd(stuff.computeSpd(monster.getMonsterStats().getSpd()));
				finalStats.setSpd(statsBonus.getSpd() + monster.getMonsterStats().getSpd());
				if (finalStats.getSpd() < value){
					this.failures[3]++;
					return null;
				}
			} else if (type == StatType.CRATE) {
				statsBonus.setCrate(stuff.computeCrate());
				finalStats.setCrate(statsBonus.getCrate() + monster.getMonsterStats().getCrate());
				if (finalStats.getCrate() < value){
					this.failures[4]++;
					return null;
				}
			} else if (type == StatType.CDMG) {
				statsBonus.setCdmg(stuff.computeCdmg());
				finalStats.setCdmg(statsBonus.getCdmg() + monster.getMonsterStats().getCdmg());
				if (finalStats.getCdmg() < value){
					this.failures[5]++;
					return null;
				}
			} else if (type == StatType.RES) {
				statsBonus.setRes(stuff.computeRes());
				finalStats.setRes(statsBonus.getRes() + monster.getMonsterStats().getRes());
				if (finalStats.getRes() < value){
					this.failures[6]++;
					return null;
				}
			} else if (type == StatType.ACC) {
				statsBonus.setAcc(stuff.computeAcc());
				finalStats.setAcc(statsBonus.getAcc() + monster.getMonsterStats().getAcc());
				if (finalStats.getAcc() < value){
					this.failures[7]++;
					return null;
				}
			}
		}
		
		if (statsBonus.getHp() == -1) {
			statsBonus.setHp(stuff.computeBonusHp(monster.getMonsterStats().getHp()));
			finalStats.setHp(statsBonus.getHp() + monster.getMonsterStats().getHp());
		}
		if (statsBonus.getAtk() == -1) {
			statsBonus.setAtk(stuff.computeBonusAtk(monster.getMonsterStats().getAtk()));
			finalStats.setAtk(statsBonus.getAtk() + monster.getMonsterStats().getAtk());
		}
		if (statsBonus.getDef() == -1) {
			statsBonus.setDef(stuff.computeBonusDef(monster.getMonsterStats().getDef()));
			finalStats.setDef(statsBonus.getDef() + monster.getMonsterStats().getDef());
		}
		if (statsBonus.getSpd() == -1) {
			statsBonus.setSpd(stuff.computeSpd(monster.getMonsterStats().getSpd()));
			finalStats.setSpd(statsBonus.getSpd() + monster.getMonsterStats().getSpd());
		}
		if (statsBonus.getCrate() == -1) {
			statsBonus.setCrate(stuff.computeCrate());
			finalStats.setCrate(statsBonus.getCrate() + monster.getMonsterStats().getCrate());
		}
		if (statsBonus.getCdmg() == -1) {
			statsBonus.setCdmg(stuff.computeCdmg());
			finalStats.setCdmg(statsBonus.getCdmg() + monster.getMonsterStats().getCdmg());
		}
		if (statsBonus.getRes() == -1) {
			statsBonus.setRes(stuff.computeRes());
			finalStats.setRes(statsBonus.getRes() + monster.getMonsterStats().getRes());
		}
		if (statsBonus.getAcc() == -1) {
			statsBonus.setAcc(stuff.computeAcc());
			finalStats.setAcc(statsBonus.getAcc() + monster.getMonsterStats().getAcc());
		}
		
		if (maxStats.getHp() < finalStats.getHp())
			maxStats.setHp(finalStats.getHp());
		if (maxStats.getAtk() < finalStats.getAtk())
			maxStats.setAtk(finalStats.getAtk());
		if (maxStats.getDef() < finalStats.getDef())
			maxStats.setDef(finalStats.getDef());
		if (maxStats.getSpd() < finalStats.getSpd())
			maxStats.setSpd(finalStats.getSpd());
		if (maxStats.getCrate() < finalStats.getCrate())
			maxStats.setCrate(finalStats.getCrate());
		if (maxStats.getCdmg() < finalStats.getCdmg())
			maxStats.setCdmg(finalStats.getCdmg());
		if (maxStats.getRes() < finalStats.getRes())
			maxStats.setRes(finalStats.getRes());
		if (maxStats.getAcc() < finalStats.getAcc())
			maxStats.setAcc(finalStats.getAcc());
		
		StuffedMonster stuffedMonster = new StuffedMonster(monster, stuff, statsBonus, finalStats);
		return stuffedMonster;
	}
	
	public StuffedMonster[] buildTeamMateStuffs(Stuff[] stuffs, TeamMate teamMate) {
		this.failures = new int[9];
		
		maxStats = new MonsterStats(0, 0, 0, 0, 0, 0, 0, 0);
		
		List<StuffedMonster> preSelecteds = new ArrayList<StuffedMonster>();
		
		long totalNbStuffs = stuffs.length;
		long currentNbStuff = 0;
		long currentPourcent = -1;
		
		Monster monster = teamMate.getMonster();
		Set<Map.Entry<StatType,Integer>> requiredStatsEntrySet = teamMate.getRequiredStats().entrySet();
		Set<Map.Entry<RuneSet,Integer>> requiredSetsEntrySet = teamMate.getRequiredSets().entrySet();
		
		StuffedMonster currentMonster = null;
		for (Stuff stuff : stuffs) {
			if ((currentMonster = this.goodStuff(stuff, monster, requiredStatsEntrySet, requiredSetsEntrySet, teamMate.isBrokenSet())) != null) {
				preSelecteds.add(currentMonster);
			}
			currentNbStuff++; 
			if (currentNbStuff % 1000000 == 0) {
				long pourcent = currentNbStuff * 100 / totalNbStuffs;
				if (pourcent != currentPourcent) {
					currentPourcent = pourcent;
					System.out.println(currentPourcent + "% selectStuff for " + teamMate.getMonster().getName());
				}
			}

		}
		
		PriorityQueue<StuffedMonster>  selecteds = new PriorityQueue<StuffedMonster>(new Comparator<StuffedMonster>() {

			@Override
			public int compare(StuffedMonster o1, StuffedMonster o2) {
				if (o2.eval(maxStats, teamMate.getEvalStats()) < o1.eval(maxStats, teamMate.getEvalStats()))
					return -1;
				else
					return 1;
			}
		});
		
		for (StuffedMonster selected : preSelecteds) {
			selecteds.add(selected);
		}
		
		
		int selectedsSize = selecteds.size() > 16000 ? 16000 : selecteds.size();
		StuffedMonster[] selectedsArray = new StuffedMonster[selectedsSize];
		for (int i = 0; i < selectedsSize; i++) {
			selectedsArray[i] = selecteds.poll();
		}

		if (selectedsArray.length == 0) {
			System.err.println(teamMate.getMonster().getName() + " NO RUNAGE FOUND");
			System.err.println("Failures: " + this.failuresToString(failures));
			
		}
			
		
		return selectedsArray;
	}
	
	public boolean haveSameRune(StuffedMonster monster1, StuffedMonster monster2) {
		for (Rune rune1 : monster1.getStuff().getRunes())
			for (Rune rune2 : monster2.getStuff().getRunes())
				if (rune1 == rune2)
					return true;
		return false;
	}
	
	public List<StuffNode> selectStuffsForTeam(Team team) {
		List<TeamMate> teamMates = team.getTeamMates();
		
		List<StuffNode> currentNodes = new ArrayList<>();
		TeamMate teamMate = teamMates.get(0);
		double bestEval = teamMate.getSelectedStuff()[0].getEval();
		for (int j = 0; j < teamMate.getSelectedStuff().length; j++) {
			StuffedMonster currentMonster = teamMate.getSelectedStuff()[j];
			currentNodes.add(new StuffNode(null, currentMonster, currentMonster.getEval() / bestEval));
			
		}
		
		
		for (int i = 1; i < teamMates.size(); i++) {
			teamMate = teamMates.get(i);
			bestEval = teamMate.getSelectedStuff()[0].getEval();
			
			PriorityQueue<StuffNode> selecteds = new PriorityQueue<StuffNode>(new Comparator<StuffNode>() {

				@Override
				public int compare(StuffNode o1, StuffNode o2) {
					if (o2.totalEval() < o1.totalEval())
						return -1;
					else
						return 1;
				}
			});
			
			for (StuffNode currentNode : currentNodes) {
				for (int j = 0; j < teamMate.getSelectedStuff().length; j++) {
					StuffedMonster currentMonster = teamMate.getSelectedStuff()[j];
					
					StuffNode parentNode = currentNode;
					boolean add = true;
					while (parentNode != null) {
						if (this.haveSameRune(currentMonster, parentNode.getStuffedMonster())) {
							add = false;
							break;
						}
						parentNode = parentNode.getParent();
					}
					if (add)
						selecteds.add(new StuffNode(currentNode, currentMonster, currentMonster.getEval() / bestEval));
				}
			}
			currentNodes.clear();
			int selectedsCount = selecteds.size() < 16000 ? selecteds.size() : 16000;
			for (int j = 0; j < selectedsCount; j++) {
				currentNodes.add(selecteds.poll());
			}
		}
		return currentNodes;
	}
	
	public List<StuffNode> optimizeTeam(Stuff[] stuffs, Team team) {
		for (TeamMate teamMate : team.getTeamMates())
			teamMate.setSelectedStuff(this.buildTeamMateStuffs(stuffs, teamMate));
		
		List<StuffNode> stuffsNode = this.selectStuffsForTeam(team);
		System.out.println("Total stuffsNode: " + stuffsNode.size());
		
		if (stuffsNode.size() == 0) {
			System.err.println("No Stuff !!!");
		}
		
		return stuffsNode;
	}
	
}
