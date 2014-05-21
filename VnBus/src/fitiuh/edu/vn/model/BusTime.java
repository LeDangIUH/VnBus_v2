package fitiuh.edu.vn.model;

/*BusTime.class will be manager id, time start and time end for all bus */
public class BusTime {
	private String busID;
	private String timeStart;
	private String timeEnd;
	
	public String getBusID() {
		return busID;
	}
	public void setBusID(String busID) {
		this.busID = busID;
	}
	public String getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}
	public String getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}
}
