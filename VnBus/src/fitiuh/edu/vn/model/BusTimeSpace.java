package fitiuh.edu.vn.model;

import java.util.List;

/*BusTimeSpace.class will be manage id and time space from busid this*/
public class BusTimeSpace {

	private String busID;
	private List<Integer> timeSpaceDetail;
	
	public String getBusID() {
		return busID;
	}
	public void setBusID(String busID) {
		this.busID = busID;
	}
	public List<Integer> getTimeSpaceDetail() {
		return timeSpaceDetail;
	}
	public void setTimeSpaceDetail(List<Integer> timeSpaceDetail) {
		this.timeSpaceDetail = timeSpaceDetail;
	}
}
