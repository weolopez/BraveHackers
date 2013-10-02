package model;

import java.sql.Connection;
import java.util.ArrayList;
import dao.UsersAccess;
import dao.Database;
import dto.User;

public class UserManager {
	public ArrayList<User> getUsers() throws Exception {
		ArrayList<User> userList = new ArrayList<User>();
		Database db = new Database();
		Connection con = db.getConnection();
		UsersAccess access = new UsersAccess();
		userList = access.getUsers(con);
		return userList;
	}
	public int addUser(User user) throws Exception {
		Database db = new Database();
		Connection con = db.getConnection();
		UsersAccess access = new UsersAccess();
		int id = access.addUser(user,con);
		return id;
	}
}