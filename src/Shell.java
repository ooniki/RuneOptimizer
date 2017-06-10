import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import logger.Logger;
import parser.JSONFileReader;
import profile.Profile;
import profile.ProfileManager;
import stuff.StuffNode;
import team.Team;
import team.TeamMate;

public class Shell {

	private Profile profile;
	private ProfileManager profileManager;
	
	public Shell() {
		this.profile = new Profile();
		this.profileManager = new ProfileManager();
	}
	
	public void run() {
		Logger.level = 2;
		Logger.print(1, "Welcome SW Optimizer");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		boolean stop = false;
		
		while (!stop) {
			try {
				stop = this.waitInput(reader);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean waitInput(BufferedReader reader) throws IOException {
		String input = reader.readLine();
		input = input.toLowerCase();
		
		if (input.length() <= 0)
			return false;
		if (input.equals("q") || input.equals("quit")) {
			Logger.print(1, "BYE !");
			return true;	
		}
		
		this.parseInput(input);
		
		return false;
	}
	
	public void parseInput(String input) {
		String[] splittedInput = input.split(" ");
		
		if (splittedInput.length <= 0)
			return;
		
		switch (splittedInput[0]) {
		case "load":
			this.load(splittedInput);
			break;
		case "show":
			this.show(splittedInput);
			break;
		case "build":
			this.build(splittedInput);
			break;
		case "logger":
			this.logger(splittedInput);
			break;

		default:
			Logger.error(0, "Invalide command: " + splittedInput[0]);
			break;
		}
	}
	
	public void load(String[] input) {
		Logger.print(4, "FUNCTION LOAD");
		
		if (input.length <= 1) {
			Logger.error(0, "load command need arguments");
			return;
		}
		
		switch (input[1]) {
		case "data":
			this.loadData(input);
			break;
		case "team":
			this.loadTeam(input);
			break;
		case "config":
			this.loadConfig(input);
			break;
		default:
			Logger.error(0, "Invalide argument for command load: " + input[1]);
			break;
		}
	}
	
	public void loadConfig(String[] input) {
		Logger.print(4, "FUNCTION LOAD CONFIG");
		
		if (input.length <= 2) {
			Logger.error(0, "load config command need file path");
			return;
		}
		
		String path = input[2];
		try {
			JSONObject json = JSONFileReader.getJsonFromFile(path);
			Logger.print(1, "load config from:" + path);
			
			this.profileManager.loadFromConfigJson(profile, json);
			Logger.print(2, "Monsters Count:" + this.profile.getMonsters().length);
			Logger.print(2, "Runes Count: " + this.profile.getRunes().length);
			Logger.print(2, "Team Count:" + this.profile.getTeams().length);
			Logger.print(2, "Banned Runes Count:" + this.profile.getBannedRunesUniqueId().size());
		} catch (FileNotFoundException e) {
			Logger.error(0, "Config file not found: " + path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadData(String[] input) {
		Logger.print(4, "FUNCTION LOAD DATA");
		
		if (input.length <= 2) {
			Logger.error(0, "load data command need file path");
			return;
		}
		
		String path = input[2];
		try {
			JSONObject json = JSONFileReader.getJsonFromFile(path);
			Logger.print(1, "load data from:" + path);
			this.profileManager.loadRunesFromJson(this.profile, json);
			this.profileManager.loadMonstersFromJson(this.profile, json);
			Logger.print(2, "Monsters Count:" + this.profile.getMonsters().length);
			Logger.print(2, "Runes Count: " + this.profile.getRunes().length);
		} catch (FileNotFoundException e) {
			Logger.error(0, "Data file not found: " + path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadTeam(String[] input) {
		Logger.print(4, "FUNCTION LOAD TEAM");
		
		if (input.length <= 2) {
			Logger.error(0, "load team command need file path");
			return;
		}
		
		String path = input[2];
		try {
			JSONObject json = JSONFileReader.getJsonFromFile(path);
			Logger.print(1, "load team from:" + path);
			this.profileManager.loadTeamFromJson(profile, json);
			Logger.print(2, "Team Count:" + this.profile.getTeams().length);
		} catch (FileNotFoundException e) {
			Logger.error(0, "Team file not found: " + path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void show(String[] input) {
		Logger.print(4, "FUNCTION SHOW");
		
		if (input.length <= 1) {
			Logger.error(0, "show command need arguments");
			return;
		}
		
		switch (input[1]) {
		case "teams":
			this.showTeams();
			break;
		case "team":
			this.showTeam(input);
			break;
		default:
			Logger.error(0, "Invalide argument for command show: " + input[1]);
			break;
		}
	}
	
	public void showTeams() {
		Logger.print(4, "FUNCTION SHOW TEAMS");
		for (Team team : this.profile.getTeams())
			Logger.print(0, team.getName());
	}
	
	public void showTeam(String[] input) {
		Logger.print(4, "FUNCTION SHOW TEAM");
		
		if (input.length <= 2) {
			Logger.error(0, "load show command need file path");
			return;
		}
		
		String teamName = input[2];
		Team team = this.profile.getTeamByName(teamName);
		if (team == null) {
			Logger.error(0, "Team dont exist: " + teamName);
			return;
		}
		for (TeamMate teamMate : team.getTeamMates()) {
			Logger.print(0, teamMate.getMonster().getName());
		}
	}
	
	public void build(String[] input) {
		Logger.print(4, "FUNCTION BUILD");
		
		if (input.length <= 1) {
			Logger.error(0, "build command need arguments");
			return;
		}
		
		switch (input[1]) {
		case "team":
			this.buildTeam(input);
			break;
		default:
			Logger.error(0, "Invalide argument for command build: " + input[1]);
			break;
		}
	}
	
	public void buildTeam(String[] input) {
		Logger.print(4, "FUNCTION BUILD TEAM");
		
		if (input.length <= 2) {
			Logger.error(0, "load build command need file path");
			return;
		}
		
		String teamName = input[2];
		Team team = this.profile.getTeamByName(teamName);
		if (team == null) {
			Logger.error(0, "Team dont exist: " + teamName);
			return;
		}
		
		List<StuffNode> stuffNodes = this.profileManager.buildTeam(this.profile, team);
		
		try {
			int nbStuff = 3;
			nbStuff = nbStuff > stuffNodes.size() ? stuffNodes.size() : nbStuff;
			for (int i = 0; i < nbStuff; i++) {
				StuffNode current = stuffNodes.get(i);
				
				JSONObject currentObj = new JSONObject();
				currentObj.put("id", i);
				
				JSONArray teamJson = new JSONArray();
				while(current != null) {
					teamJson.put(current.getStuffedMonster().toJSON());			
					current = current.getParent();
				}
				currentObj.put("team", teamJson);
				
				File file = new File("./" + team.getName() + i + ".stuff");
				FileWriter fw = new FileWriter(file);
				fw.write(currentObj.toString());
				fw.close();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void logger(String[] input) {
		Logger.print(4, "FUNCTION LOGGER");
		
		if (input.length <= 1) {
			Logger.print(1, "logger level " +  Logger.level);
		} else {
			int level = Logger.level;
			try {
				level = Integer.valueOf(input[1]);
			} catch (NumberFormatException e) {
				Logger.error(0, "level must be a number");
			}
			Logger.level = level;
			Logger.print(1, "logger level " +  Logger.level);
		}
		
		
	}
	
}
