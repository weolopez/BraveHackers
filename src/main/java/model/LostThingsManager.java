package model;

import java.sql.Connection;
import java.util.ArrayList;
import dao.LostThingsAccess;
import dao.Database;
import dto.LostThing;

public class LostThingsManager {
	public ArrayList<LostThing> getLostThings() throws Exception {
		ArrayList<LostThing> lostList = new ArrayList<LostThing>();
		Database db = new Database();
		Connection con = db.getConnection();
		LostThingsAccess access = new LostThingsAccess();
		lostList = access.getLostThings(con);
		return lostList;
	}
	public LostThing getLostThing(int id) throws Exception {
		ArrayList<LostThing> lostList = new ArrayList<LostThing>();
		Database db = new Database();
		Connection con = db.getConnection();
		LostThingsAccess access = new LostThingsAccess();
		lostList = access.getLostThings(id,con);
		if (lostList!=null && lostList.size() >0 )
		{
			return lostList.get(0);
		}
		return null;
	}
	
	public int addLostThing(LostThing lostThing) throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		LostThingsAccess access = new LostThingsAccess();
		int id = access.addLostThing(lostThing,con);
		return id;
	}
	
	public int updateFound(int id) throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		LostThingsAccess access = new LostThingsAccess();
		return access.updateFound(id, con);
	}
	
}