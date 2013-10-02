package dto;

public class Line {
	

	private int id; 
	private int lat;
	private int lng;
	private String type;
	private int vote;
	private int count;

	public Line() {
	}

	public Line(int id, int lat, int lng, String type, int vote, int count ) {
		super();
		this.id = id;
		this.lat = lat;
		this.lng = lng;
		this.type = type;
		this.vote = vote;
		this.count = count;
	}
	
		
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLat() {
		return lat;
	}

	public void setLat(int lat) {
		this.lat = lat;
	}
	

	public int getLng() {
		return lng;
	}

	public void setLng(int lng) {
		this.lat = lng;
	}
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getVote() {
		return vote;
	}

	public void setVote(int vote) {
		this.vote = vote;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	

	

	 

	@Override
	public String toString() {
		return "Course [id=" + id + ", lng=" + lng + ", lat=" + lat + ", type=" + type
				+ ", vote=" + vote + ", count=" + count +"]";
	}
}
