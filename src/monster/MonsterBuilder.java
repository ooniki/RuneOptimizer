package monster;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class MonsterBuilder {
	
	public Monster[] buildMonsters(JSONArray monsterJson) throws JSONException {
		Monster[] monsters = new Monster[monsterJson.length()];
		
		for (int i = 0; i < monsterJson.length(); i++) {
			JSONObject obj = monsterJson.getJSONObject(i);
			
			long masterId = obj.getLong("master_id");
			long id = obj.getLong("id");
			String name = obj.getString("name");
			EAttribute attribute = EAttribute.fromMarkup(obj.getString("attribute").toLowerCase());
			int stars = obj.getInt("stars");
			int level = obj.getInt("level");
			int hp = obj.getInt("b_hp");
			int atk = obj.getInt("b_atk");
			int def = obj.getInt("b_def");
			int spd = obj.getInt("b_spd");
			int crate = obj.getInt("b_crate");
			int cdmg = obj.getInt("b_cdmg");
			int res = obj.getInt("b_res");
			int acc = obj.getInt("b_acc");
			
			MonsterStats stats = new MonsterStats(hp, atk, def, spd, crate, cdmg, res, acc);
			Monster monster = new Monster(masterId, id, name, attribute, stars, level, stats);
			monsters[i] = monster;
		}
		
		return monsters;
	}
}
