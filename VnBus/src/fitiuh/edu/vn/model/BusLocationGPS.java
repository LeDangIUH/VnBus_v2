package fitiuh.edu.vn.model;

/*BusLocationGPS.class will be manager gps for all bus stop marker  */
public class BusLocationGPS {
	
	private String busID;
	private String locationGPS;
	private int sortNum;
	
	public String getBusID() {
		return busID;
	}
	public void setBusID(String busID) {
		this.busID = busID;
	}
	public String getLocationGPS() {
		return locationGPS;
	}
	public void setLocationGPS(String locationGPS) {
		this.locationGPS = locationGPS;
	}
	public int getSortNum() {
		return sortNum;
	}
	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}
}
