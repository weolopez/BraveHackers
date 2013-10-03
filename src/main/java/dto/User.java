package dto;

import java.util.Date;

public class User {
	private int id;
	private String firstname;
	private String lastname;
	private String password;
	private String username;
	private String authmethod;
	private double lat;
	private double lng;
	private Date datecreated;
	private int lineid;

	public User() {
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getAuthmethod() {
		return authmethod;
	}


	public void setAuthmethod(String authmethod) {
		this.authmethod = authmethod;
	}
	
	
	public double getLat() {
		return lat;
	}


	public void setLat(double lat) {
		this.lat = lat;
	}


	public double getLng() {
		return lng;
	}


	public void setLng(double lng) {
		this.lng = lng;
	}


	public Date getDatecreated() {
		return datecreated;
	}


	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}
	
	


	public int getLineid() {
		return lineid;
	}


	public void setLineid(int lineid) {
		this.lineid = lineid;
	}


	@Override
	public String toString() {
		return "User [id=" + id + 
				", firstname=" + firstname + 
				", lastname=" + lastname + 
				", password=" + password + 
				", username= " + username +
				", authmethod= " + authmethod  +
				", lat= " + lat +
				", lng= " + lng  +
					", datecreated= " + datecreated +
					", lineid= " + lineid 
				 + "]";
	}
}
