package dto;

public class LostThing {
	private int id;
	private int userid;
	private String thing;
	private int found;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getThing() {
		return thing;
	}
	public void setThing(String thing) {
		this.thing = thing;
	}
	public int getFound() {
		return found;
	}
	public void setFound(int found) {
		this.found = found;
	}
	@Override
	public String toString() {
		return "LostThing [id=" + id + ", userid=" + userid + ", thing="
				+ thing + ", found=" + found + "]";
	}
	
	
}
